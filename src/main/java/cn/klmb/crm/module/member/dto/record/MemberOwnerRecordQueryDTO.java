package cn.klmb.crm.module.member.dto.record;


import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 负责人变更记录 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberOwnerRecordQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 对象id
     */
    @DtoFieldQuery
    private String typeId;
    /**
     * 对象类型
     */
    @DtoFieldQuery
    private Integer type;
    /**
     * 上一负责人
     */
    @DtoFieldQuery
    private String preOwnerUserId;
    /**
     * 接手负责人
     */
    @DtoFieldQuery
    private String postOwnerUserId;

}
