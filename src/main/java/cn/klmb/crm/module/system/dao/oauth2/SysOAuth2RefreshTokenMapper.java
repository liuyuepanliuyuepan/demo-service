package cn.klmb.crm.module.system.dao.oauth2;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.klmb.crm.module.system.dto.oauth2.SysOAuth2RefreshTokenQueryDTO;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2RefreshTokenDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * oauth2刷新令牌
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
@Mapper
public interface SysOAuth2RefreshTokenMapper extends
        KlmbBaseMapper<SysOAuth2RefreshTokenDO, SysOAuth2RefreshTokenQueryDTO> {

    default int deleteByRefreshToken(String refreshToken) {
        return delete(new LambdaQueryWrapperX<SysOAuth2RefreshTokenDO>()
                .eq(SysOAuth2RefreshTokenDO::getRefreshToken, refreshToken));
    }

    default SysOAuth2RefreshTokenDO selectByRefreshToken(String refreshToken) {
        return selectOne(SysOAuth2RefreshTokenDO::getRefreshToken, refreshToken);
    }

}
