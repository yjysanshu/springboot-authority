package com.temp.generator.controller;

import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import com.temp.generator.execption.GeneratorException;
import com.temp.generator.models.request.GeneratorRequest;
import com.temp.generator.service.TableService;
import com.temp.generator.util.ActionFactory;
import com.temp.generator.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(description = "代码生成模块")
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private TableService tableService;

    @ApiOperation(value = "代码生成器", notes = "根据需要的行为生成代码")
    @RequestMapping(value = "/index", method = { RequestMethod.POST })
    public ResponseData index(@RequestBody GeneratorRequest request) throws GeneratorException {
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
