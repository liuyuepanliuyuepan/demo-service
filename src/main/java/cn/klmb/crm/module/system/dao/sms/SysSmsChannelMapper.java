package cn.klmb.crm.module.system.dao.sms;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.sms.SysSmsChannelQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsChannelDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-短信渠道
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsChannelMapper extends KlmbBaseMapper<SysSmsChannelDO, SysSmsChannelQueryDTO> {

}
