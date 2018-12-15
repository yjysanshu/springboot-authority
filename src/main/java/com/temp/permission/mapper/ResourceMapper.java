package com.temp.permission.mapper;

import com.temp.common.mapper.BaseMapper;
import com.temp.permission.entity.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceMapper extends BaseMapper<Resource> {
    List<Resource> queryAllByType(Integer type);
    List<Resource> queryListByParentId(Integer parentId);
    List<Resource> queryListByRoleIdAndType(Map map);
}
