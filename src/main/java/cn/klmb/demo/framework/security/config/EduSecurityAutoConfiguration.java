package cn.klmb.demo.framework.security.config;

import cn.klmb.demo.framework.security.core.aop.PreAuthenticatedAspect;
import cn.klmb.demo.framework.security.core.filter.TokenAuthenticationFilter;
import cn.klmb.demo.framework.security.core.handler.AccessDeniedHandlerImpl;
import cn.klmb.demo.framework.security.core.handler.AuthenticationEntryPointImpl;
import cn.klmb.demo.framework.security.core.service.SecurityFrameworkService;
import cn.klmb.demo.framework.security.core.service.SecurityFrameworkServiceImpl;
import cn.klmb.demo.framework.web.core.handler.GlobalExceptionHandler;
import cn.klmb.demo.module.system.service.oauth2.SysOAuth2TokenService;
import cn.klmb.demo.module.system.service.permission.SysPermissionService;
import javax.annotation.Resource;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Spring Security 自动配置类，主要用于相关组件的配置
 * <p>
 * 注意，不能和 {@link EduWebSecurityConfigurerAdapter} 用一个，原因是会导致初始化报错。 参见
 * https://stackoverflow.com/questions/53847050/spring-boot-delegatebuilder-cannot-be-null-on-autowiring-authenticationmanager
 * 文档。
 *
 * @author 快乐萌宝
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class EduSecurityAutoConfiguration {

    @Resource
    private SecurityProperties securityProperties;

    /**
     * 处理用户未登录拦截的切面的 Bean
     */
    @Bean
    public PreAuthenticatedAspect preAuthenticatedAspect() {
        return new PreAuthenticatedAspect();
    }

    /**
     * 认证失败处理类 Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 权限不够处理器 Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * Spring Security 加密器 考虑到安全性，这里采用 BCryptPasswordEncoder 加密器
     *
     * @see <a href="http://stackabuse.com/password-encoding-with-spring-security/">Password
     * Encoding with Spring Security</a>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Token 认证过滤器 Bean
     */
    @Bean
    public TokenAuthenticationFilter authenticationTokenFilter(
            GlobalExceptionHandler globalExceptionHandler,
            SysOAuth2TokenService sysOAuth2TokenService) {
        return new TokenAuthenticationFilter(securityProperties, globalExceptionHandler,
                sysOAuth2TokenService);
    }

    @Bean("ss") // 使用 Spring Security 的缩写，方便使用
    public SecurityFrameworkService securityFrameworkService(
            SysPermissionService sysPermissionService) {
        return new SecurityFrameworkServiceImpl(sysPermissionService);
    }

    /**
     * 声明调用 {@link SecurityContextHolder#setStrategyName(String)} 方法，
     */
    @Bean
    public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        return methodInvokingFactoryBean;
    }

}
