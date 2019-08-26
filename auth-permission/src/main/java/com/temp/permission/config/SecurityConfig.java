package com.temp.permission.config;

import com.temp.common.config.AuthenticationAccessDeniedHandler;
import com.temp.common.config.UrlAccessDecisionManager;
import com.temp.permission.model.OauthUser;
import com.temp.permission.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginServiceImpl loginService;

//    @Autowired
//    CustomSecurityMetadataSource customSecurityMetadataSource;

    @Autowired
    private CustomAuthEntryPoint customAuthEntryPoint;

    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Autowired
    UserTokenAuthFilter userTokenAuthFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/index.html",
                "/static/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用 CSRF
                .csrf().disable()

                //授权异常
                .exceptionHandling().authenticationEntryPoint(customAuthEntryPoint).and()

                //不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                //过滤请求
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).anonymous()

                .antMatchers( HttpMethod.POST, "/auth/login").anonymous()
                //配置可访问的uri
                //.antMatchers(...).anonymous()

                // swagger start
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                // swagger end

                //SQL运行查看
                .antMatchers("/druid/**").anonymous()

//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                // 防止iframe 造成跨域
                .and().headers().frameOptions().disable()

                .and().addFilterBefore(userTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);

//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setSecurityMetadataSource(customSecurityMetadataSource);
//                        o.setAccessDecisionManager(urlAccessDecisionManager);
//                        return o;
//                    }
//                })
//                .and().formLogin().loginPage("/user/login")
//                .usernameParameter("phone")
//                .passwordParameter("password").permitAll()
//                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
//                    httpServletResponse.setContentType(HeaderConst.CONTENT_TYPE);
//                    httpServletResponse.setHeader(HeaderConst.ACCESS_CONTROL_ALLOW_ORIGIN, CharacterConst.CHARACTER_ARBITRARILY);
//                    PrintWriter out = httpServletResponse.getWriter();
//                    ResponseData responseData = new ResponseData(Constants.NEED_LOGIN_ERROR);
//                    if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
//                        responseData.setMessage(Constants.LOGIN_PARAMS_ERROR.getMsg());
//                    } else if (e instanceof DisabledException) {
//                        responseData.setMessage(Constants.LOGIN_USER_DISABLE.getMsg());
//                    }
//                    out.write(responseData.toJSONString());
//                    out.flush();
//                    out.close();
//                })
//                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
//                    httpServletResponse.setContentType(HeaderConst.CONTENT_TYPE);
//                    httpServletResponse.setHeader(HeaderConst.ACCESS_CONTROL_ALLOW_ORIGIN, CharacterConst.CHARACTER_ARBITRARILY);
//                    PrintWriter out = httpServletResponse.getWriter();
//                    //输出token
//                    String token = CodeUtil.genToken();
//                    OauthUser oauthUser = getCurrentFilterUser();
//                    RedisPoolUtil.set(token, oauthUser.getUserPhone(), 7200);
//                    ResponseData response = new ResponseData(Constants.SUCCESS);
//                    Map<String, Object> map = new HashMap<>();
//                    map.put(CommonConst.RESPONSE_TOKEN, token);
//                    response.setData(map);
//                    out.write(response.toJSONString());
//                    out.flush();
//                    out.close();
//                })
//                .and().logout().permitAll()
//                .and().cors();
    }

    public static OauthUser getCurrentFilterUser() {
        return (OauthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
