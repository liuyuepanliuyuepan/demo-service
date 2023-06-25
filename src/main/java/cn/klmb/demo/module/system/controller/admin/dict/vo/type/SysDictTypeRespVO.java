package cn.klmb.demo.module.system.controller.admin.dict.vo.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
@ApiModel(description = "字典类型")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictTypeRespVO extends SysDictTypeBaseVO {

    @ApiModelProperty(value = "字典数据编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
    private String type;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
