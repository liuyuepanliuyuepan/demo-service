package cn.klmb.demo.module.system.controller.admin.sms.vo.channel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信渠道精简
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@ApiModel(description = "短信渠道精简")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysSmsChannelSimpleRespVO extends SysSmsChannelBaseVO {

    @ApiModelProperty(value = "编号", required = true, example = "uuid")
    @NotNull(message = "编号不能为空")
    private String bizId;

    @ApiModelProperty(value = "短信签名", required = true, example = "快乐萌宝")
    @NotNull(message = "短信签名不能为空")
    private String signature;

    @ApiModelProperty(value = "渠道编码", required = true, example = "ALIYUN", notes = "参见 SmsChannelEnum 枚举类")
    private String code;

}
