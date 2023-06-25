package cn.klmb.demo.module.system.controller.admin.notify.vo.message;

import static cn.klmb.demo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 站内信 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SysNotifyMessageBaseVO {

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
    private Map<String, Object> templateParams;

    @ApiModelProperty(value = "是否已读")
    private Boolean readStatus;

    @ApiModelProperty(value = "阅读时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime readTime;

}
