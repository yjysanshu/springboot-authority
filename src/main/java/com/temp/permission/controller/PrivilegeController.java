package com.temp.permission.controller;

import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.permission.model.request.PrivilegeDTO;
import com.temp.permission.service.PrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "资源权限模块")
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService service;

    @ApiOperation(value = "资源权限列表", notes = "根据参数查询所有的资源信息")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public ResponseData list(@RequestBody PrivilegeDTO request) {
        Map<String, Object> map = new HashMap<>();
        return FormatUtil.success(map);
    }

    @ApiOperation(value = "新增或修改资源权限信息", notes = "根据ID确定是否存在资源权限信息，进行新增或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public ResponseData save(@RequestBody PrivilegeDTO request) {
        return null;
    }

    @ApiOperation(value = "删除资源权限信息", notes = "根据ID删除资源权限信息")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody PrivilegeDTO request) {
        return FormatUtil.fail();
    }
}