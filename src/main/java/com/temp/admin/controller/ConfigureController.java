package com.temp.admin.controller;

import com.temp.admin.dto.param.ConfigureNamesDTO;
import com.temp.common.model.ResponseData;
import com.temp.admin.dto.ConfigureDTO;
import com.temp.common.service.ConfigureService;
import com.temp.common.util.FormatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(description = "系统参数配置模块")
@RestController("adminConfigureController")
@RequestMapping("/admin/configure")
public class ConfigureController {

    @Autowired
    private ConfigureService service;

    @ApiOperation(value = "配置信息列表", notes = "根据参数查询配置信息")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public ResponseData list(@RequestBody ConfigureDTO container) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", service.getPageList(container));
        map.put("total", service.getTotal(container));
        return FormatUtil.success(map);
    }

    @ApiOperation(value = "根据key获取参数的值", notes = "根据key获取参数的值：key 为数组类型")
    @RequestMapping(value = "/param-value", method = { RequestMethod.POST })
    public ResponseData getParamValue(@RequestBody ConfigureNamesDTO container) {
        return FormatUtil.success(service.getValueByNames(container.getNames()));
    }

    @ApiOperation(value = "新增或修改配置信息", notes = "根据ID确定修改还是新增配置信息")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public ResponseData save(@RequestBody ConfigureDTO container) {
        return FormatUtil.success(service.save(container));
    }

    @ApiOperation(value = "删除配置信息", notes = "根据ID删除配置信息")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody ConfigureDTO container) {
        if (service.delete(container.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail();
    }
}