package com.temp.permission.controller;

import com.temp.common.consts.Constants;
import com.temp.common.exception.UserException;
import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.permission.domain.dto.LoginDTO;
import com.temp.permission.domain.dto.UserChangeDTO;
import com.temp.permission.domain.dto.UserDTO;
import com.temp.permission.domain.vo.MenuVO;
import com.temp.permission.model.dto.param.ParamIdDTO;
import com.temp.permission.service.LoginService;
import com.temp.permission.service.MenuService;
import com.temp.permission.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(description = "后台用户模块")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService service;

    @Autowired
    private MenuService menuService;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录接口", notes = "用户登录，返回TOKEN信息")
    @PostMapping(value = "/login")
    public ResponseData login(@RequestBody LoginDTO dto) throws UserException {
        return FormatUtil.success(loginService.login(dto));
    }

    @ApiOperation(value = "用户信息列表", notes = "根据参数查询用户信息列表")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public ResponseData list(UserDTO request) {
        return FormatUtil.success(FormatUtil.formatList(service.getList(request),
                service.getTotal(request)));
    }

    @ApiOperation(value = "新增或修改用户信息", notes = "根据ID确定是否存在用户信息，进行新增或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public ResponseData save(@RequestBody UserDTO request) {
        return FormatUtil.success(service.save(request));
    }

    @ApiOperation(value = "重置密码", notes = "根据用户ID重置用户密码")
    @RequestMapping(value = "/reset-pwd", method = { RequestMethod.POST })
    public ResponseData resetPwd(@RequestBody ParamIdDTO container) {
        return FormatUtil.success(service.resetPwd(container.getId()));
    }

    @ApiOperation(value = "修改密码", notes = "根据信息修改用户密码")
    @RequestMapping(value = "/change-pwd", method = { RequestMethod.POST })
    public ResponseData changePwd(@RequestBody UserChangeDTO request) {
        int count = service.changePwd(request);
        return FormatUtil.success(count);
    }

    @ApiOperation(value = "删除用户信息", notes = "根据ID删除用户信息")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody UserDTO request) {
        if (service.delete(request.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail(Constants.DB_DELETE_ERROR, null);
    }

    @ApiOperation(value = "导航菜单", notes = "根据用户信息查询导航菜单")
    @RequestMapping(value = "/menu", method = { RequestMethod.GET })
    public ResponseData menu() throws IOException {
        List<MenuVO> list = menuService.getMenuByUser();
        //调整父菜单的顺序
        list.sort((o1, o2) -> o2.getSeq().compareTo(o1.getSeq()));

        //调整子菜单的顺序
        for (MenuVO menu : list) {
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
