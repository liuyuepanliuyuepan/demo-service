package cn.klmb.crm.module.system.dao.permission;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.permission.SysMenuQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysMenuDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统菜单
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Mapper
public interface SysMenuMapper extends KlmbBaseMapper<SysMenuDO, SysMenuQueryDTO> {

    default SysMenuDO selectByParentIdAndName(String parentId, String name) {
        return selectOne(new LambdaQueryWrapper<SysMenuDO>().eq(SysMenuDO::getParentId, parentId)
                .eq(SysMenuDO::getName, name));
    }

    default Long selectCountByParentId(String parentId) {
        return selectCount(
                new LambdaQueryWrapper<SysMenuDO>().eq(SysMenuDO::getParentId, parentId));
    }

    default List<SysMenuDO> selectByPermission(String permission) {
        return selectList(
                new LambdaQueryWrapper<SysMenuDO>().eq(SysMenuDO::getPermission, permission));
    }

}
