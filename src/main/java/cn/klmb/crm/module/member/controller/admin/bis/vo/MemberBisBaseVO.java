package cn.klmb.crm.module.member.controller.admin.bis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户工商信息 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberBisBaseVO {

    @ApiModelProperty(value = "公司营业执照")
    private String img;

    @ApiModelProperty(value = "办公地址")
    private String address;

    @ApiModelProperty(value = "注册资本")
    private String registCapi;

    @ApiModelProperty(value = "法定代表人", example = "李四")
    private String operName;

    @ApiModelProperty(value = "统一社会信用代码")
    private String creditNo;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "客户id")
    private String customerId;

}
