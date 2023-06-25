package cn.klmb.demo.module.system.controller.admin.notify.vo.template;

import static cn.klmb.demo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.demo.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("管理后台 - 站内信模板分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNotifyTemplatePageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "模版名称", example = "李四")
    private String name;

    @ApiModelProperty(value = "模版编码")
    private String code;

    @ApiModelProperty(value = "模版类型", example = "2")
    private Integer type;

    @ApiModelProperty(value = "发送人名称", example = "李四")
    private String nickname;

    @ApiModelProperty(value = "模版内容")
    private String content;

    @ApiModelProperty(value = "参数数组")
    private List<String> params;

    @ApiModelProperty(value = "备注", example = "朝辞白帝彩云间，千里江陵一日还")
    private String remark;

}
