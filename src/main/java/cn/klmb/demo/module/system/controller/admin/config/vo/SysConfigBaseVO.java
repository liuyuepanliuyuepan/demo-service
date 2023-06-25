package cn.klmb.demo.module.system.controller.admin.config.vo;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 配置参数 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SysConfigBaseVO {

    @ApiModelProperty(value = "参数分组", required = true, example = "sys")
    @NotEmpty(message = "参数分组不能为空")
    @Size(max = 50, message = "参数名称不能超过50个字符")
    private String category;

    @ApiModelProperty(value = "参数名称", required = true, example = "不发短信电话列表")
    @NotBlank(message = "参数名称不能为空")
    @Size(max = 100, message = "参数名称不能超过100个字符")
    private String name;

    @ApiModelProperty(value = "参数键值", required = true, example = "18600000000,18500000000")
    @NotBlank(message = "参数键值不能为空")
    @Size(max = 500, message = "参数键值长度不能超过500个字符")
    private String value;

    @ApiModelProperty(value = "是否敏感", required = true, example = "true")
    @NotNull(message = "是否敏感不能为空")
    private Boolean visible;

    @ApiModelProperty(value = "备注", example = "填入的手机号，不再发送验证码，使用默认验证码")
    private String remark;

}
