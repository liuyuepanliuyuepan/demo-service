package cn.klmb.demo.module.system.dao.sms;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.sms.SysSmsChannelQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsChannelDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-短信渠道
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsChannelMapper extends KlmbBaseMapper<SysSmsChannelDO, SysSmsChannelQueryDTO> {

}
