package com.temp.generator.controller;

import com.temp.generator.models.Table;
import com.temp.generator.models.request.TableRequest;
import com.temp.generator.service.TableService;
import com.temp.generator.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @RequestMapping("/list")
    public Map getTable(@RequestBody TableRequest request) {
        List<Table> list = tableService.getTableInfo(request.getTableName());
        return FormatUtil.success(FormatUtil.formatList(list, list.size()));
    }
}
