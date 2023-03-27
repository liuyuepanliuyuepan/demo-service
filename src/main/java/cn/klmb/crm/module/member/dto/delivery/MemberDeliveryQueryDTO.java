package cn.klmb.crm.module.member.dto.delivery;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 客户-收货地址 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberDeliveryQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 客户ID
     */
    @DtoFieldQuery
    private String memberUserId;
    /**
     * 收件人信息
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 收件人电话
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String tel;
    /**
     * 收货地址
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String address;
    /**
     * 备注
     */
    @DtoFieldQuery
    private String remark;

}
