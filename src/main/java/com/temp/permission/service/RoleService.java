package com.temp.permission.service;

import com.temp.permission.consts.BackendConst;
import com.temp.permission.entity.RoleUser;
import com.temp.permission.entity.User;
import com.temp.permission.model.request.RoleRequest;
import com.temp.permission.model.response.RoleResponse;
import com.temp.permission.mapper.RoleMapper;
import com.temp.permission.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RoleService extends BaseService {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private RoleUserService roleUserService;

    /**
     * 根据条件查找角色记录
     * @param request
     * @return
     */
    public List<RoleResponse> getList(RoleRequest request) {
        Role roleSearch = formatModelDetail(request);
        List<Role> roleList = mapper.queryList(roleSearch);
        List<RoleResponse> list = new ArrayList<>();
        for (Role role : roleList) {
            RoleResponse roleResponse = formatResponseDetail(role);
            list.add(roleResponse);
        }
        return list;
    }

    public Integer getTotal(RoleRequest request) {
        Role roleSearch = formatModelDetail(request);
        return mapper.queryCount(roleSearch);
    }

    public Role getOneById(Integer id) {
        return mapper.queryOne(id);
    }

    /**
     * 获取用户的角色
     * @return 用户的角色
     */
    public List<RoleResponse> manageRole() {
        User user = this.getCurrentUser();
        List<RoleUser> roleList = roleUserService.getRolesByUserId(user.getUserId());
        List<Role> groupRoleList = new ArrayList<>();
        for (RoleUser roleUser : roleList) {
            Role role = mapper.queryOne(roleUser.getRoleUserRoleId());

            if (role.getRoleType() == BackendConst.ROLE_TYPE_GROUP) {
                groupRoleList.add(role);
            }
        }

        return this.nestRole(groupRoleList, 0);
    }

    /**
     * 获取角色
     * @param groupRoleList
     * @param depth
     * @return
     */
    private List<RoleResponse> nestRole(List<Role> groupRoleList, Integer depth) {
        List<RoleResponse> list = new ArrayList<>();
        for (Role role : groupRoleList) {
            List<Role> roleList = mapper.queryListByParentId(role.getRoleId());

            RoleResponse roleResponse = formatResponseDetail(role);
            roleResponse.setLevel(depth);
            roleResponse.setIsExpand(false);

            if (roleList.size() > 0) {
                roleResponse.setIsParent(true);
                roleResponse.setChildren(this.nestRole(roleList, depth + 1));
            } else {
                roleResponse.setIsParent(false);
                roleResponse.setChildren(new ArrayList<>());
            }
            list.add(roleResponse);
        }
        return list;
    }

    /**
     * 通过roleId查询
     * @param roleIds
     * @return
     */
    public List<Role> getByRoleIds(List<Integer> roleIds) {
        if (roleIds.size() <= 0) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("roleIds", roleIds);
        return mapper.queryListByRoleIds(map);
    }

    public List<Role> getListByParentId(Integer parentId) {
        return mapper.queryListByParentId(parentId);
    }

    public Integer save(RoleRequest request) {
        Role role;
        if (request.getId() != null) {
            role = mapper.queryOne(request.getId());
        } else {
            role = new Role();
            role.setRoleCreateAt(new Date());
        }
        role.setRoleParentId(request.getParentId());
        role.setRoleName(request.getName());
        role.setRoleDesc(request.getDesc());
        role.setRoleStatus(request.getStatus());
        role.setRoleType(request.getType());
        if (request.getId() != null) {
            return mapper.update(role);
        } else {
            return mapper.add(role);
        }
    }

    public Integer delete(Integer id) {
        return mapper.delete(id);
    }

    private RoleResponse formatResponseDetail(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getRoleId());
        response.setParentId(role.getRoleParentId());
        response.setName(role.getRoleName());
        response.setDesc(role.getRoleDesc());
        response.setStatus(role.getRoleStatus());
        response.setType(role.getRoleType());
        response.setCreateAt((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(role.getRoleCreateAt()));
        response.setUpdateAt((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(role.getRoleUpdateAt()));
        return response;
    }

    private Role formatModelDetail(RoleRequest request) {
        Role role = new Role();
        role.setRoleId(request.getId());
        role.setRoleParentId(request.getParentId());
        role.setRoleName(request.getName());
        role.setRoleDesc(request.getDesc());
        role.setRoleStatus(request.getStatus());
        role.setRoleType(request.getType());
        role.setRoleCreateAt(request.getCreateAt());
        role.setRoleUpdateAt(request.getUpdateAt());
        return role;
    }
}