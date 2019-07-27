package com.temp.permission.mapper;

import com.temp.common.mapper.BaseMapper;
import com.temp.permission.entity.Privilege;

import java.util.List;
import java.util.Map;

public interface PrivilegeMapper extends BaseMapper<Privilege> {
    Integer queryCountByRoleIdsAndResourceId(Map map);
    Integer deleteByRoleIdAndResourceIds(Map map);
    List<Privilege> queryListByRoleId(Integer roleId);
}
