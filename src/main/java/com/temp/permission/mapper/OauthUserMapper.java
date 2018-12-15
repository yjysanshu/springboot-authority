package com.temp.permission.mapper;

import com.temp.common.mapper.BaseMapper;
import com.temp.permission.model.OauthUser;

public interface OauthUserMapper extends BaseMapper<OauthUser> {
    OauthUser queryOneByPhone(String phone);
    OauthUser queryOneByToken(String token);
}
