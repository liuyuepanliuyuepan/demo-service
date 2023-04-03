package cn.klmb.crm.module.member.controller.admin.team.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 团队成员查询VO
 *
 * @author liuyuepan
 */
@Data
@ToString
@ApiModel("团队成员查询VO")
public class MembersTeamSelectVO {

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("权限 (1 只读 2 读写 3 负责人)")
    private Integer power;

    @ApiModelProperty("过期时间")
    private String expiresTime;
}
