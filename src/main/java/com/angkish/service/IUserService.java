package com.angkish.service;

import com.angkish.pojo.dto.UserCreateDTO;
import com.angkish.pojo.dto.UserEmailLoginDTO;
import com.angkish.pojo.dto.UserLoginDTO;
import com.angkish.pojo.entity.User;
import com.angkish.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

public interface IUserService extends IService<User> {

    Result sendRegisterCode(@Email String email);

    Result register(@Valid UserCreateDTO userCreateDTO);

    Result login(@Valid UserLoginDTO userLoginDTO);

    Result emailLogin(@Valid UserEmailLoginDTO userEmailLoginDTO);
}
