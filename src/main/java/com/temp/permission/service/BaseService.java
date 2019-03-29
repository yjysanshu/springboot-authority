package com.temp.permission.service;

import com.temp.permission.consts.HeaderConst;
import com.temp.permission.entity.User;
import com.temp.permission.mapper.OauthUserMapper;
import com.temp.permission.mapper.UserMapper;
import com.temp.permission.model.OauthUser;
import com.temp.permission.util.ConsoleUtil;
import com.temp.permission.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OauthUserMapper oauthUserMapper;

    public User getCurrentUser() {
        String token = request.getHeader(HeaderConst.X_TOKEN);
        return userMapper.queryOneByToken(token);
    }

    public String getCurrentUserToken() {
        return request.getHeader(HeaderConst.X_TOKEN);
    }

    public OauthUser getUser() {
        String token = request.getHeader(HeaderConst.X_TOKEN);
        if (token == null) {
            return null;
        }
        ConsoleUtil.formatPrint(token);
        return oauthUserMapper.queryOneByToken(token);
    }

    String getIpAddress() {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        ConsoleUtil.formatPrint(XFor);
        return XFor;
    }
}
