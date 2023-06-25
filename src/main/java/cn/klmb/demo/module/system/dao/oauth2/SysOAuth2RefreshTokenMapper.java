package cn.klmb.demo.module.system.dao.oauth2;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.klmb.demo.module.system.dto.oauth2.SysOAuth2RefreshTokenQueryDTO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2RefreshTokenDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * oauth2刷新令牌
 *
 * @author liuyuepan
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
