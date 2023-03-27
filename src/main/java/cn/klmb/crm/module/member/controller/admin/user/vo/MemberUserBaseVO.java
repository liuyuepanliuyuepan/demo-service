package cn.klmb.crm.module.member.controller.admin.user.vo;

import cn.klmb.crm.framework.common.validation.InEnum;
import cn.klmb.crm.module.member.enums.MemberUserLevelEnum;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 客户-用户 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberUserBaseVO {

    @ApiModelProperty(value = "名称", required = true, example = "王五")
    @NotNull(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "联系方式")
    private String tel;

    @ApiModelProperty(value = "客户级别 1大B，2小B，3C端", required = true)
    @NotNull(message = "客户级别 1大B，2小B，3C端不能为空")
    @InEnum(value = MemberUserLevelEnum.class, message = "客户级别必须是 {value}")
    private Integer level;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "淘宝名", example = "王五")
    private String tbName;

    @ApiModelProperty(value = "微信名", example = "赵六")
    private String wxName;

    @ApiModelProperty(value = "备注", example = "备注")
    private String remark;

}
