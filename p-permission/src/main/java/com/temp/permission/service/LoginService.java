package com.temp.permission.service;

import com.temp.permission.mapper.OauthUserMapper;
import com.temp.permission.mapper.UserMapper;
import com.temp.permission.model.OauthUser;
import com.temp.common.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {
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
}
