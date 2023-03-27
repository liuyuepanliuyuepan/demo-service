package cn.klmb.crm.module.system.entity.dept;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseTreeDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统部门
 *
 * @author shilinchuan
 * @date 2022/12/2
 */
@TableName("sys_dept")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysDeptDO extends KlmbBaseTreeDO<SysDeptDO> {

    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门名称（简称）
     */
    private String nameShort;
    /**
     * 负责人
     * <p>
     * 关联 {@link SysUserDO#getBizId()}
     */
    private String leaderUserId;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;

}
