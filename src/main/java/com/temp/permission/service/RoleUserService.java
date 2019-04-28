package com.temp.permission.service;

import com.temp.permission.consts.BackendConst;
import com.temp.permission.entity.Resource;
import com.temp.permission.entity.Role;
import com.temp.permission.entity.User;
import com.temp.permission.model.dto.ResourceDTO;
import com.temp.permission.model.request.RoleUserRequest;
import com.temp.permission.model.response.RoleUserResponse;
import com.temp.permission.mapper.RoleUserMapper;
import com.temp.permission.entity.RoleUser;
import com.temp.permission.util.ConsoleUtil;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Logger
@Service
public class RoleUserService {

    @Autowired
    private RoleUserMapper mapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private Mapper mapperTrans;

    /**
     * 添加用户的角色
     * @param userId -
     * @param roleIds -
     */
    public void addUserRole(Integer userId, String roleIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIds", roleIds);
        ConsoleUtil.formatPrint(roleIds);
        mapper.callProAddUserRole(map);
    }

    /**
     * 添加角色的用户
     * @param request -
     */
    public void addRoleUser(RoleUserRequest request) {
        StringBuilder userIds = new StringBuilder(",");
        for (Integer userId : request.getList()) {
            userIds.append(userId).append(",");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", request.getId());
        map.put("userIds", userIds.toString());
        ConsoleUtil.formatPrint(userIds.toString());
        mapper.callProAddRoleUser(map);
    }

    public List<RoleUser> getRolesByUserId(Integer userId) {
        return mapper.queryListByUserId(userId);
    }

    /**
     * 判断一个角色在给定角色列表中，是否有其父角色
     * @param roleId 角色ID
     * @param judgeRoleUserList   祖先列表
     * @return list
     */
    public Role isAncestorRole(Integer roleId, List<RoleUser> judgeRoleUserList) {
        Role role = roleService.getOneById(roleId);
        List<Integer> judgeRoleIds = new ArrayList<>();
        if (role == null) {
            return null;
        }
        for (RoleUser roleUser : judgeRoleUserList) {
            judgeRoleIds.add(roleUser.getRoleUserRoleId());
        }
        List<Role> judgeRoleList = roleService.getByRoleIds(judgeRoleIds);
        if (judgeRoleList.size() <= 0) {
            return null;
        }
        while (role != null) {
            for (Role judgeRole : judgeRoleList) {
                if (judgeRole.getRoleId().equals(role.getRoleId())) {
                    return judgeRole;
                }
            }
            role = roleService.getOneById(role.getRoleParentId());
        }
        return null;
    }

    /**
     * 获取当前角色的用户，以及可分配的用户
     * @return 用户信息
     */
    public Map<String, List<User>> getRoleUser(Integer roleId) {
        // 获取能够选择给定角色的用户列表
        List<User> optionalUserList = this.getOptionalUsers(roleId);

        // 获取拥有给定角色的用户列表
        List<User> currentUserList = userService.getUserByRoleId(roleId);
        Map<String, List<User>> map = new HashMap<>();
        map.put("all", optionalUserList);
        map.put("right", currentUserList);
        return map;
    }

    /**
     * 获取当前角色的资源信息
     * @param roleId 角色ID
     * @return 角色权限
     */
    public Map getRoleResource(Integer roleId) {
        Map<String, Object> map = new HashMap<>();
        List<ResourceDTO> list = new ArrayList<>();
        //获取角色的信息
        Role role = roleService.getOneById(roleId);

        //获取所有的顶级权限
        List<Resource> topMenuList = resourceService.getListByParentId(BackendConst.PARENT_ID_DEFAULT);

        List<Resource> parentMenuList; //父角色的菜单权限
        List<Resource> parentResourceList; //获取父角色拥有的接口资源权限
        if (role.getRoleParentId() == 1) {
            parentMenuList = resourceService.getListByType(BackendConst.RESOURCE_TYPE_MENU);
            parentResourceList = resourceService.getListByType(BackendConst.RESOURCE_TYPE_API);
        } else {
            parentMenuList = resourceService.getListByRoleIdType(role.getRoleParentId(), BackendConst.RESOURCE_TYPE_MENU);
            parentResourceList = resourceService.getListByRoleIdType(role.getRoleParentId(), BackendConst.RESOURCE_TYPE_API);
        }
ConsoleUtil.formatPrint(parentResourceList);
        //获取自身拥有的接口资源权限
        List<Resource> resourceList = resourceService.getListByRoleIdType(roleId, BackendConst.RESOURCE_TYPE_API);

        List<Integer> checkedList = new ArrayList<>(); //选中的接口权限

        for (Resource topMenu : topMenuList) { //循环顶级菜单
            ResourceDTO dto = mapperTrans.map(topMenu, ResourceDTO.class);
            if (parentMenuList != null)
                for (Resource parentMenu : parentMenuList) {    //循环菜单
                    if (parentMenu.getResourceParentId().equals(dto.getId())) {
                        ResourceDTO parentDto = mapperTrans.map(parentMenu, ResourceDTO.class);
                        if (parentResourceList != null)
                            for (Resource parentResource : parentResourceList) { //循环父菜单
                                if (parentResource.getResourceParentId().equals(parentDto.getId())) {
                                    ResourceDTO parentResourceDto = mapperTrans.map(parentResource, ResourceDTO.class);
                                    if (resourceList != null)
                                        for (Resource resource : resourceList) {    //自身拥有的菜单
                                            if (resource.getResourceId().equals(parentResourceDto.getId())) {
                                                parentResourceDto.setChecked(true);
                                                break;
                                            }
                                        }

                                    parentDto.getChildren().add(parentResourceDto);
                                }
                            }

                        dto.getChildren().add(parentDto);
                    }
                }
            list.add(dto);
        }

        //自身拥有的资源不为空，添加到选中的数组里面
        if (resourceList != null) resourceList.forEach(n -> checkedList.add(n.getResourceId()));
        map.put("list", list);
        map.put("checked", checkedList);

        return map;
    }

    /**
     * 获取能够选择给定角色的用户列表
     * @param roleId Integer
     * @return array
     */
    public List<User> getOptionalUsers(Integer roleId) {
        List<User> allUserList = userService.getAll();
        List<User> adminUserList = this.getAncestorRoleUsers(roleId);
        for (User adminUser : adminUserList) {
            allUserList.remove(adminUser);
        }
        return allUserList;
    }

    /**
     * 获取拥有祖先角色的用户列表
     * @param roleId Integer
     * @return array
     */
    public List<User> getAncestorRoleUsers(Integer roleId) {
        List<User> adminUserList = new ArrayList<>();
        Role role = roleService.getOneById(roleId);
        Role fatherAdminRole = roleService.getOneById(role.getRoleParentId());
        while(fatherAdminRole != null) {
            List<User> fatherUserList = userService.getUserByRoleId(fatherAdminRole.getRoleId());
            adminUserList.addAll(fatherUserList);
            fatherAdminRole = roleService.getOneById(fatherAdminRole.getRoleParentId());
        }
        return adminUserList;
    }

    public List<RoleUserResponse> getList(RoleUserRequest request) {
        RoleUser roleUserSearch = formatModelDetail(request);
        Map<String, Object> map = new HashMap<>();
        map.put("roleUser", roleUserSearch);
        map.put("page", request.getCurrentPage());
        map.put("size", request.getLimit());        List<RoleUser> roleUserList = mapper.queryList(roleUserSearch);
        List<RoleUserResponse> list = new ArrayList<>();
        for (RoleUser roleUser : roleUserList) {
            RoleUserResponse roleUserResponse = formatResponseDetail(roleUser);
            list.add(roleUserResponse);
        }
        return list;
    }

    public Integer getTotal(RoleUserRequest request) {
        RoleUser roleUserSearch = formatModelDetail(request);
        return mapper.queryCount(roleUserSearch);
    }

    public Integer save(RoleUserRequest request) {
        RoleUser roleUser;
        if (request.getId() != null) {
            roleUser = mapper.queryOne(request.getId());
        } else {
            roleUser = new RoleUser();
            roleUser.setRoleUserCreateAt(new Date());
        }
        roleUser.setRoleUserRoleId(request.getRoleId());
        roleUser.setRoleUserUserId(request.getUserId());
        if (request.getId() != null) {
            return mapper.update(roleUser);
        } else {
            return mapper.add(roleUser);
        }
    }

    public Integer delete(Integer id) {
        return mapper.delete(id);
    }

    private RoleUserResponse formatResponseDetail(RoleUser roleUser) {
        RoleUserResponse response = new RoleUserResponse();
        response.setId(roleUser.getRoleUserId());
        response.setRoleId(roleUser.getRoleUserRoleId());
        response.setUserId(roleUser.getRoleUserUserId());
        response.setCreateAt((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(roleUser.getRoleUserCreateAt()));
        response.setUpdateAt((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(roleUser.getRoleUserUpdateAt()));
        return response;
    }

    private RoleUser formatModelDetail(RoleUserRequest request) {
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleUserId(request.getId());
        roleUser.setRoleUserRoleId(request.getRoleId());
        roleUser.setRoleUserUserId(request.getUserId());
        roleUser.setRoleUserCreateAt(request.getCreateAt());
        roleUser.setRoleUserUpdateAt(request.getUpdateAt());
        return roleUser;
    }
}