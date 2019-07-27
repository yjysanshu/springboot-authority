package com.temp.common.exception;

import com.temp.common.consts.Constants;
import com.temp.common.model.ResponseData;
import com.temp.common.util.FormatUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public ResponseData defaultErrorHandler(HttpServletRequest req, UserException e) throws Exception {
        return FormatUtil.fail(Constants.FAIL, e.getMessage(), null);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseData defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return new ResponseData(Constants.SYSTEM_ERROR);
    }
}