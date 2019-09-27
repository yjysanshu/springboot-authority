package com.temp.permission.domain.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class UserVO {
    private Integer id;
    private String phone;
    private String name;
    private String email;
    private String password;
    private String token;
    private String avatar;
    private Integer loginCount;
    private String lastIp;
    private Integer status;
    private Map<Integer, String> roles = new HashMap<>();
    private String createAt;
    private String updateAt;
}
