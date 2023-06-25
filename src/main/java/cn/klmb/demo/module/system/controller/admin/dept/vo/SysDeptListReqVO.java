package cn.klmb.demo.module.system.controller.admin.dept.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统部门 - 部门列表
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@ApiModel(description = "系统部门 - 部门列表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDeptListReqVO {

    @ApiModelProperty(value = "部门名称", example = "快乐萌宝", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

}
