package cn.klmb.crm.module.contract.controller.admin.detail.vo;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台 - 合同 Response VO(包含全部合同金额、已回款金额、未回款金额)")
@Data
@ToString(callSuper = true)
public class ContractDetailFullRespVO {

    /**
     * 分页数据
     */
    private KlmbPage<ContractDetailRespVO> klmbPage;
    /**
     * 合同总金额(仅限PC端合同分页)
     */
    @ApiModelProperty(value = "合同总金额(仅限PC端合同分页)")
    private BigDecimal contractMoney;


    /**
     * 已回款金额
     */
    @ApiModelProperty(value = "已回款金额(仅限PC端合同分页)")
    private BigDecimal receivedMoney;

    /**
     * 未回款金额
     */
    @ApiModelProperty(value = "未回款金额")
    private BigDecimal unReceivedMoney;

}
