package cn.klmb.crm.module.system.convert.file;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.system.controller.admin.file.vo.config.SysFileConfigPageReqVO;
import cn.klmb.crm.module.system.controller.admin.file.vo.config.SysFileConfigRespVO;
import cn.klmb.crm.module.system.controller.admin.file.vo.config.SysFileConfigSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.file.vo.config.SysFileConfigUpdateReqVO;
import cn.klmb.crm.module.system.dto.file.SysFileConfigQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 文件配置类型转换类
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@Mapper
public interface SysFileConfigConvert {

    SysFileConfigConvert INSTANCE = Mappers.getMapper(SysFileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    SysFileConfigDO convert(SysFileConfigSaveReqVO saveReqVO);

    @Mapping(target = "config", ignore = true)
    SysFileConfigDO convert(SysFileConfigUpdateReqVO updateReqVO);

    SysFileConfigRespVO convert(SysFileConfigDO sysDictTypeDO);

    SysFileConfigQueryDTO convert(SysFileConfigPageReqVO reqVO);

    KlmbPage<SysFileConfigRespVO> convert(KlmbPage<SysFileConfigDO> page);

}
