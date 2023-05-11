package cn.klmb.crm.module.business.controller.admin.detail.vo;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台 - 商机 Response VO(包含全部商机金额)")
@Data
@ToString(callSuper = true)
public class BusinessDetailFullRespVO {

    /**
     * 分页数据
     */
    private KlmbPage<BusinessDetailRespVO> klmbPage;
    /**
     * 商机总金额(仅限PC端商机分页)
     */
    @ApiModelProperty(value = "商机总金额(仅限PC端商机分页)")
    private BigDecimal businessSumMoney;

}
