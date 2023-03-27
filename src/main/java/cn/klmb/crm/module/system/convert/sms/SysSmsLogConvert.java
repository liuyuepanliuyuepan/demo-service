package cn.klmb.crm.module.system.convert.sms;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.system.controller.admin.sms.vo.log.SysSmsLogPageReqVO;
import cn.klmb.crm.module.system.controller.admin.sms.vo.log.SysSmsLogRespVO;
import cn.klmb.crm.module.system.dto.sms.SysSmsLogQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 短信日志类型转换类
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsLogConvert {

    SysSmsLogConvert INSTANCE = Mappers.getMapper(SysSmsLogConvert.class);

    SysSmsLogQueryDTO convert(SysSmsLogPageReqVO reqVO);

    KlmbPage<SysSmsLogRespVO> convert(KlmbPage<SysSmsLogDO> page);

}
