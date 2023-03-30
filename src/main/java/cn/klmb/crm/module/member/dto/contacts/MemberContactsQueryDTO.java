package cn.klmb.crm.module.member.dto.contacts;

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
 * 联系人 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberContactsQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 联系人名称
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


    /**
     * 客户id
     */
    @DtoFieldQuery
    private String customerId;


}
