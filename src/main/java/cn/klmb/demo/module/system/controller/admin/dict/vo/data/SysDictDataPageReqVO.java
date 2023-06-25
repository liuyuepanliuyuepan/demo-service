package cn.klmb.demo.module.system.controller.admin.dict.vo.data;


import cn.klmb.demo.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 字典数据 - 分页查询
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@ApiModel(description = "字典数据 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysDictDataPageReqVO extends PageParam {

    @ApiModelProperty(value = "字典标签", example = "男")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

    @ApiModelProperty(value = "字典类型", example = "sys_common_sex", notes = "模糊匹配")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String dictType;

    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

}
