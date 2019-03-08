package com.temp.permission.mapper;

import com.temp.common.mapper.BaseMapper;
import com.temp.permission.entity.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 根据类型查询存在角色的权限资源
     * @param type 类型
     * @return list
     */
    List<Resource> queryAllByType(Integer type);

    /**
     * 根据父ID查询所有的权限资源
     * @param parentId 父ID
     * @return list
     */
    List<Resource> queryListByParentId(Integer parentId);

    /**
     * 根据父ID和类型查询所有的权限资源
     * @param map map
     * @return list
     */
    List<Resource> queryListByParentIdType(Map map);

    /**
     * 根据角色ID和资源类型查询权限资源
     * @param map map
     * @return list
     */
    List<Resource> queryListByRoleIdAndType(Map map);

    /**
     * 根据资源类型查询权限资源
     * @param type 资源类型
     * @return list
     */
    List<Resource> queryListByType(Integer type);
}
