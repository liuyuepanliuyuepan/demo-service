package cn.klmb.crm.module.system.service.permission;

import static cn.klmb.crm.module.system.constants.SysRedisKeyConstants.SYS_ROLE_MENU;
import static cn.klmb.crm.module.system.constants.SysRedisKeyConstants.SYS_ROLE_MENU_MENU_ID;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.permission.SysRoleMenuMapper;
import cn.klmb.crm.module.system.dto.permission.SysRoleMenuQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysRoleMenuDO;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 系统角色菜单
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Service
public class SysRoleMenuServiceImpl extends
        KlmbBaseServiceImpl<SysRoleMenuDO, SysRoleMenuQueryDTO, SysRoleMenuMapper> implements
        SysRoleMenuService {

    private final SysRoleMenuServiceImpl self;

    public SysRoleMenuServiceImpl(SysRoleMenuMapper mapper, @Lazy SysRoleMenuServiceImpl self) {
        this.self = self;
        this.mapper = mapper;
    }

    @Cacheable(cacheNames = SYS_ROLE_MENU, unless = "#result == null")
    @Override
    public List<SysRoleMenuDO> getAllRoleMenu() {
        return mapper.selectList();
    }

    @Override
    public List<SysRoleMenuDO> getRoleMenuByRoleIds(Collection<String> roleIds) {
        return self.getAllRoleMenu().stream()
                .filter(roleMenuDO -> roleIds.contains(roleMenuDO.getRoleId()))
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = {SYS_ROLE_MENU, SYS_ROLE_MENU_MENU_ID}, allEntries = true)
    @Override
    public boolean saveBatch(Collection<SysRoleMenuDO> entityList) {
        return super.saveBatch(entityList);
    }
}