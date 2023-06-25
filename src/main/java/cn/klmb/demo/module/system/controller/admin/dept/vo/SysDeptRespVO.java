package cn.klmb.demo.module.system.controller.admin.dept.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统部门
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@ApiModel(description = "系统部门")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDeptRespVO extends SysDeptBaseVO {

    @ApiModelProperty(value = "部门编号", required = true, example = "1")
    private String bizId;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "部门树ID", required = true, example = "123")
    private String treeId;

}
