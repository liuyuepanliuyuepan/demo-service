package cn.klmb.crm.module.member.controller.admin.instrument.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrmCountRankVO {

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String realName;

    /**
     * 新增数量
     */
    @ApiModelProperty(value = "新增数量")
    private Long count;


    /**
     * 负责人ID
     */
    @ApiModelProperty(value = "负责人ID")
    private String ownerUserId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;


}
