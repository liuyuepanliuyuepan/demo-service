package cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户公海关联 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberUserPoolRelationBaseVO {

    @ApiModelProperty(value = "客户id", example = "21415")
    private String customerId;

    @ApiModelProperty(value = "公海id", example = "20153")
    private String poolId;

}
