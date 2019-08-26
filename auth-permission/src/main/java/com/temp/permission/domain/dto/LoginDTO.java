package com.temp.permission.domain.dto;

import lombok.Data;

@Data
public class LoginDTO {

    /**
     * 登录的用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;
}
