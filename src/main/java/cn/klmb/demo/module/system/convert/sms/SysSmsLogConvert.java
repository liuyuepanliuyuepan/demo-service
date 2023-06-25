package cn.klmb.demo.module.system.convert.sms;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.module.system.controller.admin.sms.vo.log.SysSmsLogPageReqVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.log.SysSmsLogRespVO;
import cn.klmb.demo.module.system.dto.sms.SysSmsLogQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 短信日志类型转换类
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsLogConvert {

    SysSmsLogConvert INSTANCE = Mappers.getMapper(SysSmsLogConvert.class);

    SysSmsLogQueryDTO convert(SysSmsLogPageReqVO reqVO);

    KlmbPage<SysSmsLogRespVO> convert(KlmbPage<SysSmsLogDO> page);

}
