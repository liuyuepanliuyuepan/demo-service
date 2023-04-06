package cn.klmb.crm.module.system.dao.notify;


import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.notify.SysNotifyMessageQueryDTO;
import cn.klmb.crm.module.system.entity.notify.SysNotifyMessageDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 站内信 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface SysNotifyMessageMapper extends
        KlmbBaseMapper<SysNotifyMessageDO, SysNotifyMessageQueryDTO> {

}
