package com.temp.permission.service;

import com.temp.permission.domain.dto.UserChangeDTO;
import com.temp.permission.domain.dto.UserDTO;
import com.temp.permission.domain.vo.UserVO;
import com.temp.permission.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    /**
     * 获取所有的用户信息-前端显示
     * @param request AdminUserRequest
     * @return 所有的用户信息-前端显示
     */
    public List<UserVO> getList(UserDTO request);

    /**
     * 根据条件查询总数
     * @param request
     * @return
     */
    public Integer getTotal(UserDTO request);

    /**
     * 获取用户信息
     * @return -
     */
    public UserVO getUserInfo();

    /**
     * 根据角色获取用户
     * @param roleId Integer
     * @return 角色对应的用户
     */
    public List<User> getUserByRoleId(Integer roleId);

    /**
     * 获取所有的用户信息
     * @return 所有用户信息
     */
    public List<User> getAll();


    @Transactional
    public Integer save(UserDTO request);

    /**
     * 重置密码
     * @return -
     */
    public Integer resetPwd(int userId);

    /**
     * 修改信息
     * @return -
     */
    public Integer changePwd(UserChangeDTO request);

    /**
     * 删除用户
     * @param id 用户ID
     * @return 受影响的行数
     */
    public Integer delete(Integer id);
}
