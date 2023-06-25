package cn.klmb.demo.module.system.dao.permission;

import static cn.klmb.demo.module.system.constants.SysRedisKeyConstants.SYS_USER_ROLE_USER_ID;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.permission.SysUserRoleQueryDTO;
import cn.klmb.demo.module.system.entity.permission.SysUserRoleDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


/**
 * 用户角色
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@Mapper
public interface SysUserRoleMapper extends KlmbBaseMapper<SysUserRoleDO, SysUserRoleQueryDTO> {

    @Cacheable(cacheNames = SYS_USER_ROLE_USER_ID, key = "#userId", unless = "#result == null")
    default List<SysUserRoleDO> selectListByUserId(String userId) {
        return selectList(new QueryWrapper<SysUserRoleDO>().eq("user_id", userId));
    }

    default List<SysUserRoleDO> selectListByRoleId(String roleId) {
        return selectList(new QueryWrapper<SysUserRoleDO>().eq("role_id", roleId));
    }

    @CacheEvict(cacheNames = SYS_USER_ROLE_USER_ID, key = "#userId")
    default void deleteListByUserIdAndRoleIdIds(String userId, Collection<String> roleIds) {
        delete(new QueryWrapper<SysUserRoleDO>().eq("user_id", userId).in("role_id", roleIds));
    }

    @CacheEvict(cacheNames = SYS_USER_ROLE_USER_ID, key = "#userId")
    default void deleteListByUserId(String userId) {
        delete(new QueryWrapper<SysUserRoleDO>().eq("user_id", userId));
    }

    @CacheEvict(cacheNames = SYS_USER_ROLE_USER_ID, allEntries = true)
    default void deleteListByRoleId(String roleId) {
        delete(new QueryWrapper<SysUserRoleDO>().eq("role_id", roleId));
    }

    default List<SysUserRoleDO> selectListByRoleIds(Collection<String> roleIds) {
        return selectList(SysUserRoleDO::getRoleId, roleIds);
    }

}
