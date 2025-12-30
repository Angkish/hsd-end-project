package com.angkish.controller;


import com.angkish.pojo.dto.UserCreateDTO;
import com.angkish.pojo.dto.UserEmailLoginDTO;
import com.angkish.pojo.dto.UserLoginDTO;
import com.angkish.result.Result;
import com.angkish.service.IUserService;
import com.angkish.util.BindingResultUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @return 结果
     */
    @GetMapping("/sendRegisterCode")
    public Result sendVerificationCode(@RequestParam @Email String email) {
        return userService.sendRegisterCode(email);
    }

    /**
     * 新增用户
     * @param userCreateDTO
     * @return
     */
    @PostMapping("/register")
    public Result save(@RequestBody @Valid UserCreateDTO userCreateDTO, BindingResult bindingResult) {
        // 校验失败时，返回错误信息
        String errorMessage = BindingResultUtil.handleBindingResultErrors(bindingResult);
        if (errorMessage != null) {
            return Result.error(errorMessage);
        }
        return userService.register(userCreateDTO);
    }

    /**
     * 用户名登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody @Valid UserLoginDTO userLoginDTO, BindingResult bindingResult) {
        // 校验失败时，返回错误信息
        String errorMessage = BindingResultUtil.handleBindingResultErrors(bindingResult);
        if (errorMessage != null) {
            return Result.error(errorMessage);
        }
        return userService.login(userLoginDTO);
    }

    /**
     * 邮箱登录
     * @param userEmailLoginDTO
     * @param bindingResult
     * @return
     */
    @PostMapping("/emailLogin")
    public Result emailLogin(@RequestBody @Valid UserEmailLoginDTO userEmailLoginDTO, BindingResult bindingResult) {
        // 校验失败时，返回错误信息
        String errorMessage = BindingResultUtil.handleBindingResultErrors(bindingResult);
        if (errorMessage != null) {
            return Result.error(errorMessage);
        }
        return userService.emailLogin(userEmailLoginDTO);
    }
}
