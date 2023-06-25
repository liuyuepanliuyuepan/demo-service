package cn.klmb.demo.module.system.dao.notify;


import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.notify.SysNotifyMessageQueryDTO;
import cn.klmb.demo.module.system.entity.notify.SysNotifyMessageDO;
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
