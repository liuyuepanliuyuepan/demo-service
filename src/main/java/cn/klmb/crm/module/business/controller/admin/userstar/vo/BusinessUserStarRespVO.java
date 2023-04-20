package cn.klmb.crm.module.business.controller.admin.userstar.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 用户商机标星关系 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessUserStarRespVO extends BusinessUserStarBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "4d34612a4a1c42fe8afe004bb4a23fcd")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
