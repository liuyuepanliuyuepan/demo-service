package cn.klmb.demo.module.system.controller.admin.file.vo.config;

import cn.klmb.demo.framework.file.core.client.FileClientConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件配置
 *
 * @author liuyuepan
 * @date 2022/12/6
 */
@ApiModel(description = "文件配置")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFileConfigRespVO extends SysFileConfigBaseVO {

    @ApiModelProperty(value = "编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "存储器", required = true, example = "1", notes = "参见 FileStorageEnum 枚举类")
    @NotNull(message = "存储器不能为空")
    private Integer storage;

    @ApiModelProperty(value = "是否为主配置", required = true, example = "true")
    @NotNull(message = "是否为主配置不能为空")
    private Boolean master;

    @ApiModelProperty(value = "存储配置", required = true)
    private FileClientConfig config;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
