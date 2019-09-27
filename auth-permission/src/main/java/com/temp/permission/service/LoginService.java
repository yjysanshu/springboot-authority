package com.temp.permission.service;

import com.temp.common.exception.UserException;
import com.temp.permission.domain.dto.LoginDTO;

public interface LoginService {

    /**
     * 用户登录
     * @param dto
     * @return
     */
    public String login(LoginDTO dto) throws UserException;
}
