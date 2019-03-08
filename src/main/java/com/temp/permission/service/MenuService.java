package com.temp.permission.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.temp.permission.consts.BackendConst;
import com.temp.permission.entity.Resource;
import com.temp.permission.entity.RoleUser;
import com.temp.permission.entity.User;
import com.temp.permission.mapper.ResourceMapper;
import com.temp.permission.mapper.RoleUserMapper;
import com.temp.permission.model.request.MenuDTO;
import com.temp.permission.model.MenuBase;
import com.temp.permission.model.response.MenuResponse;
import com.temp.permission.util.ConsoleUtil;
import com.temp.permission.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MenuService extends BaseService {
    @Autowired
    private ResourceMapper mapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private PrivilegeService privilegeService;

    private List<Resource> resourceListAll;

    /**
     * 获取显示的菜单
     * @param dto 请求信息
     * @return 菜单信息
     * @throws IOException json解析错误
     */
    public List<MenuBase> getList(MenuDTO dto) throws IOException {
        List<Resource> resourceList = mapper.queryList(formatModelDetail(dto));
        List<MenuBase> listMenu = new ArrayList<>();
        for (Resource resource : resourceList) {
            MenuBase menu = formatResponse(resource);
            listMenu.add(menu);
        }

        return listMenu;
    }

    /**
     * 获取所有的父级菜单
     * @return 菜单信息
     * @throws IOException json解析错误
     */
    public List<MenuBase> getFatherList() throws IOException {
        List<Resource> resourceList = mapper.queryListByParentId(0);
        List<MenuBase> listMenu = new ArrayList<>();
        for (Resource resource : resourceList) {
            MenuBase menu = formatResponse(resource);
            listMenu.add(menu);
        }

        return listMenu;
    }

    /**
     * 获取用户菜单
     * @return 菜单信息
     * @throws IOException -
     */
    public List<MenuResponse> getMenuByUser() throws IOException {
        User user = this.getCurrentUser();
        List<RoleUser> roleUserList = roleUserMapper.queryAllByUserId(user.getUserId());
        if (roleUserList.size() <= 0) {
            return new ArrayList<>();
        }
        List<Integer> roleIds = new ArrayList<>();
        for (RoleUser roleUser : roleUserList) {
            roleIds.add(roleUser.getRole().getRoleId());
        }
        ConsoleUtil.formatPrint(roleIds);
        return privilegeService.getMenuByRoleList(roleIds);
    }

    /**
     * 递归获取菜单
     * @param parentId 父级ID
     * @return 菜单
     * @throws IOException 解析JSON错误
     */
    public List<MenuResponse> getMenuByParentId(Integer parentId) throws IOException {
        if (parentId == BackendConst.PARENT_ID_DEFAULT) {
            Resource resource = new Resource();
            resource.setResourceType(BackendConst.RESOURCE_TYPE_MENU);
            resourceListAll = mapper.queryList(new Resource());
        }
        List<Resource> listResource;
        //本地有使用本地的数据
        if (resourceListAll.size() > 0) {
            listResource = this.getMenuByListAndParentId(parentId);
        } else {
            Map<String, Integer> map = new HashMap<>();
            map.put("parentId", parentId);
            map.put("type", BackendConst.RESOURCE_TYPE_MENU);
            listResource = mapper.queryListByParentIdType(map);
        }
        List<MenuResponse> list = new ArrayList<>();
        for (Resource resource : listResource) {
            if (resource.getResourceType() == BackendConst.RESOURCE_TYPE_API) {
                continue;
            }
            MenuResponse menu = formatResponse(resource);
            menu.setChildren(this.getMenuByParentId(menu.getId()));
            list.add(menu);
        }
        return list;
    }

    /**
     * 查询本地list信息
     * @param parentId -
     * @return -
     */
    public List<Resource> getMenuByListAndParentId(Integer parentId) {
        List<Resource> resourceList = new ArrayList<>();
        for (Resource resource : resourceListAll) {
            if (resource.getResourceParentId().equals(parentId)) {
                resourceList.add(resource);
            }
        }
        return resourceList;
    }

    /**
     * 是否展示菜单: 当所有子菜单都未被勾选时，不展示当前菜单，返回 false
     *              其他情况都需要展示菜单，均返回 true
     * @param menu 菜单
     * @return true-展示菜单
     */
    public Boolean isDisplay(MenuResponse menu) {
        if (menu.getChecked()) {
            return true;
        }
        for (MenuResponse cMenu: menu.getChildren()) {
            if (this.isDisplay(cMenu)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否选中父菜单
     * @param menu -
     * @return -
     */
    public Boolean isChecked(MenuResponse menu) {
        for (MenuResponse cMenu : menu.getChildren()) {
            if (!this.isChecked(cMenu)) {
                return false;
            }
        }
        return menu.getChecked();
    }

    public List<Resource> getPrivilegeListByRoleIdAndType(Integer roleId, String resourceType) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("resourceType", resourceType);
        return mapper.queryListByRoleIdAndType(map);
    }

    public Resource getOneById(Integer id) {
        return mapper.queryOne(id);
    }

    /**
     * 保存菜单
     * @param menu 请求信息
     * @return 受影响的行数
     * @throws JsonProcessingException 解析JSON错误
     */
    public Integer save(MenuDTO menu) throws JsonProcessingException {
        Resource resource;
        if (menu.getId() != null) {
            resource = mapper.queryOne(menu.getId());
        } else {
            resource = new Resource();
            resource.setResourceCreateAt(new Date());
            resource.setResourceType(0);
        }
        resource.setResourceParentId(menu.getParentId());
        resource.setResourceTarget(menu.getUrl());
        resource.setResourceData(JSONUtil.menuToJson(menu));
        if (menu.getId() != null) {
            return mapper.update(resource);
        } else {
            return mapper.add(resource);
        }
    }

    /**
     * 删除菜单
     * @param id 资源ID
     * @return 受影响的行数
     */
    public Integer delete(Integer id) {
        return mapper.delete(id);
    }

    private MenuResponse formatResponse(Resource resource) throws IOException {
        Map map = JSONUtil.jsonToMap(resource.getResourceData());
        MenuResponse menu = new MenuResponse();
        menu.setId(resource.getResourceId());
        menu.setUrl(resource.getResourceTarget());
        menu.setParentId(resource.getResourceParentId());
        menu.setTitle((String) map.get("title"));
        menu.setPath((String) map.get("path"));
        menu.setSeq((Integer) map.get("seq"));
        menu.setIcon((String) map.get("icon"));
        menu.setType((String) map.get("type"));
        menu.setChecked(true);
        menu.setChildren(new ArrayList<>());
        return menu;
    }

    private Resource formatModelDetail(MenuDTO menu) {
        Resource resource = new Resource();
        resource.setResourceId(menu.getId());
        resource.setResourceParentId(menu.getParentId());
        resource.setResourceTarget(menu.getUrl());
        resource.setResourceType(BackendConst.RESOURCE_TYPE_MENU);
        return resource;
    }
}
