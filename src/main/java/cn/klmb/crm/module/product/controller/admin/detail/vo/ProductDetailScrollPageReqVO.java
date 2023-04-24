package cn.klmb.crm.module.product.controller.admin.detail.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.base.core.pojo.PageScrollParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 产品滚动分页查询
 *
 * @author liuyuepan
 * @date 2023/4/15
 */
@ApiModel(description = "产品滚动分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDetailScrollPageReqVO extends PageScrollParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "产品编码")
    private String num;

    @ApiModelProperty(value = "产品分类ID")
    private String categoryId;

    @ApiModelProperty(value = "负责人ID")
    private String ownerUserId;

    @ApiModelProperty(value = "场景ID(1全部产品,9上架的产品,10下架的产品,4我关注的,默认传1)")
    private Integer sceneId;


}
