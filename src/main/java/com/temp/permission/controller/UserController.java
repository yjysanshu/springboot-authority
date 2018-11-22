package com.temp.permission.controller;

import com.temp.permission.consts.ErrorConst;
import com.temp.permission.model.request.ChangeRequest;
import com.temp.permission.model.request.UserRequest;
import com.temp.permission.model.response.MenuResponse;
import com.temp.permission.service.MenuService;
import com.temp.permission.service.UserService;
import com.temp.permission.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/login")
    public Map login() {
        return FormatUtil.fail();
    }

    @RequestMapping(value = "/list")
    public Map list(UserRequest request) {
        return FormatUtil.success(FormatUtil.formatList(service.getList(request),
                service.getTotal(request)));
    }

    @RequestMapping("/save")
    public Map save(@RequestBody UserRequest request) {
        return FormatUtil.success(service.save(request));
    }

    @RequestMapping("/reset-pwd")
    public Map resetPwd() {
        return FormatUtil.success(service.resetPwd());
    }

    @RequestMapping("/change-pwd")
    public Map changePwd(@RequestBody ChangeRequest request) {
        int count = service.changePwd(request);
        return FormatUtil.success(count);
    }

    @RequestMapping(value = "/del")
    public Map delete(@RequestBody UserRequest request) {
        if (service.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail(ErrorConst.DB_DELETE_EXCEPTION, null);
    }

    @RequestMapping("/menu")
    public Map menu() throws IOException {
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
    public Map info() {
        return FormatUtil.success(service.getUserInfo());
    }
}
