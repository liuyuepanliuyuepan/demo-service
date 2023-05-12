package cn.klmb.crm.module.contract.controller.admin.detail.vo;

import cn.klmb.crm.module.contract.controller.admin.product.vo.ContractProductSaveReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 合同详情创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractDetailSaveReqVO extends ContractDetailBaseVO {

    /**
     * 合同产品关系
     */
    @ApiModelProperty(value = "合同产品关系集合")
    @NotEmpty(message = "合同产品关系集合不能为空")
    private List<ContractProductSaveReqVO> contractProductSaveReqVOList;
}
