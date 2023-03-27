package cn.klmb.crm.framework.base.core.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新状态
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@ApiModel("更新状态")
@Data
public class UpdateStatusReqVO {

    @ApiModelProperty(value = "编码", required = true, example = "uuid")
    @NotNull(message = "编码不能为空")
    private String bizId;

    @ApiModelProperty(value = "状态", required = true, example = "0")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
