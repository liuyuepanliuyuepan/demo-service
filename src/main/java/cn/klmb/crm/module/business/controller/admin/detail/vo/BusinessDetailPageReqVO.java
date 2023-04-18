package cn.klmb.crm.module.business.controller.admin.detail.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("管理后台 - 商机分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessDetailPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "商机状态", example = "1")
    private String businessStatus;

    @ApiModelProperty(value = "客户ID", example = "4387")
    private String customerId;

    @ApiModelProperty(value = "首要联系人ID", example = "1603")
    private String contactsId;


    @ApiModelProperty(value = "跟进状态 0未跟进1已跟进")
    private Integer followup;

    @ApiModelProperty(value = "商机名称", example = "王五")
    private String businessName;

    @ApiModelProperty(value = "负责人ID", example = "31124")
    private String ownerUserId;


}
