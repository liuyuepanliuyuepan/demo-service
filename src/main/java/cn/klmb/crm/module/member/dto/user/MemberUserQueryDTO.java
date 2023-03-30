package cn.klmb.crm.module.member.dto.user;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.annotations.DtoKeywordQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.util.List;
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
    @DtoKeywordQuery
    private String name;

    /**
     * 手机
     */
    @DtoKeywordQuery
    private String mobile;
    /**
     * 电话
     */
    @DtoKeywordQuery
    private String tel;

    /**
     * 负责人
     */
    @DtoFieldQuery
    private String ownerUserId;

    /**
     * 负责人id列表
     */
    @DtoFieldQuery(queryType = Operator.IN, fieldName = "ownerUserId")
    private List<String> ownerUserIds;


}
