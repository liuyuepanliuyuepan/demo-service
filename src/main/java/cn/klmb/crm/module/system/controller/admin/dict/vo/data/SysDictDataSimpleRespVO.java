package cn.klmb.crm.module.system.controller.admin.dict.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 字典数据 - 精简信息
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@ApiModel("字典数据 - 精简信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDataSimpleRespVO {

    @ApiModelProperty(value = "字典类型", required = true, example = "gender")
    private String dictType;

    @ApiModelProperty(value = "字典键值", required = true, example = "1")
    private String value;

    @ApiModelProperty(value = "字典标签", required = true, example = "男")
    private String label;

}
