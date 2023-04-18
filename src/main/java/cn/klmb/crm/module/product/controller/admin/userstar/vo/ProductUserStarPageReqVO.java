package cn.klmb.crm.module.product.controller.admin.userstar.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("管理后台 - 用户产品标星关系分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductUserStarPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "产品id", example = "1463")
    private String productId;

    @ApiModelProperty(value = "用户id", example = "18059")
    private String userId;

}
