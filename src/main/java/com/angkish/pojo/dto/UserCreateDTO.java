package com.angkish.pojo.dto;


import com.angkish.constant.MessageConstant;
import com.angkish.constant.RegexPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserCreateDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = MessageConstant.USERNAME + MessageConstant.NOT_NULL)
    @Pattern(regexp = RegexPatterns.USERNAME_REGEX, message = MessageConstant.USERNAME + MessageConstant.FORMAT_ERROR)
    private String username;

    @NotBlank(message = MessageConstant.PASSWORD + MessageConstant.NOT_NULL)
    @Pattern(regexp = RegexPatterns.PASSWORD_REGEX, message = MessageConstant.PASSWORD + MessageConstant.FORMAT_ERROR)
    private String password;

    private String realName;

    @Pattern(regexp = RegexPatterns.PHONE_REGEX, message = MessageConstant.PHONE + MessageConstant.FORMAT_ERROR)
    private String phone;

    @NotNull(message = MessageConstant.USER_ROLE + MessageConstant.NOT_NULL)
    private Integer role;

}
