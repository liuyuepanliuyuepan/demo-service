package cn.klmb.crm.module.system.dto.dict;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统管理-字典类型
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysDictTypeQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 字典名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 字典类型
     */
    @DtoFieldQuery
    private String type;
    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    @DtoFieldQuery
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
