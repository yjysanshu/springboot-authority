package com.temp.common.exception;

import com.temp.permission.util.ConsoleUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomException implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        if (e instanceof DataIntegrityViolationException) {
            map.put("message", "该角色尚有关联的资源或用户，删除失败!");
        } else {
            String message = e.getMessage() == null ? "系统异常" : e.getMessage();
            map.put("message", message);
        }
        e.printStackTrace();
        ConsoleUtil.formatPrint(e.getStackTrace());
        ConsoleUtil.formatPrint(e.getMessage());
        ConsoleUtil.formatPrint(e.getCause());
        mv.addAllObjects(map);
        return mv;
    }
}
