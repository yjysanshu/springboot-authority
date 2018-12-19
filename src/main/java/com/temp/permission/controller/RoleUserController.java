package com.temp.permission.controller;

import com.temp.common.model.ResponseData;
import com.temp.permission.model.request.RoleUserRequest;
import com.temp.common.util.FormatUtil;
import com.temp.permission.service.RoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(description = "角色用户模块")
@RestController
@RequestMapping("/roleUser")
public class RoleUserController {

    @Autowired
    private RoleUserService service;

    @ApiOperation(value = "用户角色列表", notes = "根据")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public ResponseData list(@RequestBody RoleUserRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", service.getList(request));
        map.put("total", service.getTotal(request));
        return FormatUtil.success(map);
    }

    @ApiOperation(value = "保存用户角色", notes = "根据")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public ResponseData save(@RequestBody RoleUserRequest request) {
        return FormatUtil.success(service.save(request));
    }

    @ApiOperation(value = "删除用户角色", notes = "根据")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody RoleUserRequest request) {
        if (service.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail();
    }
}