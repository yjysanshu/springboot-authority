package com.temp.permission.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.temp.common.consts.Constants;
import com.temp.generator.util.FormatUtil;
import com.temp.permission.model.request.MenuRequest;
import com.temp.permission.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Api(description = "导航菜单模块")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "导航菜单列表", notes = "根据参数查询所有的菜单信息")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public Map list(@RequestBody MenuRequest menuRequest) throws IOException {
        return FormatUtil.success(menuService.getList(menuRequest));
    }

    @ApiOperation(value = "父级菜单列表", notes = "根据参数查询学生信息")
    @RequestMapping(value = "/father-menus", method = { RequestMethod.GET })
    public Map fatherMenus() throws IOException {
        return FormatUtil.success(menuService.getFatherList());
    }

    @ApiOperation(value = "新增或修改菜单信息", notes = "根据ID确定是否存在菜单信息，进行新增或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public Map save(@RequestBody MenuRequest request) throws JsonProcessingException {
        return FormatUtil.success(menuService.save(request));
    }

    @ApiOperation(value = "删除菜单信息", notes = "根据ID删除菜单信息")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public Map delete(@RequestBody MenuRequest request) {
        if (menuService.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail(Constants.DB_DELETE_ERROR.getCode(), null);
    }
}
