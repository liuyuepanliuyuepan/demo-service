package cn.klmb.crm.module.business.controller.admin.detail.vo;

import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductSaveReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 商机更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessDetailUpdateReqVO extends BusinessDetailBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "efd3e4cbc8834ff59aab0c631f9268d3")
    @NotNull(message = "业务id不能为空")
    private String bizId;

    /**
     * 商机产品关系集合
     */
    @ApiModelProperty(value = "商机产品关系集合")
    private List<BusinessProductSaveReqVO> businessProductSaveList;

}
