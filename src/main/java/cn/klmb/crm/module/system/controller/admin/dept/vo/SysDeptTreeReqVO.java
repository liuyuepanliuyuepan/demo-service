package cn.klmb.crm.module.system.controller.admin.dept.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统部门 - 树查询
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@ApiModel(description = "系统部门 - 树查询")
@Data
public class SysDeptTreeReqVO {

    @ApiModelProperty(value = "部门名称", example = "快乐萌宝", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "部门名称（简称）", required = true, example = "快乐萌宝")
    private String nameShort;

    @ApiModelProperty(value = "部门编号", required = true, example = "uuid")
    @NotNull(message = "部门编号不能为空")
    private String treeId;

    @ApiModelProperty(value = "树深度", required = true, example = "1")
    @NotNull(message = "树深度不能为空")
    private String treeDepth;

}
