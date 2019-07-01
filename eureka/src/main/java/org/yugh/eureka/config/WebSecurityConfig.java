package org.yugh.eureka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * //
 * 1: 使用Spring Security不需要重写很多自定义的拦截代码
 * 2: 比如MVC的 WebMvcConfigurationSupport 或者 HandlerInterceptor
 *
 * @author: 余根海
 * @creation: 2019-06-30 19:14
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final AccessDeniedHandler accessDeniedHandler;
    private final EurekaAuthenticationEntryPoint eurekaAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(@Qualifier("eurekaAuthenticationAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler,
                             EurekaAuthenticationEntryPoint eurekaAuthenticationEntryPoint) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.eurekaAuthenticationEntryPoint = eurekaAuthenticationEntryPoint;
    }


    /**
     * 非http://localhost:8010/和健康检查http://localhost:8010/actuator，其他统一不允许
     *
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(eurekaAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/", "/actuator/**")
                .permitAll()
                .anyRequest().authenticated();
        http.headers().cacheControl();
    }

    /**
     * 跳过静态资源防止页面异常
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "/favicon.ico",
                        "/eureka/**"
                );
    }

}
