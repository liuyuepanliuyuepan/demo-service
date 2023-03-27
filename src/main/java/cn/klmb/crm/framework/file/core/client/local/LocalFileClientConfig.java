package cn.klmb.crm.framework.file.core.client.local;

import cn.klmb.crm.framework.file.core.client.FileClientConfig;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * 本地文件客户端的配置类
 *
 * @author 快乐萌宝
 */
@Data
public class LocalFileClientConfig implements FileClientConfig {

    /**
     * 基础路径
     */
    @NotEmpty(message = "基础路径")
    private String basePath;

    /**
     * 自定义域名
     */
    @NotEmpty(message = "domain 不能为空")
    @URL(message = "domain 必须是 URL 格式")
    private String domain;

    /**
     * 是否为静态文件，静态文件直接通过web服务器（nginx）进行访问
     */
    @NotNull(message = "是否为静态文件 不能为空")
    private Boolean staticFile;

}
