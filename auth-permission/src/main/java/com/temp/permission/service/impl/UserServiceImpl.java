package com.temp.permission.service.impl;

import com.temp.permission.consts.CommonConst;
import com.temp.permission.domain.dto.UserChangeDTO;
import com.temp.permission.domain.dto.UserDTO;
import com.temp.permission.domain.vo.UserVO;
import com.temp.permission.entity.Role;
import com.temp.permission.entity.User;
import com.temp.permission.mapper.UserMapper;
import com.temp.permission.model.request.ChangeRequest;
import com.temp.common.util.ConsoleUtil;
import com.temp.permission.service.UserService;
import com.temp.permission.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserMapper repo;

    @Autowired
    private RoleUserServiceImpl roleUserService;

    /**
     * 获取所有的用户信息-前端显示
     * @param dto AdminUserDTO
     * @return 所有的用户信息-前端显示
     */
    public List<UserVO> getList(UserDTO dto) {
        User userSearch = formatModelDetail(dto);
        Map<String, Object> map = new HashMap<>();
        map.put("user", userSearch);
        map.put("page", dto.getCurrentPage());
        map.put("size", dto.getLimit());
        List<User> userList = repo.queryPageList(map);
        List<UserVO> list = new ArrayList<>();
        for (User user : userList) {
            UserVO userResponse = formatResponseDetail(user);
            list.add(userResponse);
        }
        return list;
    }

    /**
     * 根据条件查询总数
     * @param request
     * @return
     */
    public Integer getTotal(UserDTO request) {
        User adminUserSearch = formatModelDetail(request);
        return repo.queryCount(adminUserSearch);
    }

    /**
     * 获取用户信息
     * @return -
     */
    public UserVO getUserInfo() {
        User user = this.getCurrentUser();
        return formatResponseDetail(user);
    }

    /**
     * 根据角色获取用户
     * @param roleId Integer
     * @return 角色对应的用户
     */
    public List<User> getUserByRoleId(Integer roleId) {
        return repo.queryListByRoleId(roleId);
    }

    /**
     * 获取所有的用户信息
     * @return 所有用户信息
     */
    public List<User> getAll() {
        return repo.queryList(new User());
    }


    @Transactional
    public Integer save(UserDTO request) {
        User user;
        if (request.getId() != null) {
            user = repo.queryOne(request.getId());
        } else {
            user = new User();
            user.setUserCreateAt(new Date());
            String password = new BCryptPasswordEncoder().encode(CommonConst.INITIAL_PASSWORD);
            user.setUserPassword(password);
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
            repo.update(user);
        } else {
            repo.add(user);
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
        User user = repo.queryOne(userId);
        String password = new BCryptPasswordEncoder().encode(CommonConst.INITIAL_PASSWORD);
        user.setUserPassword(password);
        return repo.update(user);
    }

    @Override
    public Integer changePwd(UserChangeDTO request) {
        return null;
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
            count = repo.update(user);
        }
        return count;
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 受影响的行数
     */
    public Integer delete(Integer id) {
        return repo.delete(id);
    }

    private UserVO formatResponseDetail(User user) {
        UserVO response = new UserVO();
        response.setId(user.getUserId());
        response.setPhone(user.getUserPhone());
        response.setName(user.getUserName());
        response.setEmail(user.getUserEmail());
        response.setPassword(user.getUserPassword());
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

    private User formatModelDetail(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getId());
        user.setUserPhone(dto.getPhone());
        user.setUserName(dto.getName());
        user.setUserEmail(dto.getEmail());
        user.setUserPassword(dto.getPassword());
        user.setUserAvatar(dto.getAvatar());
        user.setUserLoginCount(dto.getLoginCount());
        user.setUserLastIp(dto.getLastIp());
        user.setUserStatus(dto.getStatus());
        user.setUserCreateAt(dto.getCreateAt());
        user.setUserUpdateAt(dto.getUpdateAt());
        return user;
    }
}
