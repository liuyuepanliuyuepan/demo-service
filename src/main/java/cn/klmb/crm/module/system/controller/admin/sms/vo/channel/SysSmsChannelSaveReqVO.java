package cn.klmb.crm.module.system.controller.admin.sms.vo.channel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理 - 短信渠道 - 新增
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@ApiModel(description = "系统管理 - 短信渠道 - 新增")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysSmsChannelSaveReqVO extends SysSmsChannelBaseVO {

    @ApiModelProperty(value = "渠道编码", required = true, example = "ALIYUN", notes = "参见 SmsChannelEnum 枚举类")
    @NotNull(message = "渠道编码不能为空")
    private String code;

}
