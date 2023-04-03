package cn.klmb.crm.module.member.controller.admin.team.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Data;

/**
 * @author liuyuepan
 */
@Data
@ApiModel("crm团队成员BO对象")
public class MemberTeamSaveBO {

    @ApiModelProperty("bizIds")
    private List<String> bizIds;

    @ApiModelProperty("成员ids")
    private List<String> userIds;

    @ApiModelProperty("权限（1.只读 2.读写）")
    private Integer power;

    @ApiModelProperty("变更类型 1、联系人 2、商机 3、合同 4 回访(编辑团队成员时此值不用传)")
    private List<Integer> changeType;

    @ApiModelProperty("过期时间")
    @Hidden
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime expiresTime;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间")
    private String time;

    public MemberTeamSaveBO() {
    }

    public MemberTeamSaveBO(List<String> bizIds, MemberTeamSaveBO memberTeamSaveBO) {
        this.bizIds = bizIds;
        this.userIds = memberTeamSaveBO.getUserIds();
        this.power = memberTeamSaveBO.getPower();
        this.expiresTime = memberTeamSaveBO.getExpiresTime();
    }

    public MemberTeamSaveBO(String bizId, String userId) {
        this.bizIds = Collections.singletonList(bizId);
        this.userIds = Collections.singletonList(userId);
    }
}
