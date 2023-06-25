package cn.klmb.demo.module.system.entity.permission;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统角色-菜单
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@TableName("sys_role_menu")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysRoleMenuDO extends KlmbBaseDO {

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 菜单 ID
     */
    private String menuId;

}
