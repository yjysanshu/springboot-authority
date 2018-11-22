package com.temp.permission.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.temp.generator.util.FormatUtil;
import com.temp.permission.consts.ErrorConst;
import com.temp.permission.model.request.MenuRequest;
import com.temp.permission.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/list")
    public Map list(@RequestBody MenuRequest menuRequest) throws IOException {
        return FormatUtil.success(menuService.getList(menuRequest));
    }

    @RequestMapping("/father-menus")
    public Map fatherMenus() throws IOException {
        return FormatUtil.success(menuService.getFatherList());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map save(@RequestBody MenuRequest request) throws JsonProcessingException {
        return FormatUtil.success(menuService.save(request));
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Map delete(@RequestBody MenuRequest request) {
        if (menuService.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail(ErrorConst.DB_DELETE_EXCEPTION, null);
    }
}
