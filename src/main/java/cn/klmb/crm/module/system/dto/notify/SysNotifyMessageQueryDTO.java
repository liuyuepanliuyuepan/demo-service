package cn.klmb.crm.module.system.dto.notify;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 站内信 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysNotifyMessageQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 用户id
     */
    @DtoFieldQuery
    private String userId;
    /**
     * 用户类型(1会员,2管理员)
     */
    @DtoFieldQuery
    private Integer userType;
    /**
     * 模版编号
     */
    @DtoFieldQuery
    private String templateId;
    /**
     * 模版编码
     */
    @DtoFieldQuery
    private String templateCode;
    /**
     * 模版发送人名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String templateNickname;
    /**
     * 模版内容
     */
    @DtoFieldQuery
    private String templateContent;
    /**
     * 模版参数
     */
    @DtoFieldQuery
    private String templateParams;
    /**
     * 是否已读
     */
    @DtoFieldQuery
    private Boolean readStatus;
    /**
     * 阅读时间
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime[] readTime;

}
