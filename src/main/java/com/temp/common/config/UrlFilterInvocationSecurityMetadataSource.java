package com.temp.common.config;

import com.temp.permission.consts.BackendConst;
import com.temp.permission.entity.Resource;
import com.temp.permission.entity.Role;
import com.temp.permission.model.OauthUser;
import com.temp.permission.service.LoginService;
import com.temp.permission.service.ResourceService;
import com.temp.permission.util.ConsoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private LoginService loginService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private HttpServletRequest request;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        if ("/user/login".equals(requestUrl)) {
            return null;
        }

        String token = request.getHeader("X-TOKEN");

        if (token != null) {
            OauthUser oauthUser = (OauthUser) this.loginService.loadUserByToken(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    oauthUser, null, oauthUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                    request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ConsoleUtil.formatPrint(authentication);
        } else {
            if (request.getMethod().equals("OPTIONS")) {
                return null;
            }
        }
        ConsoleUtil.formatPrint("request-url: " + requestUrl);
        List<Resource> resourcesList = resourceService.getAllByType(BackendConst.RESOURCE_TYPE_API);
        for (Resource resource : resourcesList) {
            List<Role> roleList = resource.getRoleList();
            ConsoleUtil.formatPrint("database-url: " + resource.getResourceTarget());
            ConsoleUtil.formatPrint("roleList.size(): " + roleList.size());
            if (antPathMatcher.match(resource.getResourceTarget(), requestUrl) && roleList.size() > 0) {
                int size = roleList.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roleList.get(i).getRoleName();
                }
                ConsoleUtil.formatPrint("array value: " + values.toString());
                return SecurityConfig.createList(values);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
