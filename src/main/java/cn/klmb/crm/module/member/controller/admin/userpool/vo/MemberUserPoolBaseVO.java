package cn.klmb.crm.module.member.controller.admin.userpool.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公海 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberUserPoolBaseVO {

    @ApiModelProperty(value = "公海名称", example = "赵六")
    private String poolName;

}
