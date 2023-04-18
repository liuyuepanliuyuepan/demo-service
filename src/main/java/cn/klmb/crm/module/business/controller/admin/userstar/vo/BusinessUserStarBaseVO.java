package cn.klmb.crm.module.business.controller.admin.userstar.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户商机标星关系 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class BusinessUserStarBaseVO {

    @ApiModelProperty(value = "用户id", example = "18138")
    private String userId;

    @ApiModelProperty(value = "客户id", example = "20879")
    private String businessId;

}
