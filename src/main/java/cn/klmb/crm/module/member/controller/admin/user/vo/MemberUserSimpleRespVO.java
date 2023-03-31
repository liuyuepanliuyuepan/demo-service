package cn.klmb.crm.module.member.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理后台 - 客户精简信息 Response VO
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@ApiModel(description = "管理后台 - 客户精简信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUserSimpleRespVO {

    @ApiModelProperty(value = "用户业务id", required = true)
    private String bizId;

    @ApiModelProperty(value = "客户名称", required = true)
    private String name;

}
