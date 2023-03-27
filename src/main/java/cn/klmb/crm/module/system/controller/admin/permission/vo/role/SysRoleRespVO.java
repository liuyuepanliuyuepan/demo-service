package cn.klmb.crm.module.system.controller.admin.permission.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@ApiModel(description = "系统角色")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleRespVO extends SysRoleBaseVO {

    @ApiModelProperty(value = "角色编号", required = true, example = "1")
    private String bizId;

    @ApiModelProperty(value = "数据范围", required = true, example = "1", notes = "参见 DataScopeEnum 枚举类")
    private Integer dataScope;

    @ApiModelProperty(value = "数据范围(指定部门数组)", example = "1")
    private Set<String> dataScopeDeptIds;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "角色类型", required = true, example = "1", notes = "参见 SysRoleTypeEnum 枚举类")
    private Integer type;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
