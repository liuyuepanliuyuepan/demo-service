package cn.klmb.demo.module.system.entity.permission;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统用户-角色
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@TableName("sys_user_role")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysUserRoleDO extends KlmbBaseDO {

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色 ID
     */
    private String roleId;

}
