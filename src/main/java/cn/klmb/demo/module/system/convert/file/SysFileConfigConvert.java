package cn.klmb.demo.module.system.convert.file;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.module.system.controller.admin.file.vo.config.SysFileConfigPageReqVO;
import cn.klmb.demo.module.system.controller.admin.file.vo.config.SysFileConfigRespVO;
import cn.klmb.demo.module.system.controller.admin.file.vo.config.SysFileConfigSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.file.vo.config.SysFileConfigUpdateReqVO;
import cn.klmb.demo.module.system.dto.file.SysFileConfigQueryDTO;
import cn.klmb.demo.module.system.entity.file.SysFileConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 文件配置类型转换类
 *
 * @author liuyuepan
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
