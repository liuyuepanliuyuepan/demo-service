package cn.klmb.crm.module.member.controller.admin.user.vo;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户-用户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserPageReqVO extends PageParam {

    @ApiModelProperty(value = "客户名称", example = "张三")
    private String name;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "场景ID(1全部,2自己负责的,3下属负责的,4关注的,默认传1)")
    @NotNull(message = "场景ID不能为空！")
    private Integer sceneId;


}
