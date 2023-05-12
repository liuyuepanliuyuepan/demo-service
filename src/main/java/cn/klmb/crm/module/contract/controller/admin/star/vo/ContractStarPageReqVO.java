package cn.klmb.crm.module.contract.controller.admin.star.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.klmb.crm.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 合同标星关系分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractStarPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "合同ID", example = "1045")
    private String contractId;

    @ApiModelProperty(value = "用户id", example = "12884")
    private String userId;

}
