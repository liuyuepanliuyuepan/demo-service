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

    @ApiModelProperty(value = "场景ID(1全部,2自己负责的,3下属负责的,4关注的,5赢单商机,6输单商机,7无效商机,8进行中的商机,默认传1)")
    private Integer sceneId;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "首要联系人ID")
    private String contactsId;

    @ApiModelProperty(value = "商机名称")
    private String businessName;



}
