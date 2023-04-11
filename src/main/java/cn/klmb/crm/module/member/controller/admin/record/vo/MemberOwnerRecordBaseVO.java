package cn.klmb.crm.module.member.controller.admin.record.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 负责人变更记录 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberOwnerRecordBaseVO {

    @ApiModelProperty(value = "对象id", example = "30743")
    private String typeId;

    @ApiModelProperty(value = "对象类型", example = "2")
    private Integer type;

    @ApiModelProperty(value = "上一负责人", example = "26760")
    private String preOwnerUserId;

    @ApiModelProperty(value = "接手负责人", example = "25966")
    private String postOwnerUserId;

}
