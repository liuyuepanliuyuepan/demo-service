package cn.klmb.crm.module.system.controller.admin.permission.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统菜单 - 菜单列表
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@ApiModel(description = "系统菜单 - 菜单列表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuListReqVO {

    @ApiModelProperty(value = "菜单名称", example = "用户管理", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

}
