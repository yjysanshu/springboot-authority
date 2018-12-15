package com.temp.permission.mapper;

import com.temp.common.mapper.BaseMapper;
import com.temp.permission.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {
    List<Role> queryListByRoleIds(Map map);
    List<Role> queryListByParentId(Integer parentId);
}