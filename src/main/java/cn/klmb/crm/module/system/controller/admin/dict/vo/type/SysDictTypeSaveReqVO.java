package cn.klmb.crm.module.system.controller.admin.dict.vo.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型 - 新增
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@ApiModel(description = "字典类型 - 新增")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictTypeSaveReqVO extends SysDictTypeBaseVO {

    @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
    @NotNull(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String type;

}
