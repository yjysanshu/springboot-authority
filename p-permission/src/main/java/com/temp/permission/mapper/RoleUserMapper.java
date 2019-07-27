package com.temp.permission.mapper;

import com.temp.common.mapper.BaseMapper;
import com.temp.permission.entity.RoleUser;

import java.util.List;
import java.util.Map;

public interface RoleUserMapper extends BaseMapper<RoleUser> {
    List<RoleUser> queryAllByUserId(Integer userId);
    void callProAddUserRole(Map map);
    void callProAddRoleUser(Map map);
    List<RoleUser> queryListByUserId(Integer userId);
}
