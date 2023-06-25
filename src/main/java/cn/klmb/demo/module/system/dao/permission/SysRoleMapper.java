package cn.klmb.demo.module.system.dao.permission;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.permission.SysRoleQueryDTO;
import cn.klmb.demo.module.system.entity.permission.SysRoleDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统角色
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@Mapper
public interface SysRoleMapper extends KlmbBaseMapper<SysRoleDO, SysRoleQueryDTO> {

    default SysRoleDO selectByDeptIdAndCode(String deptId, String code) {
        return selectOne(new LambdaQueryWrapper<SysRoleDO>().eq(SysRoleDO::getDeptId, deptId)
                .eq(SysRoleDO::getCode, code));
    }

    default SysRoleDO selectByDeptIdAndName(String deptId, String name) {
        return selectOne(new LambdaQueryWrapper<SysRoleDO>().eq(SysRoleDO::getDeptId, deptId)
                .eq(SysRoleDO::getName, name));
    }

}
