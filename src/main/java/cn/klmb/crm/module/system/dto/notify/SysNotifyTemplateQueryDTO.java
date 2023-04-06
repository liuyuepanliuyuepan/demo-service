package cn.klmb.crm.module.system.dto.notify;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 站内信模板 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysNotifyTemplateQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 模版名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 模版编码
     */
    @DtoFieldQuery
    private String code;
    /**
     * 模版类型
     */
    @DtoFieldQuery
    private Integer type;
    /**
     * 发送人名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String nickname;
    /**
     * 模版内容
     */
    @DtoFieldQuery
    private String content;
    /**
     * 参数数组
     */
    @DtoFieldQuery
    private List<String> params;
    /**
     * 备注
     */
    @DtoFieldQuery
    private String remark;

}
