package cn.klmb.demo.module.system.controller.admin.dict.vo.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 字典类型 - 精简信息
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
@ApiModel("字典类型 - 精简信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDictTypeSimpleRespVO {

    @ApiModelProperty(value = "字典类型编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "字典类型名称", required = true, example = "快乐萌宝")
    private String name;

    @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
    private String type;

}
