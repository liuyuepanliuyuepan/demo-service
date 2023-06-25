package cn.klmb.demo.framework.swagger.config;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 配置属性
 *
 * @author 快乐萌宝
 */
@ConfigurationProperties("demo.swagger")
@Data
public class SwaggerProperties {

    /**
     * 是否启用
     */
    @NotEmpty(message = "是否启用不能为空")
    private Boolean enable1;

    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    private String title;
    /**
     * 描述
     */
    @NotEmpty(message = "描述不能为空")
    private String description;
    /**
     * 作者
     */
    @NotEmpty(message = "作者不能为空")
    private String author;
    /**
     * 版本
     */
    @NotEmpty(message = "版本不能为空")
    private String version;
    /**
     * 扫描的包
     */
    @NotEmpty(message = "扫描的 package 不能为空")
    private String basePackage;

    /**
     * basePath地址，接口调用不符时，需要指定
     */
    private String basePath;

}
