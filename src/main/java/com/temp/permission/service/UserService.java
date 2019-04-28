package com.temp.permission.service;

import com.temp.permission.consts.CommonConst;
import com.temp.permission.entity.Role;
import com.temp.permission.entity.User;
import com.temp.permission.mapper.UserMapper;
import com.temp.permission.model.request.ChangeRequest;
import com.temp.permission.model.request.UserRequest;
import com.temp.permission.model.response.UserResponse;
import com.temp.permission.util.CodeUtil;
import com.temp.permission.util.ConsoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RoleUserService roleUserService;

    /**
     * 获取所有的用户信息-前端显示
     * @param request AdminUserRequest
     * @return 所有的用户信息-前端显示
     */
    public List<UserResponse> getList(UserRequest request) {
        User userSearch = formatModelDetail(request);
        Map<String, Object> map = new HashMap<>();
        map.put("user", userSearch);
        map.put("page", request.getCurrentPage());
        map.put("size", request.getLimit());
        List<User> userList = mapper.queryPageList(map);
        List<UserResponse> list = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResponse = formatResponseDetail(user);
            list.add(userResponse);
        }
        return list;
    }

    /**
     * 根据条件查询总数
     * @param request
     * @return
     */
    public Integer getTotal(UserRequest request) {
        User adminUserSearch = formatModelDetail(request);
        return mapper.queryCount(adminUserSearch);
    }

    /**
     * 获取用户信息
     * @return -
     */
    public UserResponse getUserInfo() {
        User user = this.getCurrentUser();
        return formatResponseDetail(user);
    }

    /**
     * 根据角色获取用户
     * @param roleId Integer
     * @return 角色对应的用户
     */
    public List<User> getUserByRoleId(Integer roleId) {
        return mapper.queryListByRoleId(roleId);
    }

    /**
     * 获取所有的用户信息
     * @return 所有用户信息
     */
    public List<User> getAll() {
        return mapper.queryList(new User());
    }


    @Transactional
    public Integer save(UserRequest request) {
        User user;
        if (request.getId() != null) {
            user = mapper.queryOne(request.getId());
        } else {
            user = new User();
            user.setUserCreateAt(new Date());
            String password = new BCryptPasswordEncoder().encode(CommonConst.INITIAL_PASSWORD);
            user.setUserPassword(password);
            user.setUserToken(CodeUtil.createUUID());
            user.setUserLoginCount(1);
        }
        user.setUserPhone(request.getPhone());
        user.setUserName(request.getName());
        user.setUserEmail(request.getEmail());
        user.setUserAvatar(request.getAvatar());
        user.setUserStatus(request.getStatus());
        user.setUserLastIp(this.getIpAddress());
        ConsoleUtil.formatPrint(user);
        if (request.getId() != null) {
            mapper.update(user);
        } else {
            mapper.add(user);
        }

        //插入或修改用户的角色
        StringBuilder roleIds = new StringBuilder(",");
        for (Integer roleId : request.getRoles()) {
            roleIds.append(roleId).append(",");
        }
        roleUserService.addUserRole(user.getUserId(), roleIds.toString());
        return CommonConst.SUCCESS;
    }

    /**
     * 重置密码
     * @return -
     */
    public Integer resetPwd(int userId) {
        User user = mapper.queryOne(userId);
        String password = new BCryptPasswordEncoder().encode(CommonConst.INITIAL_PASSWORD);
        user.setUserPassword(password);
        user.setUserToken(CodeUtil.createUUID());
        return mapper.update(user);
    }

    /**
     * 修改信息
     * @return -
     */
    public Integer changePwd(ChangeRequest request) {
        ConsoleUtil.formatPrint(request);
        User user = this.getCurrentUser();
        int count = 0;
        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();
        String password = encrypt.encode(request.getNewPassword());
        if (encrypt.matches(request.getPassword(), user.getUserPassword())) {
            user.setUserAvatar(request.getPic());
            user.setUserPassword(password);
            user.setUserToken(CodeUtil.createUUID());
            count = mapper.update(user);
        }
        return count;
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 受影响的行数
     */
    public Integer delete(Integer id) {
        return mapper.delete(id);
    }

    private UserResponse formatResponseDetail(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getUserId());
        response.setPhone(user.getUserPhone());
        response.setName(user.getUserName());
        response.setEmail(user.getUserEmail());
        response.setPassword(user.getUserPassword());
        response.setToken(user.getUserToken());
        response.setAvatar(user.getUserAvatar());
        response.setLoginCount(user.getUserLoginCount());
        response.setLastIp(user.getUserLastIp());
        response.setStatus(user.getUserStatus());
        response.setCreateAt((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(user.getUserCreateAt()));
        response.setUpdateAt((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(user.getUserUpdateAt()));

        Map<Integer, String> map = new HashMap<>();
        if (user.getRoleList().size() > 0) {
            for (Role role : user.getRoleList()) {
                map.put(role.getRoleId(), role.getRoleName());
            }
            response.setRoles(map);
        }

        return response;
    }

    private User formatModelDetail(UserRequest request) {
        User user = new User();
        user.setUserId(request.getId());
        user.setUserPhone(request.getPhone());
        user.setUserName(request.getName());
        user.setUserEmail(request.getEmail());
        user.setUserPassword(request.getPassword());
        user.setUserToken(request.getToken());
        user.setUserAvatar(request.getAvatar());
        user.setUserLoginCount(request.getLoginCount());
        user.setUserLastIp(request.getLastIp());
        user.setUserStatus(request.getStatus());
        user.setUserCreateAt(request.getCreateAt());
        user.setUserUpdateAt(request.getUpdateAt());
        return user;
    }
}
