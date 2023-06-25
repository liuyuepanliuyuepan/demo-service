package cn.klmb.demo.module.system.convert.sms;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.sms.core.property.SmsChannelProperties;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelPageReqVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelRespVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelSimpleRespVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelUpdateReqVO;
import cn.klmb.demo.module.system.dto.sms.SysSmsChannelQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsChannelDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 短信渠道类型转换类
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsChannelConvert {

    SysSmsChannelConvert INSTANCE = Mappers.getMapper(SysSmsChannelConvert.class);

    SysSmsChannelDO convert(SysSmsChannelSaveReqVO saveReqVO);

    SysSmsChannelDO convert(SysSmsChannelUpdateReqVO updateReqVO);

    SysSmsChannelRespVO convert(SysSmsChannelDO sysDictTypeDO);

    SysSmsChannelQueryDTO convert(SysSmsChannelPageReqVO reqVO);

    KlmbPage<SysSmsChannelRespVO> convert(KlmbPage<SysSmsChannelDO> page);

    List<SysSmsChannelSimpleRespVO> convert(List<SysSmsChannelDO> list);

    SmsChannelProperties convert01(SysSmsChannelDO entity);
}
