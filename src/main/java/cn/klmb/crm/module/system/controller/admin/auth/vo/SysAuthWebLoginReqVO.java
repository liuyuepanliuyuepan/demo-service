package cn.klmb.crm.module.system.controller.admin.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "飞书网页登录 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysAuthWebLoginReqVO {

    /**
     * 飞书小程序登录时获取的 code
     */
    @NotEmpty(message = "飞书网页登录时获取的code不能为空")
    @ApiModelProperty(value = "飞书网页登录时获取的 code")
    private String code;

}
