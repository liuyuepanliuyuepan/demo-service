package cn.klmb.demo.module.system.controller.admin.sms.vo.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理 - 短信模板
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@ApiModel(description = "短信模板 - 短信渠道")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysSmsTemplateRespVO extends SysSmsTemplateBaseVO {

    @ApiModelProperty(value = "编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "短信渠道编码", required = true, example = "ALIYUN")
    private String channelCode;

    @ApiModelProperty(value = "参数数组", example = "name,code")
    private List<String> params;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
