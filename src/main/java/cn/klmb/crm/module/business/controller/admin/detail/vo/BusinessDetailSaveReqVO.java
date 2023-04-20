package cn.klmb.crm.module.business.controller.admin.detail.vo;

import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductSaveReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
     * 商机产品关系集合
     */
    @ApiModelProperty(value = "商机产品关系集合")
    private List<BusinessProductSaveReqVO> businessProductSaveList;

}
