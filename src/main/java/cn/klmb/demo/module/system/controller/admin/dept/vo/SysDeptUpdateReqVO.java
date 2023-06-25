package cn.klmb.demo.module.system.controller.admin.dept.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统部门 - 更新
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@ApiModel(description = "系统部门 - 更新")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDeptUpdateReqVO extends SysDeptBaseVO {

    @ApiModelProperty(value = "菜单编号", required = true, example = "uuid")
    @NotNull(message = "菜单编号不能为空")
    private String bizId;

}
