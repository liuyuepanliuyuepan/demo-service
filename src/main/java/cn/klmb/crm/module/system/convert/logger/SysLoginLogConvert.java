package cn.klmb.crm.module.system.convert.logger;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.system.controller.admin.logger.vo.loginlog.SysLoginLogPageReqVO;
import cn.klmb.crm.module.system.controller.admin.logger.vo.loginlog.SysLoginLogRespVO;
import cn.klmb.crm.module.system.dto.logger.SysLoginLogQueryDTO;
import cn.klmb.crm.module.system.entity.logger.SysLoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 登录日志类型转换类
 *
 * @author shilinchuan
 * @date 2022/12/12
 */
@Mapper
public interface SysLoginLogConvert {

    SysLoginLogConvert INSTANCE = Mappers.getMapper(SysLoginLogConvert.class);

    SysLoginLogRespVO convert(SysLoginLogDO sysDictTypeDO);

    SysLoginLogQueryDTO convert(SysLoginLogPageReqVO reqVO);

    KlmbPage<SysLoginLogRespVO> convert(KlmbPage<SysLoginLogDO> page);
}
