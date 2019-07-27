package com.temp.permission.controller;

import com.temp.common.consts.Constants;
import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.permission.model.dto.param.ParamIdDTO;
import com.temp.permission.model.request.ChangeRequest;
import com.temp.permission.model.request.UserRequest;
import com.temp.permission.model.response.MenuResponse;
import com.temp.permission.service.MenuService;
import com.temp.permission.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Api(description = "后台用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "用户登录接口", notes = "登录授权交给 security 管理，这里没多大用")
    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    public ResponseData login() {
        return FormatUtil.fail();
    }

    @ApiOperation(value = "用户信息列表", notes = "根据参数查询用户信息列表")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public ResponseData list(UserRequest request) {
        return FormatUtil.success(FormatUtil.formatList(service.getList(request),
                service.getTotal(request)));
    }

    @ApiOperation(value = "新增或修改用户信息", notes = "根据ID确定是否存在用户信息，进行新增或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public ResponseData save(@RequestBody UserRequest request) {
        return FormatUtil.success(service.save(request));
    }

    @ApiOperation(value = "重置密码", notes = "根据用户ID重置用户密码")
    @RequestMapping(value = "/reset-pwd", method = { RequestMethod.POST })
    public ResponseData resetPwd(@RequestBody ParamIdDTO container) {
        return FormatUtil.success(service.resetPwd(container.getId()));
    }

    @ApiOperation(value = "修改密码", notes = "根据信息修改用户密码")
    @RequestMapping(value = "/change-pwd", method = { RequestMethod.POST })
    public ResponseData changePwd(@RequestBody ChangeRequest request) {
        int count = service.changePwd(request);
        return FormatUtil.success(count);
    }

    @ApiOperation(value = "删除用户信息", notes = "根据ID删除用户信息")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody UserRequest request) {
        if (service.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail(Constants.DB_DELETE_ERROR, null);
    }

    @ApiOperation(value = "导航菜单", notes = "根据用户信息查询导航菜单")
    @RequestMapping(value = "/menu", method = { RequestMethod.GET })
    public ResponseData menu() throws IOException {
        List<MenuResponse> list = menuService.getMenuByUser();
        //调整父菜单的顺序
        list.sort((o1, o2) -> o2.getSeq().compareTo(o1.getSeq()));

        //调整子菜单的顺序
        for (MenuResponse menu : list) {
            menu.getChildren().sort((o1, o2) -> o2.getSeq().compareTo(o1.getSeq()));
        }
        return FormatUtil.success(list);
    }

    @ApiOperation(value = "用户详细信息", notes = "根据用户 token 信息查询用户信息")
    @RequestMapping(value = "/info", method = { RequestMethod.GET })
    public ResponseData info() {
        return FormatUtil.success(service.getUserInfo());
    }
}
