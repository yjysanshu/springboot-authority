package com.temp.common.config;

import com.temp.permission.consts.CharacterConst;
import com.temp.permission.consts.CommonConst;
import com.temp.permission.consts.ErrorConst;
import com.temp.permission.consts.HeaderConst;
import com.temp.permission.model.OauthUser;
import com.temp.permission.model.response.Result;
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
                    Result result = new Result();
                    result.setCode(ErrorConst.SYSTEM_EXCEPTION);
                    if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                        result.setMessage(ErrorConst.messageMap.get(ErrorConst.LOGIN_PARAMS_ERROR));
                    } else if (e instanceof DisabledException) {
                        result.setMessage(ErrorConst.messageMap.get(ErrorConst.LOGIN_USER_DISABLE));
                    } else {
                        result.setMessage(ErrorConst.messageMap.get(ErrorConst.LOGIN_FAIL));
                    }
                    out.write(result.toJson());
                    out.flush();
                    out.close();
                })
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType(HeaderConst.CONTENT_TYPE);
                    httpServletResponse.setHeader(HeaderConst.ACCESS_CONTROL_ALLOW_ORIGIN, CharacterConst.CHARACTER_ARBITRARILY);
                    PrintWriter out = httpServletResponse.getWriter();
                    Result result = new Result();
                    result.setCode(ErrorConst.NO_EXCEPTION);
                    result.setMessage(ErrorConst.messageMap.get(ErrorConst.NO_EXCEPTION));
                    Map<String, Object> map = new HashMap<>();
                    map.put(CommonConst.RESPONSE_TOKEN, getCurrentFilterUser().getUserToken());
                    result.setData(map);
                    out.write(result.toJson());
                    out.flush();
                    out.close();
                })
                .and().logout().permitAll()
                .and().csrf().disable().exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler);
    }

    public static OauthUser getCurrentFilterUser() {
        return (OauthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
