package cn.klmb.demo.module.system.convert.notify;


import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessagePageReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessageRespVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessageSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessageScrollPageReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.message.SysNotifyMessageUpdateReqVO;
import cn.klmb.demo.module.system.dto.notify.SysNotifyMessageQueryDTO;
import cn.klmb.demo.module.system.entity.notify.SysNotifyMessageDO;
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

    SysNotifyMessageQueryDTO convert(SysNotifyMessageScrollPageReqVO reqVO);

    KlmbScrollPage<SysNotifyMessageRespVO> convert(KlmbScrollPage<SysNotifyMessageDO> page);

}
