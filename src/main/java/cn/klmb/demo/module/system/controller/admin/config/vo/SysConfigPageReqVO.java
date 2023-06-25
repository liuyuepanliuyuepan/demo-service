package cn.klmb.demo.module.system.controller.admin.config.vo;


import static cn.klmb.demo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.demo.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统管理 - 配置管理 - 分页查询
 *
 * @author liuyuepan
 * @date 2022/12/5
 */
@ApiModel(description = "系统管理 - 配置管理 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysConfigPageReqVO extends PageParam {

    @ApiModelProperty(value = "数据源名称", example = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "参数键名", example = "sys.msg.no_message_tel_list", notes = "模糊匹配")
    private String configKey;

    @ApiModelProperty(value = "参数类型", example = "1", notes = "参见 SysConfigTypeEnum 枚举")
    private Integer type;

    @ApiModelProperty(value = "创建时间", example = "2022-07-01 00:00:00,2022-07-01 23:59:59")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
