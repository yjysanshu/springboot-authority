package com.temp.generator.controller;

import com.temp.generator.execption.GeneratorException;
import com.temp.generator.models.request.GeneratorRequest;
import com.temp.generator.service.TableService;
import com.temp.generator.util.ActionFactory;
import com.temp.generator.util.FormatUtil;
import com.temp.generator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private TableService tableService;

    @RequestMapping("/index")
    public Map index(@RequestBody GeneratorRequest request) throws GeneratorException {
        Boolean isTrue = false;
        String tableName;
        if (request.isDeletePrefix()) {
            tableName = StringUtil.deleteOnePrefix(request.getTableName());
        } else {
            tableName = request.getTableName();
        }
        for (String action : request.getActions()) {
            isTrue = ActionFactory.getInstance(action).init(tableService.getTableInfo(request.getTableName()),
                    request.getPackageName(), tableName, request.getTableName()).handle();
        }
        if (isTrue) {
            return FormatUtil.success();
        }
        return FormatUtil.fail();
    }
}
