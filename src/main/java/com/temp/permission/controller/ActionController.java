package com.temp.permission.controller;

import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.permission.model.dto.param.ParamActionDTO;
import com.temp.permission.model.dto.param.ParamIdDTO;
import com.temp.permission.model.dto.param.ParamStringDTO;
import com.temp.permission.service.ActionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @ApiOperation(value = "获取所有的controller类", notes = "获取所有的controller类")
    @RequestMapping(value = "/get-classes", method = { RequestMethod.GET })
    public ResponseData getClasses() {
        return FormatUtil.success(actionService.getClasses());
    }

    @ApiOperation(value = "获取controller类的接口信息", notes = "根据类名获取接口的信息")
    @RequestMapping(value = "/get-resources", method = { RequestMethod.POST })
    public ResponseData getResource(@RequestBody ParamStringDTO dto) throws ClassNotFoundException {
        return FormatUtil.success(actionService.getResource(dto.getStr()));
    }

    @ApiOperation(value = "获取接口资源信息", notes = "获取所有的资源接口信息")
    @RequestMapping(value = "/all", method = { RequestMethod.GET })
    public ResponseData all() {
        return FormatUtil.success(actionService.getAllPrivilege());
    }

    @ApiOperation(value = "获取接口资源信息", notes = "获取所有的资源接口信息")
    @RequestMapping(value = "/batch-save", method = { RequestMethod.POST })
    public ResponseData batchSave(@RequestBody ParamActionDTO dto) {
        return FormatUtil.success(actionService.batchSave(dto));
    }

    @ApiOperation(value = "删除接口URI资源", notes = "根据ID删除URI资源")
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody ParamIdDTO dto) {
        return FormatUtil.success(actionService.delete(dto.getId()));
    }
}
