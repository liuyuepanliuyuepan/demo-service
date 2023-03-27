package cn.klmb.crm.module.system.dao.user;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.user.SysUserQueryDTO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@Mapper
public interface SysUserMapper extends KlmbBaseMapper<SysUserDO, SysUserQueryDTO> {

    default SysUserDO selectByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getUsername, username));
    }

    default SysUserDO selectByEmail(String email) {
        return selectOne(new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getEmail, email));
    }

    default SysUserDO selectByMobile(String mobile) {
        return selectOne(new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getMobile, mobile));
    }

    default void updatePassword(String bizId, String password) {
        update(new SysUserDO().setPassword(password),
                new LambdaUpdateWrapper<SysUserDO>().eq(SysUserDO::getBizId, bizId));
    }

    default void updateStatus(String bizId, Integer status) {
        update(new SysUserDO().setStatus(status),
                new LambdaUpdateWrapper<SysUserDO>().eq(SysUserDO::getBizId, bizId));
    }

}
