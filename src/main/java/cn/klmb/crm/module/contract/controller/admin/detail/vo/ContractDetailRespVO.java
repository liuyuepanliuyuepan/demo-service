package cn.klmb.crm.module.contract.controller.admin.detail.vo;

import lombok.*;
import java.time.LocalDateTime;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 合同详情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractDetailRespVO extends ContractDetailBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "cba1c9ca85d54ef2a95e078b55e9dfcc")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "客户名称")
    private String memberUserName;

    @ApiModelProperty(value = "商机名称")
    private String businessName;

    @ApiModelProperty(value = "客户签约人名称")
    private String contactsName;

    @ApiModelProperty(value = "公司签约人名称")
    private String companyUserName;

    // todo 字典查询
    @ApiModelProperty(value = "合同类型")
    private String typesName;

    @ApiModelProperty(value = "负责人")
    private String ownerUserName;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "负责人团队名称")
    private String ownerDeptName;

    @ApiModelProperty(value = "相关团队")
    private String teamMemberIds;
    /**
     * 是否标星
     */
    @ApiModelProperty(value = "是否标星")
    private Boolean star;

    // todo  签约状态

}
