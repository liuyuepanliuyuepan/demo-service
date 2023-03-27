package cn.klmb.crm.module.system.dao.sms;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.sms.SysSmsLogQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-短信日志
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsLogMapper extends KlmbBaseMapper<SysSmsLogDO, SysSmsLogQueryDTO> {

}
