package cn.klmb.crm.module.member.controller.admin.record.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


@ApiModel("管理后台 - 负责人变更记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberOwnerRecordPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "对象id", example = "30743")
    private String typeId;

    @ApiModelProperty(value = "对象类型", example = "2")
    private Integer type;

    @ApiModelProperty(value = "上一负责人", example = "26760")
    private String preOwnerUserId;

    @ApiModelProperty(value = "接手负责人", example = "25966")
    private String postOwnerUserId;

}
