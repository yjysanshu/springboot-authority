package com.temp.generator.controller;

import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.generator.models.request.DatabaseRequest;
import com.temp.generator.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(description = "数据库信息模块")
@RestController
@RequestMapping("/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @ApiOperation(value = "数据库中数据表列表", notes = "根据数据库名查询数据表列表")
    @RequestMapping(value = "/index", method = { RequestMethod.POST })
    public ResponseData index(@RequestBody DatabaseRequest request) {
        List list = databaseService.getDatabaseInfo(request.getDatabaseName());
        return FormatUtil.success(FormatUtil.formatList(list, list.size()));
    }

    @ApiOperation(value = "数据库列表", notes = "查询所有的数据库")
    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    public ResponseData list() {
        return FormatUtil.success(databaseService.getDatabases());
    }
}
