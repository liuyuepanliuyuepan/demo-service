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
     * 客户名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 手机
     */
    @DtoFieldQuery
    private String mobile;
    /**
     * 电话
     */
    @DtoFieldQuery
    private String tel;


}
