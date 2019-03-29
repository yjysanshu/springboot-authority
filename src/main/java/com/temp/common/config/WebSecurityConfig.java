package com.temp.common.config;

import com.temp.common.consts.Constants;
import com.temp.common.model.ResponseData;
import com.temp.permission.model.OauthUser;
import com.temp.permission.consts.CharacterConst;
import com.temp.permission.consts.CommonConst;
import com.temp.permission.consts.HeaderConst;
import com.temp.permission.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsUtils;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginService loginService;
    @Autowired
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/index.html", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                .and().formLogin().loginPage("/user/login")
                .usernameParameter("phone")
                .passwordParameter("password").permitAll()
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType(HeaderConst.CONTENT_TYPE);
                    httpServletResponse.setHeader(HeaderConst.ACCESS_CONTROL_ALLOW_ORIGIN, CharacterConst.CHARACTER_ARBITRARILY);
                    PrintWriter out = httpServletResponse.getWriter();
                    ResponseData responseData = new ResponseData(Constants.LOGIN_FAIL);
                    if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                        responseData.setMessage(Constants.LOGIN_PARAMS_ERROR.getMsg());
                    } else if (e instanceof DisabledException) {
                        responseData.setMessage(Constants.LOGIN_USER_DISABLE.getMsg());
                    }
                    out.write(responseData.toJSONString());
                    out.flush();
                    out.close();
                })
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType(HeaderConst.CONTENT_TYPE);
                    httpServletResponse.setHeader(HeaderConst.ACCESS_CONTROL_ALLOW_ORIGIN, CharacterConst.CHARACTER_ARBITRARILY);
                    PrintWriter out = httpServletResponse.getWriter();
                    ResponseData response = new ResponseData(Constants.SUCCESS);
                    Map<String, Object> map = new HashMap<>();
                    map.put(CommonConst.RESPONSE_TOKEN, getCurrentFilterUser().getUserToken());
                    response.setData(map);
                    out.write(response.toJSONString());
                    out.flush();
                    out.close();
                })
                .and().logout().permitAll()
                .and().csrf().disable().cors()
                .and().exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler);
    }

    public static OauthUser getCurrentFilterUser() {
        return (OauthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
