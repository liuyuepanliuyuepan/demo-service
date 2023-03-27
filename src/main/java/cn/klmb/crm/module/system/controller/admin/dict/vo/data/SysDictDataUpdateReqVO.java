package cn.klmb.crm.module.system.controller.admin.dict.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据 - 更新
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@ApiModel(description = "字典数据 - 更新")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictDataUpdateReqVO extends SysDictDataBaseVO {

    @ApiModelProperty(value = "字典数据编号", required = true, example = "uuid")
    @NotNull(message = "字典数据不能为空")
    private String bizId;

}
