package cn.klmb.crm.module.business.controller.admin.detail.vo;

import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 商机 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessDetailRespVO extends BusinessDetailBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "efd3e4cbc8834ff59aab0c631f9268d3")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    /**
     * 产品 Response VO 集合
     */
    @ApiModelProperty(value = "产品ResponseVO集合")
    private List<ProductDetailRespVO> productDetailRespVOList;

}
