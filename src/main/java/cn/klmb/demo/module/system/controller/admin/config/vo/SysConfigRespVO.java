package cn.klmb.demo.module.system.controller.admin.config.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理 - 配置管理
 *
 * @author liuyuepan
 * @date 2022/12/5
 */
@ApiModel(description = "系统管理 - 配置管理")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigRespVO extends SysConfigBaseVO {

    @ApiModelProperty(value = "参数配置序号", required = true, example = "1024")
    private String bizId;

    @ApiModelProperty(value = "参数键名", required = true, example = "sys.msg.no_message_tel_list")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(max = 100, message = "参数键名长度不能超过100个字符")
    private String configKey;

    @ApiModelProperty(value = "参数类型", required = true, example = "1", notes = "参见 SysConfigTypeEnum 枚举")
    private Integer type;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
