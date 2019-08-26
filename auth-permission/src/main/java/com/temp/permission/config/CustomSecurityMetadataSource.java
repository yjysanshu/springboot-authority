package com.temp.permission.config;

import com.temp.common.util.ConsoleUtil;
import com.temp.permission.consts.BackendConst;
import com.temp.permission.entity.Resource;
import com.temp.permission.entity.Role;
import com.temp.permission.model.OauthUser;
import com.temp.permission.service.impl.LoginServiceImpl;
import com.temp.permission.service.impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

//@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private ResourceServiceImpl resourceService;

    @Autowired
    private HttpServletRequest request;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        System.out.println("xxxxxxxxx");
        System.out.println(requestUrl);

        String token = request.getHeader("X-TOKEN");

        //根据前端的token来验证用户的登陆信息
        if (token != null) {
            OauthUser oauthUser = (OauthUser) this.loginService.loadUserByToken(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    oauthUser, null, oauthUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                    request));
            //把用户设置到权限验证的上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //如果是超级管理员直接返回
            List<Role> list = oauthUser.getResourceList();
            for (Role role : list) {
                if (role.getRoleId() == BackendConst.ROLE_SUPER_ADMIN) {
                    return null;
                }
            }
            ConsoleUtil.formatPrint(authentication);
        } else {
            if (request.getMethod().equals("OPTIONS")) {
                return null;
            }
        }
        ConsoleUtil.formatPrint("request-url: " + requestUrl);

        //查询有角色关联了的接口，这样的接口说明其是需要权限验证的
        List<Resource> resourcesList = resourceService.getAllByType(BackendConst.RESOURCE_TYPE_API);
        for (Resource resource : resourcesList) {
            List<Role> roleList = resource.getRoleList();
            ConsoleUtil.formatPrint("database-url: " + resource.getResourceTarget());
            ConsoleUtil.formatPrint("roleList.size(): " + roleList.size());
            if (antPathMatcher.match(resource.getResourceTarget(), requestUrl) && roleList.size() > 0) {
                int size = roleList.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roleList.get(i).getRoleName();  //拥有这个接口权限的角色，赋值到value
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
