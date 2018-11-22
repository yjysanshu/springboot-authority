package com.temp.permission.controller;

import com.temp.permission.model.request.RoleUserRequest;
import com.temp.permission.service.RoleUserService;
import com.temp.permission.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/roleUser")
public class RoleUserController {

    @Autowired
    private RoleUserService service;

    @RequestMapping("/list")
    public Map list(@RequestBody RoleUserRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", service.getList(request));
        map.put("total", service.getTotal(request));
        return FormatUtil.success(map);
    }

    @RequestMapping("/save")
    public Map save(@RequestBody RoleUserRequest request) {
        return FormatUtil.success(service.save(request));
    }

    @RequestMapping("/del")
    public Map delete(@RequestBody RoleUserRequest request) {
        if (service.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail();
    }
}