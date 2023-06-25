package cn.klmb.demo.module.system.convert.config;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.module.system.controller.admin.config.vo.SysConfigPageReqVO;
import cn.klmb.demo.module.system.controller.admin.config.vo.SysConfigRespVO;
import cn.klmb.demo.module.system.controller.admin.config.vo.SysConfigSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.config.vo.SysConfigUpdateReqVO;
import cn.klmb.demo.module.system.dto.config.SysConfigQueryDTO;
import cn.klmb.demo.module.system.entity.config.SysConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 配置类型转换类
 *
 * @author liuyuepan
 * @date 2022/12/5
 */
@Mapper
public interface SysConfigConvert {

    SysConfigConvert INSTANCE = Mappers.getMapper(SysConfigConvert.class);

    SysConfigDO convert(SysConfigSaveReqVO saveReqVO);

    SysConfigDO convert(SysConfigUpdateReqVO updateReqVO);

    SysConfigRespVO convert(SysConfigDO sysDictTypeDO);

    SysConfigQueryDTO convert(SysConfigPageReqVO reqVO);

    KlmbPage<SysConfigRespVO> convert(KlmbPage<SysConfigDO> page);
}
