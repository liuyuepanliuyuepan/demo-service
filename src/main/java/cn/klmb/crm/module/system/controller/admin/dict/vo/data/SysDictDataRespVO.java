package cn.klmb.crm.module.system.controller.admin.dict.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@ApiModel(description = "字典数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictDataRespVO extends SysDictDataBaseVO {

    @ApiModelProperty(value = "字典数据编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
