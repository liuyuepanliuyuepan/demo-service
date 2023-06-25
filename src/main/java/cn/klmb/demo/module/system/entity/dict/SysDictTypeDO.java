package cn.klmb.demo.module.system.entity.dict;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统管理-字典类型
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
@TableName("sys_dict_type")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysDictTypeDO extends KlmbBaseDO {

    /**
     * 字典名称
     */
    private String name;
    /**
     * 字典类型
     */
    private String type;
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
