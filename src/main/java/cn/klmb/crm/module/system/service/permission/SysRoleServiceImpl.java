package cn.klmb.crm.module.system.service.permission;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.constants.SysRedisKeyConstants.SYS_ROLE;
import static cn.klmb.crm.module.system.constants.SysRedisKeyConstants.SYS_ROLE_BIZ_ID;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DEPT_NOT_FOUND;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.ROLE_ADMIN_CODE_ERROR;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.ROLE_CODE_DUPLICATE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.ROLE_NAME_DUPLICATE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.ROLE_NOT_EXISTS;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.module.system.dao.permission.SysRoleMapper;
import cn.klmb.crm.module.system.dto.permission.SysRoleQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import cn.klmb.crm.module.system.entity.permission.SysRoleDO;
import cn.klmb.crm.module.system.enums.permission.DataScopeEnum;
import cn.klmb.crm.module.system.enums.permission.SysRoleCodeEnum;
import cn.klmb.crm.module.system.enums.permission.SysRoleDeptIdEnum;
import cn.klmb.crm.module.system.enums.permission.SysRoleTypeEnum;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 系统角色
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Service
public class SysRoleServiceImpl extends
        KlmbBaseServiceImpl<SysRoleDO, SysRoleQueryDTO, SysRoleMapper> implements SysRoleService {

    private final SysDeptService sysDeptService;
    private final SysPermissionService sysPermissionService;
    private final SysRoleServiceImpl self;

    public SysRoleServiceImpl(SysRoleMapper mapper, SysDeptService sysDeptService,
            @Lazy SysPermissionService sysPermissionService, @Lazy SysRoleServiceImpl self) {
        this.sysDeptService = sysDeptService;
        this.sysPermissionService = sysPermissionService;
        this.self = self;
        this.mapper = mapper;
    }

    @CacheEvict(cacheNames = SYS_ROLE, allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDO(SysRoleDO saveDO) {
        // 校验角色
        checkDuplicateRole(saveDO.getDeptId(), saveDO.getName(), saveDO.getCode(), null);
        // 插入到数据库
        saveDO.setType(SysRoleTypeEnum.CUSTOM.getType());
        saveDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        saveDO.setDataScope(DataScopeEnum.ALL.getScope()); // 默认可查看所有数据。原因是，可能一些项目不需要项目权限
        return super.saveDO(saveDO);
    }

    @CacheEvict(cacheNames = SYS_ROLE, allEntries = true)
    @Override
    public boolean updateDO(SysRoleDO updateDO) {
        // 校验是否可以更新
        SysRoleDO sysRoleDO = checkUpdateRole(updateDO.getBizId());
        // 校验角色的唯一字段是否重复
        checkDuplicateRole(sysRoleDO.getDeptId(), updateDO.getName(), updateDO.getCode(),
                updateDO.getBizId());
        // 更新到数据库
        return super.updateDO(updateDO);
    }

    @CacheEvict(cacheNames = {SYS_ROLE, SYS_ROLE_BIZ_ID}, allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByBizIds(List<String> bizIds) {
        // 校验是否可以更新
        bizIds.forEach(this::checkUpdateRole);
        // 删除角色
        super.removeByBizIds(bizIds);
        // 删除相关数据
        bizIds.forEach(sysPermissionService::processRoleDeleted);
    }

    @CacheEvict(cacheNames = SYS_ROLE, allEntries = true)
    @Override
    public void updateStatus(String bizId, Integer status) {
        // 校验是否可以更新
        checkUpdateRole(bizId);
        super.updateStatus(bizId, status);
    }

    @Cacheable(cacheNames = SYS_ROLE_BIZ_ID, key = "#bizId", unless = "#result == null")
    @Override
    public SysRoleDO getByBizId(String bizId) {
        return super.getByBizId(bizId);
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<SysRoleDO> roleList) {
        if (CollectionUtil.isEmpty(roleList)) {
            return false;
        }
        return roleList.stream().anyMatch(role -> SysRoleCodeEnum.isSuperAdmin(role.getCode()));
    }

    @Override
    public void updateRoleDataScope(String bizId, Integer dataScope, Set<String> dataScopeDeptIds) {
        // 校验是否可以更新
        checkUpdateRole(bizId);
        // 更新数据范围
        super.updateDO(SysRoleDO.builder()
                .bizId(bizId)
                .dataScope(dataScope)
                .dataScopeDeptIds(dataScopeDeptIds)
                .build());
    }

    @Override
    public SysRoleDO getByDeptIdAndCode(String deptId, String code) {
        return mapper.selectByDeptIdAndCode(deptId, code);
    }

    @Cacheable(cacheNames = SYS_ROLE, unless = "#result == null")
    @Override
    public List<SysRoleDO> getAllRoles() {
        return mapper.selectList();
    }

    @Override
    public List<SysRoleDO> getRoles(Collection<String> bizIds) {
        return self.getAllRoles().stream().filter(roleDO -> bizIds.contains(roleDO.getBizId()))
                .collect(Collectors.toList());
    }

    /**
     * 校验角色的唯一字段是否重复 1. 是否存在相同部门+名字的角色 2. 是否存在相同部门+编码的角色
     *
     * @param deptId 所属部门ID
     * @param name   角色名字
     * @param code   角色编码
     * @param bizId  角色编号
     */
    public void checkDuplicateRole(String deptId, String name, String code, String bizId) {
        // 0. 超级管理员，不允许创建
        if (SysRoleCodeEnum.isSuperAdmin(code)) {
            throw exception(ROLE_ADMIN_CODE_ERROR, code);
        }
        // 1. 部门不存在
        if (StrUtil.isBlank(deptId)) {
            throw exception(DEPT_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(SysRoleDeptIdEnum.SUPER_DEPT_ID.getId(), deptId)) {
            SysDeptDO sysDeptDO = sysDeptService.getByBizId(deptId);
            if (ObjectUtil.isNull(sysDeptDO)) {
                throw exception(DEPT_NOT_FOUND);
            }
        }
        // 2. 当前部门该 name 名字被其它角色所使用
        SysRoleDO sysRole = mapper.selectByDeptIdAndName(deptId, name);
        if (ObjectUtil.isNotNull(sysRole) && ObjectUtil.notEqual(sysRole.getBizId(), bizId)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
        // 3. 当前部门是否存在相同编码的角色
        if (StrUtil.isBlank(code)) {
            return;
        }
        // 当前部门该 code 编码被其它角色所使用
        sysRole = mapper.selectByDeptIdAndCode(deptId, code);
        if (ObjectUtil.isNotNull(sysRole) && ObjectUtil.notEqual(sysRole.getBizId(), bizId)) {
            throw exception(ROLE_CODE_DUPLICATE, code);
        }
    }

    /**
     * 校验角色是否可以被更新
     *
     * @param bizId 角色编号
     */
    public SysRoleDO checkUpdateRole(String bizId) {
        SysRoleDO sysRoleDO = mapper.selectByBizId(bizId);
        if (sysRoleDO == null) {
            throw exception(ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许操作
        if (SysRoleTypeEnum.SYSTEM.getType().equals(sysRoleDO.getType())) {
            throw exception(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
        return sysRoleDO;
    }

}