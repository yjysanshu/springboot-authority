package com.temp.permission.service;

import com.temp.common.util.DateUtil;
import com.temp.permission.consts.BackendConst;
import com.temp.permission.entity.Resource;
import com.temp.permission.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceService {

    @Autowired
    private ResourceMapper mapper;

    /**
     * 根据类型获取所有的匹配角色的资源信息
     * @param type 资源类型
     * @return list
     */
    public List<Resource> getAllByType(Integer type) {
        return mapper.queryAllByType(type);
    }

    /**
     * 根据类型获取所有的资源权限
     * @param type 资源类型
     * @return list
     */
    public List<Resource> getListByType(Integer type) {
        return mapper.queryListByType(type);
    }

    /**
     * 根据类型获取所有的资源信息
     * @param type 资源类型
     * @return list
     */
    public List<Resource> getListByRoleIdType(Integer roleId, Integer type) {
        Map<String, Integer> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("resourceType", type);
        return mapper.queryListByRoleIdAndType(map);
    }

    /**
     * 根据父ID获取资源信息
     * @param parentId 父ID
     * @return list
     */
    public List<Resource> getListByParentId(Integer parentId) {
        return mapper.queryListByParentId(parentId);
    }

    /**
     * 批量保存资源数据
     * @param parentId 父ID
     * @param resources 接口资源
     * @return boolean
     */
    public Boolean batchSave(Integer parentId, List<String> resources) {
        Resource resource = new Resource();
        for (String url : resources) {
            resource.setResourceType(BackendConst.RESOURCE_TYPE_API);
            resource.setResourceParentId(parentId);
            resource.setResourceTarget(url);
            resource.setResourceCreateAt(new Date());
            mapper.add(resource);
        }
        return true;
    }

    /**
     * 删除一条资源权限
     * @param id i
     * @return boolean
     */
    public Boolean delete(Integer id) {
        return mapper.delete(id) > 0;
    }
}
