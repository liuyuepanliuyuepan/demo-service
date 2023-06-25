package cn.klmb.demo.module.system.controller.admin.sms.vo.channel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理 - 短信渠道
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@ApiModel(description = "系统管理 - 短信渠道")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysSmsChannelRespVO extends SysSmsChannelBaseVO {

    @ApiModelProperty(value = "编号", required = true, example = "1024")
    private String bizId;

    @ApiModelProperty(value = "渠道编码", required = true, example = "ALIYUN", notes = "参见 SmsChannelEnum 枚举类")
    private String code;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
