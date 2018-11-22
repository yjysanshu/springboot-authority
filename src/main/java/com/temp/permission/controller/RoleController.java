package com.temp.permission.controller;

import com.temp.permission.model.request.PrivilegeRequest;
import com.temp.permission.model.request.RoleRequest;
import com.temp.permission.model.request.RoleUserRequest;
import com.temp.permission.service.PrivilegeService;
import com.temp.permission.service.RoleService;
import com.temp.permission.service.RoleUserService;
import com.temp.permission.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService service;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RoleUserService roleUserService;

    @RequestMapping("/list")
    public Map list() {
        return FormatUtil.success(service.manageRole());
    }

    @RequestMapping("/all")
    public Map all(@RequestBody RoleRequest request) {
        return FormatUtil.success(service.getList(request));
    }

    @RequestMapping("/get-menu")
    public Map getMenu(@RequestBody RoleRequest request) throws IOException {
        return FormatUtil.success(privilegeService.getRoleMenu(request.getId()));
    }

    @RequestMapping("/get-user")
    public Map getUser(@RequestBody RoleRequest request) {
        return FormatUtil.success(roleUserService.getRoleUser(request.getId()));
    }

    @RequestMapping("/save")
    public Map save(@RequestBody RoleRequest request) {
        return FormatUtil.success(service.save(request));
    }

    @RequestMapping("/save-user")
    public Map saveUser(@RequestBody RoleUserRequest request) {
        roleUserService.addRoleUser(request);
        return FormatUtil.success();
    }

    @RequestMapping("/save-privilege")
    public Map savePrivilege(@RequestBody PrivilegeRequest request) {
        return FormatUtil.success(privilegeService.savePrivilege(request));
    }

    @RequestMapping("/del")
    public Map delete(@RequestBody RoleRequest request) {
        if (service.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail();
    }
}