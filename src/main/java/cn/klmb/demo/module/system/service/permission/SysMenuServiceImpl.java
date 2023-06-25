package cn.klmb.demo.module.system.service.permission;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.demo.module.system.constants.SysRedisKeyConstants.SYS_MENU;
import static cn.klmb.demo.module.system.constants.SysRedisKeyConstants.SYS_MENU_PERMISSION;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.MENU_EXISTS_CHILDREN;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.MENU_NAME_DUPLICATE;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.MENU_NOT_EXISTS;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.MENU_PARENT_ERROR;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.MENU_PARENT_NOT_DIR_OR_MENU;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.MENU_PARENT_NOT_EXISTS;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.demo.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.framework.common.util.collection.CollectionUtils;
import cn.klmb.demo.module.system.dao.permission.SysMenuMapper;
import cn.klmb.demo.module.system.dto.permission.SysMenuQueryDTO;
import cn.klmb.demo.module.system.entity.permission.SysMenuDO;
import cn.klmb.demo.module.system.enums.permission.SysMenuIdEnum;
import cn.klmb.demo.module.system.enums.permission.SysMenuTypeEnum;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 系统菜单
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@Service
public class SysMenuServiceImpl extends
        KlmbBaseServiceImpl<SysMenuDO, SysMenuQueryDTO, SysMenuMapper> implements SysMenuService {

    private final SysPermissionService sysPermissionService;
    private final SysMenuServiceImpl self;

    public SysMenuServiceImpl(SysMenuMapper mapper,
            @Lazy SysPermissionService sysPermissionService, @Lazy SysMenuServiceImpl self) {
        this.sysPermissionService = sysPermissionService;
        this.self = self;
        this.mapper = mapper;
    }

    @CacheEvict(cacheNames = {SYS_MENU, SYS_MENU_PERMISSION}, allEntries = true)
    @Override
    public boolean saveDO(SysMenuDO entity) {
        // 校验父菜单存在
        checkParentResource(entity.getParentId(), null);
        // 校验菜单（自己）
        checkResource(entity.getParentId(), entity.getName(), null);
        initMenuProperty(entity);
        // 插入数据库
        return super.saveDO(entity);
    }

    @CacheEvict(cacheNames = {SYS_MENU, SYS_MENU_PERMISSION}, allEntries = true)
    @Override
    public boolean updateDO(SysMenuDO entity) {
        // 校验更新的菜单是否存在
        SysMenuDO sysMenuDO = mapper.selectByBizId(entity.getBizId());
        if (ObjectUtil.isNull(sysMenuDO)) {
            throw exception(MENU_NOT_EXISTS);
        }
        // 校验父菜单存在
        checkParentResource(entity.getParentId(), entity.getBizId());
        // 校验菜单（自己）
        checkResource(entity.getParentId(), entity.getName(), entity.getBizId());
        // 更新到数据库
        initMenuProperty(entity);
        return super.updateDO(entity);
    }

    @CacheEvict(cacheNames = {SYS_MENU, SYS_MENU_PERMISSION}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        bizIds.forEach(bizId -> {
            // 校验是否还有子菜单
            if (mapper.selectCountByParentId(bizId) > 0) {
                throw exception(MENU_EXISTS_CHILDREN);
            }
            // 校验删除的菜单是否存在
            if (ObjectUtil.isNull(mapper.selectByBizId(bizId))) {
                throw exception(MENU_NOT_EXISTS);
            }
            // 删除授予给角色的权限
            sysPermissionService.processMenuDeleted(bizId);
        });
        // 删除菜单
        super.removeByBizIds(bizIds);
    }

    @CacheEvict(cacheNames = {SYS_MENU, SYS_MENU_PERMISSION}, allEntries = true)
    @Override
    public void updateStatus(String bizId, Integer status) {
        super.updateStatus(bizId, status);
    }

    @Cacheable(cacheNames = SYS_MENU, unless = "#result == null")
    @Override
    public List<SysMenuDO> getMenuList() {
        return super.list(SysMenuQueryDTO.builder()
                .status(CommonStatusEnum.ENABLE.getStatus())
                .build());
    }

    @Override
    public List<SysMenuDO> getMenuList(Collection<Integer> menuTypes,
            Collection<Integer> menusStatuses) {
        // 任一一个参数为空，则返回空
        if (CollectionUtils.isAnyEmpty(menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }
        List<SysMenuDO> menuList = self.getMenuList();
        return menuList.stream()
                .filter(menu -> menuTypes.contains(menu.getType())
                        && menusStatuses.contains(menu.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysMenuDO> getMenuList(Collection<String> menuIds, Collection<Integer> menuTypes,
            Collection<Integer> menusStatuses) {
        // 任一一个参数为空，则返回空
        if (CollectionUtils.isAnyEmpty(menuIds, menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }
        List<SysMenuDO> menuList = self.getMenuList();
        return menuList.stream().filter(menu -> menuIds.contains(menu.getBizId())
                        && menuTypes.contains(menu.getType())
                        && menusStatuses.contains(menu.getStatus()))
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = SYS_MENU_PERMISSION, key = "#permission", unless = "#result == null")
    @Override
    public List<SysMenuDO> getMenuListByPermission(String permission) {
        return mapper.selectByPermission(permission);
    }

    /**
     * 校验父菜单是否合法
     * <p>
     * 1. 不能设置自己为父菜单 2. 父菜单不存在 3. 父菜单必须是 {@link SysMenuTypeEnum#MENU} 菜单类型
     *
     * @param parentId 父菜单编号
     * @param childId  当前菜单编号
     */
    public void checkParentResource(String parentId, String childId) {
        if (StrUtil.isBlank(parentId) || ObjectUtil.equal(SysMenuIdEnum.ROOT.getId(), parentId)) {
            return;
        }
        // 不能设置自己为父菜单
        if (parentId.equals(childId)) {
            throw exception(MENU_PARENT_ERROR);
        }
        SysMenuDO menu = mapper.selectByBizId(parentId);
        // 父菜单不存在
        if (ObjectUtil.isNull(menu)) {
            throw exception(MENU_PARENT_NOT_EXISTS);
        }
        // 父菜单必须是目录或者菜单类型
        if (ObjectUtil.notEqual(SysMenuTypeEnum.DIR.getType(), menu.getType())
                && ObjectUtil.notEqual(SysMenuTypeEnum.MENU.getType(), menu.getType())) {
            throw exception(MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }

    /**
     * 校验菜单是否合法
     * <p>
     * 1. 校验相同父菜单编号下，是否存在相同的菜单名
     *
     * @param name     菜单名字
     * @param parentId 父菜单编号
     * @param id       菜单编号
     */
    public void checkResource(String parentId, String name, String id) {
        SysMenuDO menu = mapper.selectByParentIdAndName(parentId, name);
        if (ObjectUtil.isNull(menu)) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (ObjectUtil.isNull(id)) {
            throw exception(MENU_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(menu.getBizId(), id)) {
            throw exception(MENU_NAME_DUPLICATE);
        }
    }

    /**
     * 初始化菜单的通用属性。
     * <p>
     * 例如说，只有目录或者菜单类型的菜单，才设置 icon
     *
     * @param menu 菜单
     */
    private void initMenuProperty(SysMenuDO menu) {
        // 菜单为按钮类型时，无需 component、icon、path 属性，进行置空
        if (ObjectUtil.equal(SysMenuTypeEnum.BUTTON.getType(), menu.getType())) {
            menu.setComponent("");
            menu.setIcon("");
            menu.setPath("");
        }
    }

}