package cn.klmb.demo.module.system.controller.admin.user.vo;

import cn.klmb.demo.module.system.controller.admin.permission.vo.role.SysRoleSimpleRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户 - 用户信息
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@ApiModel(description = "系统用户 - 用户信息")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserRespVO extends SysUserBaseVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1")
    private String bizId;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "最后登录 IP", required = true, example = "192.168.1.1")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间", required = true, example = "时间戳格式")
    private LocalDateTime loginDate;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "部门名称", required = true, example = "快乐萌宝")
    private String deptName;

    @ApiModelProperty(value = "角色列表", required = true)
    private List<SysRoleSimpleRespVO> roles;

}
