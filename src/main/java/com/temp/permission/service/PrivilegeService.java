package com.temp.permission.service;

import com.temp.permission.consts.BackendConst;
import com.temp.permission.entity.Privilege;
import com.temp.permission.entity.Resource;
import com.temp.permission.entity.Role;
import com.temp.permission.entity.RoleUser;
import com.temp.permission.mapper.PrivilegeMapper;
import com.temp.permission.model.request.PrivilegeDTO;
import com.temp.permission.model.response.MenuResponse;
import com.temp.permission.util.ConsoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeMapper mapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleUserService roleUserService;

    /**
     * 获取角色的菜单
     * @param roleId 角色ID
     * @return 菜单信息
     * @throws IOException -
     */
    public Map getRoleMenu(Integer roleId) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Role role = roleService.getOneById(roleId);
        if (role == null) {
            return null;
        }

        List<MenuResponse> list= this.getMenuByRoleList(Collections.singletonList(role.getRoleParentId()));
        list = this.checkMenuPrivilege(list, Collections.singletonList(roleId));

        List<Integer> listCheckedKeys = new ArrayList<>(), listExpandedKeys = new ArrayList<>();
        for (MenuResponse menu : list) {
            if (menuService.isChecked(menu)) {
                listCheckedKeys.add(menu.getId());
            }
            if (menuService.isDisplay(menu)) {
                listExpandedKeys.add(menu.getId());
            }
            for (MenuResponse cMenu : menu.getChildren()) {
                if (cMenu.getChecked()) {
                    listCheckedKeys.add(cMenu.getId());
                }
            }
        }

        map.put("list", list);
        map.put("checkedKeys", listCheckedKeys);
        map.put("expandedKeys", listExpandedKeys);
        return map;
    }

    /**
     * 获取给定角色的菜单列表
     * @param roleIds 角色ID
     * @return 角色的菜单
     * @throws IOException -
     */
    public List<MenuResponse> getMenuByRoleList(List<Integer> roleIds) throws IOException {
        List<MenuResponse> list = menuService.getMenuByParentId(BackendConst.PARENT_ID_DEFAULT);
        if (roleIds.contains(BackendConst.ROLE_SUPER_ADMIN)) {
            return list;
        }
        list = this.checkMenuPrivilege(list, roleIds);
        List<MenuResponse> removeList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (!menuService.isDisplay(list.get(i))) {
                ConsoleUtil.formatPrint("i = " + i);
                removeList.add(list.get(i));
                continue;
            }
            List<MenuResponse> listChildren = list.get(i).getChildren();
            List<MenuResponse> removeChildrenList = new ArrayList<>();
            for (int j = 0; j < listChildren.size(); j++) {
                if (!menuService.isDisplay(listChildren.get(j))) {
                    ConsoleUtil.formatPrint("j = " + j);
                    removeChildrenList.add(listChildren.get(j));
                }
            }
            for (MenuResponse menu : removeChildrenList) {
                listChildren.remove(menu);
            }
        }
        for (MenuResponse menu : removeList) {
            list.remove(menu);
        }

        return list;
    }

    /**
     * 检查角色对菜单的权限
     * @param menuList 菜单
     * @param roleIds 角色ID
     * @return array
     */
    private List<MenuResponse> checkMenuPrivilege(List<MenuResponse> menuList, List<Integer> roleIds) {
        for (MenuResponse menu : menuList) {
            menu.setChecked(this.hasRoleListPrivilege(roleIds, menu.getId()));
            menu.setChildren(this.checkMenuPrivilege(menu.getChildren(), roleIds));
        }
        return menuList;
    }

    /**
     * 验证角色是否有菜单权限
     * @param roleIds 角色ID
     * @param resourceId 菜单ID
     * @return true-有权限
     */
    private Boolean hasRoleListPrivilege(List<Integer> roleIds, Integer resourceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleIds", roleIds);
        map.put("resourceId", resourceId);
        return mapper.queryCountByRoleIdsAndResourceId(map) > 0;
    }

    @Transactional
    public Boolean savePrivilege(PrivilegeDTO dto) {
        //判断当前用户是否有权限保存角色和资源的关联关系
        this.checkOperatorPrivilege(dto.getRoleId(), dto.getResourceIds());

        // 获取当前角色的权限数组
        List<Resource> listOld = menuService.getPrivilegeListByRoleIdAndType(dto.getRoleId(), dto.getResourceType());

        List<Integer> resourceIds = new ArrayList<>();
        for (Resource resource : listOld) {
            resourceIds.add(resource.getResourceId());
        }
        // 删除当前角色的权限
        this.deleteByRoleAndResource(resourceIds, dto.getRoleId());

        // 重建新的权限关系
        for (Integer resourceId : dto.getResourceIds()) {
            Privilege privilege = new Privilege();
            privilege.setPrivilegeRoleId(dto.getRoleId());
            privilege.setPrivilegeResourceId(resourceId);
            privilege.setPrivilegeCreateAt(new Date());
            mapper.add(privilege);
        }

        // 去除后代角色与当前角色的资源差集
        this.removeDescendantResource(dto.getRoleId(), dto.getResourceType(), dto.getResourceIds());

        return true;

    }

    /**
     * 根据角色ID和权限IDs删除拥有的权限
     * @param resourceIds 权限IDs
     * @param roleId 角色ID
     * @return list
     */
    private Boolean deleteByRoleAndResource(List<Integer> resourceIds, Integer roleId) {
        if (resourceIds.size() <= 0 || roleId == null) {
            return true;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("resourceIds", resourceIds);
        return mapper.deleteByRoleIdAndResourceIds(map) > 0;
    }

    /**
     * 去除后代角色与当前角色的权限差集
     * @param roleId 角色ID
     * @param resourceType 角色类型
     * @param resourceIds 资源IDs
     */
    private void removeDescendantResource(Integer roleId, String resourceType, List<Integer> resourceIds) {
        List<Role> childrenRolesList = roleService.getListByParentId(roleId);

        for (Role adminRole : childrenRolesList) {
            List<Privilege> listPrivilege = mapper.queryListByRoleId(adminRole.getRoleId());
            for (Privilege privilege : listPrivilege) {
                Resource resource = menuService.getOneById(privilege.getPrivilegeResourceId());
                if ((resource != null) &&
                        resource.getResourceType().equals(resourceType) &&
                        !Collections.singletonList(resourceIds).contains(privilege.getPrivilegeResourceId())) {
                    mapper.delete(privilege.getPrivilegeId());
                }
                this.removeDescendantResource(adminRole.getRoleId(), resourceType, resourceIds);
            }
        }
    }

    /**
     * 校验可操作的权限
     * @param roleId 角色ID
     * @param resourceIds 权限IDs
     * @return boolean
     */
    private Boolean checkOperatorPrivilege(Integer roleId, List<Integer> resourceIds) {
        List<RoleUser> listRoleUser = roleUserService.getRolesByUserId(1);
        Role ancestorRole = roleUserService.isAncestorRole(roleId, listRoleUser);
        if (ancestorRole == null) {
            return false;
        }
        return this.judgeResourceOptional(ancestorRole.getRoleId(), resourceIds);
    }

    /**
     * 判断资源是否都存在于给定角色的可操作资源列表
     * @param roleId 角色ID
     * @param listId 权限IDs
     * @return boolean
     */
    private Boolean judgeResourceOptional(Integer roleId, List<Integer> listId) {
        List<Privilege> list = mapper.queryListByRoleId(roleId);

        // 判断资源是否都存在于给定角色的可操作资源列表
        for (Privilege privilege : list) {
            for (Integer resourceId : listId) {
                if (!resourceId.equals(privilege.getPrivilegeId())) {
                    return false;
                }
            }
        }
        return true;
    }
}
