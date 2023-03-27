package cn.klmb.crm.module.system.controller.admin.config.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理 - 配置管理 - 新增
 *
 * @author shilinchuan
 * @date 2022/12/5
 */
@ApiModel(description = "系统管理 - 配置管理 - 新增")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigSaveReqVO extends SysConfigBaseVO {

    @ApiModelProperty(value = "参数键名", required = true, example = "sys.msg.no_message_tel_list")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(max = 100, message = "参数键名长度不能超过100个字符")
    private String configKey;

}
