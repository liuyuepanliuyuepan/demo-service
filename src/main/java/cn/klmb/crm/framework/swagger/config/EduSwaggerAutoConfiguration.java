package cn.klmb.crm.framework.swagger.config;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.swagger.core.SpringFoxHandlerProviderBeanPostProcessor;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.DefaultPathProvider;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 自动配置类
 *
 * @author 快乐萌宝
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@ConditionalOnClass({Docket.class, ApiInfoBuilder.class})
// 允许使用 swagger.enable=false 禁用 Swagger
@ConditionalOnProperty(prefix = "crm.swagger", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
public class EduSwaggerAutoConfiguration {

    @Bean
    public SpringFoxHandlerProviderBeanPostProcessor springFoxHandlerProviderBeanPostProcessor() {
        return new SpringFoxHandlerProviderBeanPostProcessor();
    }

    @Bean
    public Docket createRestApi(SwaggerProperties properties) {
        // 创建 Docket 对象
        Docket host = new Docket(DocumentationType.SWAGGER_2)
                // 是否启用
                .enable(properties.getEnable1())
                // ① 用来创建该 API 的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo(properties))
                // ② 设置扫描指定 package 包下的
                .select()
                .apis(basePackage(properties.getBasePackage()))
//                .apis(basePackage("cn.klmb.crm.module.system")) // 可用于 swagger 无法展示时使用
                .paths(PathSelectors.any())
                .build()
                // ③ 安全上下文（认证）
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());

        if (StrUtil.isNotBlank(properties.getBasePath())) {
            host.pathProvider(new DefaultPathProvider() {
                @Override
                public String getOperationPath(String operationPath) {
                    operationPath = properties.getBasePath() + operationPath;
                    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
                    return Paths.removeAdjacentForwardSlashes(
                            uriComponentsBuilder.path(operationPath).build().toString());
                }

                @Override
                public String getResourceListingPath(String groupName, String apiDeclaration) {
                    apiDeclaration = super.getResourceListingPath(groupName, apiDeclaration);
                    return apiDeclaration;
                }
            });
        }
        return host;


    }

    // ========== apiInfo ==========

    /**
     * API 摘要信息
     */
    private static ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .contact(new Contact(properties.getAuthor(), null, null))
                .version(properties.getVersion())
                .build();
    }

    // ========== securitySchemes ==========

    /**
     * 安全模式，这里配置通过请求头 Authorization 传递 token 参数
     */
    private static List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(
                new ApiKey(HttpHeaders.AUTHORIZATION, "Authorization", "header"));
    }

    /**
     * 安全上下文
     *
     * @see #securitySchemes()
     * @see #authorizationScopes()
     */
    private static List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                // 通过 PathSelectors.regex("^(?!auth).*$")，排除包含 "auth" 的接口不需要使用securitySchemes
                .operationSelector(o -> o.requestMappingPattern().matches("^(?!auth).*$"))
                .build());
    }

    private static List<SecurityReference> securityReferences() {
        return Collections.singletonList(
                new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes()));
    }

    private static AuthorizationScope[] authorizationScopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")};
    }

    // ========== globalRequestParameters ==========

}
