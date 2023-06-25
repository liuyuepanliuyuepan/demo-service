package cn.klmb.demo.module.system.controller.admin.notify.vo.template;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 站内信模板 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SysNotifyTemplateBaseVO {

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
