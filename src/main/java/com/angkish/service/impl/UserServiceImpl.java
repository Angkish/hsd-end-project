package com.angkish.service.impl;

import com.angkish.constant.JwtClaimsConstant;
import com.angkish.constant.MessageConstant;
import com.angkish.exception.BusinessException;
import com.angkish.mapper.UserMapper;
import com.angkish.pojo.dto.UserCreateDTO;
import com.angkish.pojo.dto.UserEmailLoginDTO;
import com.angkish.pojo.dto.UserLoginDTO;
import com.angkish.pojo.entity.User;
import com.angkish.pojo.vo.UserLoginVO;
import com.angkish.properties.JwtProperties;
import com.angkish.result.Result;
import com.angkish.service.EmailService;
import com.angkish.service.IUserService;
import com.angkish.util.JwtUtil;
import com.angkish.util.RandomCodeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private EmailService emailService;


    /**
     * 新增用户
     * @param userCreateDTO
     * @return
     */
    @Override
    public Result register(UserCreateDTO userCreateDTO) {
        // 根据用户名查用户
        User user1 = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", userCreateDTO.getUsername())
        );
        if (user1 != null) {
            return Result.error(MessageConstant.USERNAME + MessageConstant.EXIST);
        }

        User user = new User();
        BeanUtils.copyProperties(userCreateDTO, user);

        // 使用 MD5 加密密码
        String password = userCreateDTO.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5Password);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userMapper.insert(user);

        return Result.success(MessageConstant.ADD_USER + MessageConstant.SUCCESS);
    }


    /**
     * 用户名登录
     * @param userLoginDTO
     */
    @Override
    public Result<UserLoginVO> login(UserLoginDTO userLoginDTO) {
        // 根据用户名查用户
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", userLoginDTO.getUsername())
        );
        if (user == null) {
            return Result.error(MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }

        // 对前端密码执行 MD5
        String inputPwd = DigestUtils.md5DigestAsHex(
                userLoginDTO.getPassword().getBytes(StandardCharsets.UTF_8)
        );
        // 校验
        if (!inputPwd.equals(user.getPassword())) {
            return Result.error(MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }

        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user, userLoginVO);

        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        claims.put(JwtClaimsConstant.USERNAME,user.getUsername());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        userLoginVO.setToken(token);

        // Redis 中按用户存 token
        String redisTokenKey = "login:token:" + user.getId();
        redisTemplate.opsForValue().set(redisTokenKey, token, 6, TimeUnit.HOURS);

        return Result.success(MessageConstant.LOGIN + MessageConstant.SUCCESS,userLoginVO);
    }

    /**
     * 发送验证码
     * @param email
     * @return
     */
    @Override
    public Result sendRegisterCode(String email) {

        // 1. 是否已注册
        Long count = userMapper.selectCount(
                new QueryWrapper<User>().eq("email", email)
        );
        if (count > 0) {
            throw new BusinessException("该邮箱已注册");
        }

        // 2. 生成验证码
        String code = RandomCodeUtil.generateRandomCode();

        // 3. 存Redis
        String redisCodeKey = "register:email:code:" + email;
        redisTemplate.opsForValue()
                .set(redisCodeKey, code, 5, TimeUnit.MINUTES);

        // 4. 发送邮件（关键）
        emailService.sendRegisterCode(email, code);

        return Result.success(MessageConstant.EMAIL_SEND_SUCCESS);
    }

    /**
     * 邮箱登录
     * @param userEmailLoginDTO
     * @return
     */
    @Override
    public Result<UserLoginVO> emailLogin(UserEmailLoginDTO userEmailLoginDTO) {
        // 根据邮箱查用户
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("email", userEmailLoginDTO.getEmail())
        );
        if (user == null) {
            return Result.error(MessageConstant.EMAIL_ERROR);
        }

        // 验证验证码
        String redisCodeKey = "register:email:code:" + userEmailLoginDTO.getEmail();
        String storedCode = (String) redisTemplate.opsForValue().get(redisCodeKey);

        if (storedCode == null) {
            return Result.error("验证码已过期");
        }

        if (!storedCode.equals(userEmailLoginDTO.getCode())) {
            return Result.error("验证码错误");
        }

        // 验证成功，删除验证码
        redisTemplate.delete(redisCodeKey);

        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user, userLoginVO);

        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        claims.put(JwtClaimsConstant.USERNAME,user.getUsername());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        userLoginVO.setToken(token);

        // Redis 中按用户存 token
        String redisTokenKey = "login:token:" + user.getId();
        redisTemplate.opsForValue().set(redisTokenKey, token, 6, TimeUnit.HOURS);

        return Result.success(MessageConstant.LOGIN + MessageConstant.SUCCESS,userLoginVO);
    }


}