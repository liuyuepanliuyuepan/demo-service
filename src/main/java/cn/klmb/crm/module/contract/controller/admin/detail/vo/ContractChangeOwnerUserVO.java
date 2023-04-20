package cn.klmb.crm.module.contract.controller.admin.detail.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zhouzhipeng
 * 合同转移 对象
 */
@Data
@ToString(callSuper = true)
public class ContractChangeOwnerUserVO {
    @ApiModelProperty(value = "变更的ID列表",required = true)
    private List<String> bizIds;

    @ApiModelProperty("新的负责人ID")
    private String ownerUserId;

    @ApiModelProperty("转移类型 1 移出团队，2 变为团队成员")
    private Integer transferType;

    @ApiModelProperty("权限 1 只读，2 读写 后续再做")
    private Integer power;

}
