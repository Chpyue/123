package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : chpyue@foxmail.com
 * @date :2018/11/5 10:24
 * @Description: 安全配置类
 */

/**启用方法安全设置*/


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "ilemon.fun";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        // 设置密码加密方式
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

/**
 *  这部分代码存在异议-需百度
 *  bug
 */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
            return super.userDetailsService();
    }
    /**
     * 自定义配置
     * *重写此方法便能将前端自动弹出的自动登录页面关闭
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                //都可以访问
                .antMatchers("/css/**","/js/**","/fonts/**","/assets/**",
                               "/fragments/**","/img/**","/index").permitAll()
                //需要相应角色才能访问
                .antMatchers("/admins/**").hasRole("ADMIN")
                .and()
                //基于 Form 表单的登录验证
                .formLogin()
                //  自定义登录界面
                .loginPage("/login").defaultSuccessUrl("/login-allot").failureUrl("/login-error")
                // 启用remember_me
                .and().rememberMe().key(KEY)
                //处理异常,拒绝访问就重定向到403页面
                .and().exceptionHandling().accessDeniedPage("/403");
//        //禁用 H2 控制台的CSRF 防护
//        httpSecurity.csrf().ignoringAntMatchers("/h2-console/**");
//        // 允许来自同一来源的H2 控制台的请求
//        httpSecurity.headers().frameOptions().sameOrigin();
    }

    /**
     * 认证信息管理
     * 备注: 该方法的名称可以任意\
     *       但是在类中必须要有
     *       *@EnableWebSecuriry
     *       *@EnableGlobalMethodSecurity
     *       *@EnableGlobalAuthentication
     *       这三个注解
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }
}

