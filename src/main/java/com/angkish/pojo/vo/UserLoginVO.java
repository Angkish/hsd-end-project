package com.angkish.pojo.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private Integer role;

    private String token;

}
