package cn.klmb.crm.module.system.dto.dict;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.module.system.entity.dict.SysDictDataDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统管理-字典数据
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysDictDataQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 字典排序
     */
    private Integer sort;
    /**
     * 字典标签
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String label;
    /**
     * 字典值
     */
    private String value;
    /**
     * 字典类型
     * <p>
     * 冗余 {@link SysDictDataDO#getDictType()}
     */
    @DtoFieldQuery
    private String dictType;
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
