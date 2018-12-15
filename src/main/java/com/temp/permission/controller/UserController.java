package com.temp.permission.controller;

import com.temp.common.consts.Constants;
import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.permission.model.request.ChangeRequest;
import com.temp.permission.model.request.UserRequest;
import com.temp.permission.model.response.MenuResponse;
import com.temp.permission.service.MenuService;
import com.temp.permission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/login")
    public ResponseData login() {
        return FormatUtil.fail();
    }

    @RequestMapping(value = "/list")
    public ResponseData list(UserRequest request) {
        return FormatUtil.success(FormatUtil.formatList(service.getList(request),
                service.getTotal(request)));
    }

    @RequestMapping("/save")
    public ResponseData save(@RequestBody UserRequest request) {
        return FormatUtil.success(service.save(request));
    }

    @RequestMapping("/reset-pwd")
    public ResponseData resetPwd() {
        return FormatUtil.success(service.resetPwd());
    }

    @RequestMapping("/change-pwd")
    public ResponseData changePwd(@RequestBody ChangeRequest request) {
        int count = service.changePwd(request);
        return FormatUtil.success(count);
    }

    @RequestMapping(value = "/del")
    public ResponseData delete(@RequestBody UserRequest request) {
        if (service.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail(Constants.DB_DELETE_ERROR, null);
    }

    @RequestMapping("/menu")
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

    @RequestMapping("/info")
    public ResponseData info() {
        return FormatUtil.success(service.getUserInfo());
    }
}
