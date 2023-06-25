package cn.klmb.demo.module.system.entity.dict;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统管理-字典数据
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
@TableName("sys_dict_data")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysDictDataDO extends KlmbBaseDO {

    /**
     * 字典排序
     */
    private Integer sort;
    /**
     * 字典标签
     */
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
    private String dictType;
    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
