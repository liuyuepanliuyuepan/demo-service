package cn.klmb.crm.module.business.controller.admin.detail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 更新商机状态
 *
 * @author liuyuepan
 * @date 2023/4/19
 */
@ApiModel("更新商机状态")
@Data
public class UpdateBusinessStatusReqVO {

    @ApiModelProperty(value = "bizIds", required = true)
    @NotEmpty(message = "bizIds不能为空")
    private List<String> bizIds;

    @ApiModelProperty(value = "商机状态", required = true, example = "0")
    @NotBlank(message = "商机状态不能为空")
    private String businessStatus;

}
