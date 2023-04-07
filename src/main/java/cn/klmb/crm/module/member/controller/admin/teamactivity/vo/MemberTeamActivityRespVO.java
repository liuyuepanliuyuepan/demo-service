package cn.klmb.crm.module.member.controller.admin.teamactivity.vo;

import cn.klmb.crm.module.system.entity.file.SysFileDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - crm团队成员活动 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberTeamActivityRespVO extends MemberTeamActivityBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "8991f5d29b4549b686d91cfef1a63fd4")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    /**
     * 图片详细信息
     */
    @ApiModelProperty(value = "图片详细信息")
    private List<SysFileDO> imgInfo;

    /**
     * 文件详细信息
     */
    @ApiModelProperty(value = "文件详细信息")
    private List<SysFileDO> fileInfo;

}
