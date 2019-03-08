package com.temp.permission.controller;

import com.temp.common.model.ResponseData;
import com.temp.permission.model.request.RoleRequest;
import com.temp.permission.model.request.RoleUserRequest;
import com.temp.common.util.FormatUtil;
import com.temp.permission.model.request.PrivilegeDTO;
import com.temp.permission.service.PrivilegeService;
import com.temp.permission.service.RoleService;
import com.temp.permission.service.RoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Api(description = "角色信息模块")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService service;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RoleUserService roleUserService;

    @ApiOperation(value = "用户角色信息列表", notes = "查询用户可管理的角色")
    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    public ResponseData list() {
        return FormatUtil.success(service.manageRole());
    }

    @ApiOperation(value = "角色信息列表", notes = "查询所有的角色信息")
    @RequestMapping(value = "/all", method = { RequestMethod.POST })
    public ResponseData all(@RequestBody RoleRequest request) {
        return FormatUtil.success(service.getList(request));
    }

    @ApiOperation(value = "角色的菜单信息", notes = "根据ID获取角色菜单信息")
    @RequestMapping(value = "/get-menu", method = { RequestMethod.POST })
    public ResponseData getMenu(@RequestBody RoleRequest request) throws IOException {
        return FormatUtil.success(privilegeService.getRoleMenu(request.getId()));
    }

    @ApiOperation(value = "角色的用户信息", notes = "根据ID获取角色的用户")
    @RequestMapping(value = "/get-user", method = { RequestMethod.POST })
    public ResponseData getUser(@RequestBody RoleRequest request) {
        return FormatUtil.success(roleUserService.getRoleUser(request.getId()));
    }

    @ApiOperation(value = "角色的资源信息", notes = "根据ID获取角色的资源")
    @RequestMapping(value = "/get-resource", method = { RequestMethod.POST })
    public ResponseData getResource(@RequestBody RoleRequest request) {
        return FormatUtil.success(roleUserService.getRoleResource(request.getId()));
    }

    @ApiOperation(value = "新增或修改角色信息", notes = "根据ID确定是否存在角色信息，进行新增或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public ResponseData save(@RequestBody RoleRequest request) {
        return FormatUtil.success(service.save(request));
    }

    @ApiOperation(value = "保存角色的用户", notes = "根据ID保存对应的角色")
    @RequestMapping(value = "/save-user", method = { RequestMethod.POST })
    public ResponseData saveUser(@RequestBody RoleUserRequest request) {
        roleUserService.addRoleUser(request);
        return FormatUtil.success();
    }

    @ApiOperation(value = "保存权限", notes = "保存权限")
    @RequestMapping(value = "/save-privilege", method = { RequestMethod.POST })
    public ResponseData savePrivilege(@RequestBody PrivilegeDTO dto) {
        return FormatUtil.success(privilegeService.savePrivilege(dto));
    }

    @ApiOperation(value = "删除角色信息", notes = "根据ID删除角色信息")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody RoleRequest request) {
        if (service.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail();
    }
}