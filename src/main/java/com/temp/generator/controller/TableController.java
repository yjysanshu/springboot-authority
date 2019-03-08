package com.temp.generator.controller;

import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.generator.models.Table;
import com.temp.generator.models.request.TableRequest;
import com.temp.generator.service.TableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(description = "数据表模块")
@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @ApiOperation(value = "数据表字段列表", notes = "根据表名查询数据表字段信息")
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public ResponseData getTable(@RequestBody TableRequest request) {
        List<Table> list = tableService.getTableInfo(request.getTableName());
        return FormatUtil.success(FormatUtil.formatList(list, list.size()));
    }
}
