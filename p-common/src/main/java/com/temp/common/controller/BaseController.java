package com.temp.common.controller;

import com.temp.common.consts.Constants;
import com.temp.common.model.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    default ResponseData send(Exception e){
        ResponseData response = new ResponseData(Constants.FAIL);
        response.setMessage(e.getMessage());
        return response;
    }

    /**
     * 统一异常处理，实现内容可以个性化定制
     * @param ex 异常信息
     * @return 返回信息
     */
    @ExceptionHandler({ RuntimeException.class, Exception.class })
    default ResponseData exceptionHandler(Exception ex) {
        logger.error("Result exceptionHandler, detail：" + ex.getMessage(), ex);
        return send(ex);
    }
}
