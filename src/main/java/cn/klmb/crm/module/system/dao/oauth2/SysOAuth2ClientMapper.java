package cn.klmb.crm.module.system.dao.oauth2;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.oauth2.SysOAuth2ClientQueryDTO;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2ClientDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * oauth2客户端
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
@Mapper
public interface SysOAuth2ClientMapper extends
        KlmbBaseMapper<SysOAuth2ClientDO, SysOAuth2ClientQueryDTO> {

    default SysOAuth2ClientDO selectByClientId(String clientId) {
        return selectOne(SysOAuth2ClientDO::getClientId, clientId);
    }

}
