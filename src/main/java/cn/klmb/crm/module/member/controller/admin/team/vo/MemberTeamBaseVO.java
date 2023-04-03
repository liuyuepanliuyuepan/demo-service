package cn.klmb.crm.module.member.controller.admin.team.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * crm团队成员 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberTeamBaseVO {

    @ApiModelProperty(value = "用户ID", example = "15278")
    private String userId;

    @ApiModelProperty(value = "权限(1 只读 2 读写 3 负责人)")
    private Integer power;

    @ApiModelProperty(value = "过期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime expiresTime;

    @ApiModelProperty(value = "类型，同crm类型", example = "1")
    private Integer type;

    @ApiModelProperty(value = "对应类型主键ID", example = "4378")
    private String typeId;

}
