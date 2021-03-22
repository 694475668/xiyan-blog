package com.xiyan.config;

import com.xiyan.filter.CustomAuthenticationFilter;
import com.xiyan.hander.UserLoginFailureHandler;
import com.xiyan.hander.UserLoginSuccessHandler;
import com.xiyan.hander.UserNotLoginHandler;
import com.xiyan.service.impl.CustomUserServiceImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
/**
 * @author bright
 * 开启权限注解,默认是关闭的 当@EnableGlobalMethodSecurity(prePostEnabled=true)的时候，@PreAuthorize可以使用
 * 这里主要@PreAuthorize, @PostAuthorize, @Secured这三个注解可以使用
 * 当@EnableGlobalMethodSecurity(securedEnabled=true)的时候，@Secured可以使用：
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Data
@ConfigurationProperties(prefix = "exclude")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 需要放行的路径
     */
    private String[] path;

    /**
     * 自定义登录成功处理器
     */
    final UserLoginSuccessHandler userLoginSuccessHandler;
    /**
     * 自定义登录失败处理器
     */
    final UserLoginFailureHandler userLoginFailureHandler;
    /**
     * 自定义未登录的处理器
     */
    final UserNotLoginHandler userNotLoginHandler;
    /**
     * 自定义登录逻辑验证器
     */
    final CustomUserServiceImpl customUserService;

    /**
     * 注入对象
     *
     * @param userLoginSuccessHandler
     * @param userLoginFailureHandler
     * @param userNotLoginHandler
     * @param customUserService
     */
    public SecurityConfig(UserLoginSuccessHandler userLoginSuccessHandler,
                          UserLoginFailureHandler userLoginFailureHandler,
                          UserNotLoginHandler userNotLoginHandler,
                          CustomUserServiceImpl customUserService) {
        this.userLoginSuccessHandler = userLoginSuccessHandler;
        this.userLoginFailureHandler = userLoginFailureHandler;
        this.userNotLoginHandler = userNotLoginHandler;
        this.customUserService = customUserService;
    }

    /**
     * 加密方式
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置登录验证逻辑
     * 密码编译:new BCryptPasswordEncoder().encode("123456")
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //(1)基于内存认证方式
        // auth.inMemoryAuthentication()
        // .withUser("ks").password("123456").roles("vip2","vip3")
        // .and()
        // .withUser("root").password("123456").roles("vip1","vip2","vip3");
        //(2)基于数据库认证方式
        // 这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(customUserService);
    }

    /**
     * hasRole([role])	用户拥有指定的角色时返回true(hasRole()默认会将配置中的 role 带有 ROLE_ 前缀再和用户的角色权限 进行对比)
     * hasAnyRole([role1,role2])	用户拥有任意一个指定中的角色时返回true
     * hasAuthority([auth])	同hasRole()但不添加前缀 ROLE_
     * hasAnyAuthority([auth1,auth2])	同hasAnyRole([auth1,auth2])，但不添加前缀 ROLE_
     * permitAll	永远返回true
     * denyAll	永远返回false
     * anonymous	当前用户时 anonymous(匿名、未认证)时返回true
     * rememberMe	当前用户时 rememberMe(记住登录) 时发挥true
     * authentication	当前登录用户的 authentication 对象
     * fullAuthticated	当前用户既不是 anonymous 也不是 rememberMe 时返回true（即正常认证登录时返回true）
     * hasIpAddress("192.168.1.0/24")	ip匹配时返回true
     * openidLogin()	用于基于 OpenId 的验证
     * headers()	将安全标头添加到响应
     * cors()	配置跨域资源共享（ CORS ）
     * sessionManagement()	允许配置会话管理
     * portMapper()	允许配置一个PortMapper(HttpSecurity#(getSharedObject(class)))，其他提供SecurityConfigurer的对象使用 PortMapper 从 HTTP 重定向到 HTTPS 或者从 HTTPS 重定向到 HTTP。默认情况下，Spring Security使用一个PortMapperImpl映射 HTTP 端口8080到 HTTPS 端口8443，HTTP 端口80到 HTTPS 端口443
     * jee()	配置基于容器的预认证。 在这种情况下，认证由Servlet容器管理
     * x509()	配置基于x509的认证
     * rememberMe	允许配置“记住我”的验证
     * authorizeRequests()	允许基于使用HttpServletRequest限制访问
     * requestCache()	允许配置请求缓存
     * exceptionHandling()	允许配置错误处理
     * securityContext()	在HttpServletRequests之间的SecurityContextHolder上设置SecurityContext的管理。 当使用WebSecurityConfigurerAdapter时，这将自动应用
     * servletApi()	将HttpServletRequest方法与在其上找到的值集成到SecurityContext中。 当使用WebSecurityConfigurerAdapter时，这将自动应用
     * csrf()	添加 CSRF 支持，使用WebSecurityConfigurerAdapter时，默认启用
     * logout()	添加退出登录支持。当使用WebSecurityConfigurerAdapter时，这将自动应用。默认情况是，访问URL"/ logout"，使HTTP Session无效来清除用户，清除已配置的任何#rememberMe()身份验证，清除SecurityContextHolder，然后重定向到"/login?success"
     * anonymous()	允许配置匿名用户的表示方法。 当与WebSecurityConfigurerAdapter结合使用时，这将自动应用。 默认情况下，匿名用户将使用org.springframework.security.authentication.AnonymousAuthenticationToken表示，并包含角色 "ROLE_ANONYMOUS"
     * formLogin()	指定支持基于表单的身份验证。如果未指定FormLoginConfigurer#loginPage(String)，则将生成默认登录页面
     * oauth2Login()	根据外部OAuth 2.0或OpenID Connect 1.0提供程序配置身份验证
     * requiresChannel()	配置通道安全。为了使该配置有用，必须提供至少一个到所需信道的映射
     * httpBasic()	配置 Http Basic 验证
     * addFilterAt()	在指定的Filter类的位置添加过滤器
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 从配置文件获取不用进行权限验证的请求或资源
                .antMatchers(path).permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userNotLoginHandler)
                .and()
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();
        // 基于Token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 添加application/json参数重写过滤器
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setFilterProcessesUrl("/user/login");
        filter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        filter.setAuthenticationFailureHandler(userLoginFailureHandler);
        //json的前端属性参数名称
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");
        return filter;
    }
}