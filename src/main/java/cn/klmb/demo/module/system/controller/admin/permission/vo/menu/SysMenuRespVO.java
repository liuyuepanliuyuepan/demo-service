package cn.klmb.demo.module.system.controller.admin.permission.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统菜单
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@ApiModel(description = "系统菜单")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuRespVO extends SysMenuBaseVO {

    @ApiModelProperty(value = "菜单编号", required = true, example = "1")
    private String bizId;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
