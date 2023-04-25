package cn.klmb.crm.module.member.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * @author liuyuepan 负责人变更BO
 */
@Data
@ToString
@ApiModel(value = "负责人变更BO")
public class CrmChangeOwnerUserBO {

    @ApiModelProperty(value = "对应的类型(1 线索 2 客户 3 联系人 4 产品 5 商机 6 合同 7回款)")
    private String type;

    @ApiModelProperty("变更的bizId列表")
    private List<String> bizIds;

    @ApiModelProperty("新的负责人ID")
    private String ownerUserId;

    @ApiModelProperty("转移类型 1 移出团队，2 变为团队成员")
    private Integer transferType;

    @ApiModelProperty("权限 1 只读，2 读写")
    private Integer power;

    @ApiModelProperty("变更类型 1、联系人 2、商机 3、合同")
    private List<Integer> changeType;

    @ApiModelProperty("过期时间")
    private LocalDateTime expiresTime;
}
