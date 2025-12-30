package com.angkish.pojo.dto;

import com.angkish.constant.MessageConstant;
import com.angkish.constant.RegexPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserEmailLoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = MessageConstant.EMAIL + MessageConstant.NOT_NULL)
    @Pattern(regexp = RegexPatterns.EMAIL_REGEX, message = MessageConstant.EMAIL + MessageConstant.FORMAT_ERROR)
    private String email;

    @NotBlank
    private String code;

}
