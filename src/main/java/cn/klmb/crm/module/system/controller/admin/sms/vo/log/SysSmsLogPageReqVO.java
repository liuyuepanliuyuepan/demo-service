package cn.klmb.crm.module.system.controller.admin.sms.vo.log;


import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统管理 - 短信日志 - 分页查询
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@ApiModel(description = "系统管理 - 短信日志 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysSmsLogPageReqVO extends PageParam {

    @ApiModelProperty(value = "短信渠道编号", example = "uuid")
    private String channelId;

    @ApiModelProperty(value = "模板编号", example = "uuid")
    private String templateId;

    @ApiModelProperty(value = "手机号", example = "15600000000")
    private String mobile;

    @ApiModelProperty(value = "发送状态", example = "1", notes = "参见 SmsSendStatusEnum 枚举类")
    private Integer sendStatus;

//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
//    @ApiModelProperty(value = "发送时间")
//    private LocalDateTime[] sendTime;

    @ApiModelProperty(value = "接收状态", example = "0", notes = "参见 SmsReceiveStatusEnum 枚举类")
    private Integer receiveStatus;

//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
//    @ApiModelProperty(value = "接收时间")
//    private LocalDateTime[] receiveTime;

}
