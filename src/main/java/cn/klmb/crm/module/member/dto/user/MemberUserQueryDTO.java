package cn.klmb.crm.module.member.dto.user;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 客户-用户 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberUserQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 联系方式
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String tel;
    /**
     * 客户级别 1大B，2小B，3C端 {@link cn.klmb.crm.module.member.enums.MemberUserLevelEnum}
     */
    @DtoFieldQuery
    private Integer level;
    /**
     * 地址
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String address;
    /**
     * 淘宝名
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String tbName;
    /**
     * 微信名
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String wxName;

}
