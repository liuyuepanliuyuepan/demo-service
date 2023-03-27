package cn.klmb.crm.module.member.controller.admin.user.vo;

import cn.klmb.crm.framework.common.pojo.PageParam;
import cn.klmb.crm.framework.common.validation.InEnum;
import cn.klmb.crm.module.member.enums.MemberUserLevelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户-用户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserPageReqVO extends PageParam {

    @ApiModelProperty(value = "名称", example = "王五")
    private String name;

    @ApiModelProperty(value = "联系方式")
    private String tel;

    @ApiModelProperty(value = "客户级别 1大B，2小B，3C端")
    @InEnum(value = MemberUserLevelEnum.class, message = "客户级别必须是 {value}")
    private Integer level;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "淘宝名", example = "王五")
    private String tbName;

    @ApiModelProperty(value = "微信名", example = "赵六")
    private String wxName;

}
