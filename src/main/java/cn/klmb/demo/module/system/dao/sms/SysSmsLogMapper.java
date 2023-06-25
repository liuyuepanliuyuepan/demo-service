package cn.klmb.demo.module.system.dao.sms;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.sms.SysSmsLogQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-短信日志
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsLogMapper extends KlmbBaseMapper<SysSmsLogDO, SysSmsLogQueryDTO> {

}
