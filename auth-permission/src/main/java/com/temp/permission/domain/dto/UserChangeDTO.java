package com.temp.permission.domain.dto;

import lombok.Data;

@Data
public class UserChangeDTO {
    private String pic;
    private String password;
    private String newPassword;
}
