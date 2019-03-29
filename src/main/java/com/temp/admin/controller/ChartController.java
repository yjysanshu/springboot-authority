package com.temp.admin.controller;

import com.temp.admin.dto.param.StartEndParamDTO;
import com.temp.common.model.ResponseData;
import com.temp.common.service.chart.ExampleChartService;
import com.temp.common.util.FormatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "echart图形展示模块")
@RestController("adminChartController")
@RequestMapping("/admin/chart")
public class ChartController {
    @Autowired
    private ExampleChartService service;

    @ApiOperation(value = "配置信息列表", notes = "根据参数查询配置信息")
    @RequestMapping(value = "/example", method = { RequestMethod.POST })
    public ResponseData list(@RequestBody StartEndParamDTO container) {
        return FormatUtil.success(service.initParam(container).handle());
    }
}
