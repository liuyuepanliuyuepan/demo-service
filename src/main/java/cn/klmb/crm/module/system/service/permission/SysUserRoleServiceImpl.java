package cn.klmb.crm.module.system.service.permission;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.permission.SysUserRoleMapper;
import cn.klmb.crm.module.system.dto.permission.SysUserRoleQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysUserRoleDO;
import org.springframework.stereotype.Service;

/**
 * 系统用户角色
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Service
public class SysUserRoleServiceImpl extends
        KlmbBaseServiceImpl<SysUserRoleDO, SysUserRoleQueryDTO, SysUserRoleMapper> implements
        SysUserRoleService {

    public SysUserRoleServiceImpl(SysUserRoleMapper mapper) {
        this.mapper = mapper;
    }

}