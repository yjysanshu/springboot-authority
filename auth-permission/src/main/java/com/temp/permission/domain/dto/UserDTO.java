package com.temp.permission.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
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
    private List<Integer> roles;
    private Integer limit = 10;
    private Integer currentPage = 1;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;
}
