package cn.klmb.crm.module.system.controller.admin.permission.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 系统菜单 - 菜单信息精简
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@ApiModel("系统菜单 - 菜单信息精简")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuSimpleRespVO {

    @ApiModelProperty(value = "菜单编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "菜单名称", required = true, example = "用户管理")
    private String name;

    @ApiModelProperty(value = "父菜单 ID", required = true, example = "uuid")
    private String parentId;

    @ApiModelProperty(value = "类型", required = true, example = "1", notes = "参见 MenuTypeEnum 枚举类")
    private Integer type;

}
