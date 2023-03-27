package cn.klmb.crm.module.member.controller.admin.delivery.vo;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 客户-收货地址 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberDeliveryBaseVO {

    @ApiModelProperty(value = "收件人信息", required = true, example = "王五")
    @NotNull(message = "收件人信息不能为空")
    private String name;

    @ApiModelProperty(value = "收件人电话")
    private String tel;

    @ApiModelProperty(value = "收货地址")
    private String address;

    @ApiModelProperty(value = "备注", example = "飞流直下三千尺，疑是银河落九天")
    private String remark;

}
