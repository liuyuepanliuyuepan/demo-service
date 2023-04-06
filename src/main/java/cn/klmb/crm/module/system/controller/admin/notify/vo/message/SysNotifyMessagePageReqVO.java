package cn.klmb.crm.module.system.controller.admin.notify.vo.message;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("管理后台 - 站内信分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNotifyMessagePageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "用户id", example = "25544")
    private String userId;

    @ApiModelProperty(value = "用户类型(1会员,2管理员)", example = "1")
    private Integer userType;

    @ApiModelProperty(value = "模版编号", example = "19311")
    private String templateId;

    @ApiModelProperty(value = "模版编码")
    private String templateCode;

    @ApiModelProperty(value = "模版发送人名称", example = "李四")
    private String templateNickname;

    @ApiModelProperty(value = "模版内容")
    private String templateContent;

    @ApiModelProperty(value = "模版参数")
    private String templateParams;

    @ApiModelProperty(value = "是否已读")
    private Boolean readStatus;

    @ApiModelProperty(value = "阅读时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] readTime;

}
