package com.temp.permission.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.temp.permission.domain.dto.MenuDTO;
import com.temp.permission.domain.vo.MenuVO;
import com.temp.permission.entity.Resource;

import java.io.IOException;
import java.util.List;

public interface MenuService {
    /**
     * 获取显示的菜单
     * @param dto 请求信息
     * @return 菜单信息
     * @throws IOException json解析错误
     */
    public List<MenuDTO> getList(MenuDTO dto) throws IOException;

    /**
     * 获取所有的父级菜单
     * @return 菜单信息
     * @throws IOException json解析错误
     */
    public List<MenuDTO> getFatherList() throws IOException;
    /**
     * 获取用户菜单
     * @return 菜单信息
     * @throws IOException -
     */
    public List<MenuVO> getMenuByUser() throws IOException;

    /**
     * 递归获取菜单
     * @param parentId 父级ID
     * @return 菜单
     * @throws IOException 解析JSON错误
     */
    public List<MenuVO> getMenuByParentId(Integer parentId) throws IOException;

    /**
     * 查询本地list信息
     * @param parentId -
     * @return -
     */
    public List<Resource> getMenuByListAndParentId(Integer parentId);

    /**
     * 是否展示菜单: 当所有子菜单都未被勾选时，不展示当前菜单，返回 false
     *              其他情况都需要展示菜单，均返回 true
     * @param menu 菜单
     * @return true-展示菜单
     */
    public Boolean isDisplay(MenuVO menu);

    /**
     * 是否选中父菜单
     * @param menu -
     * @return -
     */
    public Boolean isChecked(MenuVO menu);

    /**
     * 获取授权的资源信息
     * @param roleId
     * @param resourceType
     * @return
     */
    public List<Resource> getPrivilegeListByRoleIdAndType(Integer roleId, String resourceType);

    /**
     * 获取一个菜单或者权限信息
     * @param id
     * @return
     */
    public Resource getOneById(Integer id);

    /**
     * 保存菜单
     * @param menu 请求信息
     * @return 受影响的行数
     * @throws JsonProcessingException 解析JSON错误
     */
    public Integer save(MenuDTO menu) throws JsonProcessingException;

    /**
     * 删除菜单
     * @param id 资源ID
     * @return 受影响的行数
     */
    public Integer delete(Integer id);
}
