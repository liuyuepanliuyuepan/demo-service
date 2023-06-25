package cn.klmb.demo.module.system.controller.admin.dept.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统部门精简
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@ApiModel(description = "系统部门精简")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDeptSimpleRespVO extends SysDeptBaseVO {

    @ApiModelProperty(value = "部门编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "部门名称", required = true, example = "快乐萌宝")
    private String name;

    @ApiModelProperty(value = "树 ID", required = true, example = "uuid")
    private String treeId;

    @ApiModelProperty(value = "父部门 ID", required = true, example = "uuid")
    private String treeParentId;

}
