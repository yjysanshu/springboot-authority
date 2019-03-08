package com.temp.permission.controller;

import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.permission.model.dto.param.ParamIdDTO;
import com.temp.permission.model.request.PrivilegeDTO;
import com.temp.permission.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "资源模块")
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService service;

    @ApiOperation(value = "资源信息列表", notes = "根据参数查询所有的资源信息")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public ResponseData list(@RequestBody PrivilegeDTO request) {
        Map<String, Object> map = new HashMap<>();
        return FormatUtil.success(map);
    }

    @ApiOperation(value = "新增或修改资源信息", notes = "根据ID确定是否存在资源信息，进行新增或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public ResponseData save(@RequestBody PrivilegeDTO request) {
        return null;
    }

    @ApiOperation(value = "删除资源信息", notes = "根据ID删除资源信息")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody PrivilegeDTO request) {
        return FormatUtil.fail();
    }

    @RequestMapping(value = "/get-resource", method = { RequestMethod.POST })
    public ResponseData getResource(@RequestBody ParamIdDTO dto) {
        return FormatUtil.success(service.getAllByType(dto.getId()));
    }
}