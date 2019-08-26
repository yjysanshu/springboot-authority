package com.temp.permission.service.impl;

import com.temp.permission.domain.dto.LoginDTO;
import com.temp.permission.mapper.OauthUserMapper;
import com.temp.permission.mapper.UserMapper;
import com.temp.permission.model.OauthUser;
import com.temp.common.util.RedisPoolUtil;
import com.temp.permission.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements UserDetailsService, LoginService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private OauthUserMapper oauthUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        OauthUser oauthUser = oauthUserMapper.queryOneByPhone(s);
        if (oauthUser == null) {
            throw new UsernameNotFoundException("用户名密码错误");
        }
        return oauthUser;
    }

    public UserDetails loadUserByToken(String s) throws UsernameNotFoundException {
        String phone = RedisPoolUtil.get(s);
        OauthUser oauthUser = oauthUserMapper.queryOneByPhone(phone);
        if (oauthUser == null) {
            throw new UsernameNotFoundException("该用户没有权限");
        }
        return oauthUser;
    }

    @Override
    public String login(LoginDTO dto) {
        UserDetails userDetails = this.loadUserByUsername(dto.getUsername());
        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();
        if (encrypt.matches(dto.getPassword(), userDetails.getPassword())) {
            RedisPoolUtil.set("user:", userDetails.getPassword(), 7200);
        }

        return null;
    }
}
