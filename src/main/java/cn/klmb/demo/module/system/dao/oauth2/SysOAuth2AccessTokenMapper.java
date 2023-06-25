package cn.klmb.demo.module.system.dao.oauth2;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.oauth2.SysOAuth2AccessTokenQueryDTO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;


/**
 * oauth2访问令牌
 *
 * @author liuyuepan
 * @date 2022/12/1
 */
@Mapper
public interface SysOAuth2AccessTokenMapper extends
        KlmbBaseMapper<SysOAuth2AccessTokenDO, SysOAuth2AccessTokenQueryDTO> {

    default SysOAuth2AccessTokenDO selectByAccessToken(String accessToken) {
        return selectOne(SysOAuth2AccessTokenDO::getAccessToken, accessToken);
    }

    default List<SysOAuth2AccessTokenDO> selectListByRefreshToken(String refreshToken) {
        return selectList(SysOAuth2AccessTokenDO::getRefreshToken, refreshToken);
    }

}
