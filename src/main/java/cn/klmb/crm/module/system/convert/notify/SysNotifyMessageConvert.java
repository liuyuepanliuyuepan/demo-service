package cn.klmb.crm.module.system.convert.notify;


import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.system.controller.admin.notify.vo.message.SysNotifyMessagePageReqVO;
import cn.klmb.crm.module.system.controller.admin.notify.vo.message.SysNotifyMessageRespVO;
import cn.klmb.crm.module.system.controller.admin.notify.vo.message.SysNotifyMessageSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.notify.vo.message.SysNotifyMessageUpdateReqVO;
import cn.klmb.crm.module.system.dto.notify.SysNotifyMessageQueryDTO;
import cn.klmb.crm.module.system.entity.notify.SysNotifyMessageDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 站内信 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface SysNotifyMessageConvert {

    SysNotifyMessageConvert INSTANCE = Mappers.getMapper(SysNotifyMessageConvert.class);

    SysNotifyMessageDO convert(SysNotifyMessageSaveReqVO saveReqVO);

    SysNotifyMessageDO convert(SysNotifyMessageUpdateReqVO updateReqVO);

    KlmbPage<SysNotifyMessageRespVO> convert(KlmbPage<SysNotifyMessageDO> page);

    List<SysNotifyMessageRespVO> convert(List<SysNotifyMessageDO> list);

    SysNotifyMessageRespVO convert(SysNotifyMessageDO saveDO);

    SysNotifyMessageQueryDTO convert(SysNotifyMessagePageReqVO reqVO);

}
