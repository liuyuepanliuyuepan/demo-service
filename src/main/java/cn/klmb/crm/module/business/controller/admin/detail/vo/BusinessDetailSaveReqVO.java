package cn.klmb.crm.module.business.controller.admin.detail.vo;

import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailSaveReqVO;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 商机创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessDetailSaveReqVO extends BusinessDetailBaseVO {

    /**
     * 产品信息
     */
    private List<ProductDetailSaveReqVO> productDetailSaveReqVO;

}
