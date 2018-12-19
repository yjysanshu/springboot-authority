package com.temp.admin.controller;

import com.temp.common.model.ResponseData;
import com.temp.admin.dto.StudentDTO;
import com.temp.common.service.StudentService;
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

@Api(description = "学生管理模块")
@RestController("adminStudentController")
@RequestMapping("/admin/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @ApiOperation(value = "学生信息列表", notes = "根据参数查询学生信息")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public ResponseData list(@RequestBody StudentDTO container) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", service.getPageList(container));
        map.put("total", service.getTotal(container));
        return FormatUtil.success(map);
    }

    @ApiOperation(value = "新增或修改学生列表", notes = "根据ID确定是否存在学生信息，进行新增或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    public ResponseData save(@RequestBody StudentDTO container) {
        return FormatUtil.success(service.save(container));
    }

    @ApiOperation(value = "删除学生信息", notes = "根据ID删除学生信息")
    @RequestMapping(value = "/del", method = { RequestMethod.DELETE })
    public ResponseData delete(@RequestBody StudentDTO container) {
        if (service.delete(container.getId()) > 0) {
            return FormatUtil.success();
        }
        return FormatUtil.fail();
    }
}