package cn.klmb.crm.module.system.convert.fileextra;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraPageReqVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraReqVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraRespVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraScrollPageReqVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraUpdateReqVO;
import cn.klmb.crm.module.system.dto.fileextra.SysFileExtraQueryDTO;
import cn.klmb.crm.module.system.entity.fileextra.SysFileExtraDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * CRM附件关联 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface SysFileExtraConvert {

    SysFileExtraConvert INSTANCE = Mappers.getMapper(SysFileExtraConvert.class);

    SysFileExtraDO convert(SysFileExtraSaveReqVO saveReqVO);

    SysFileExtraDO convert(SysFileExtraUpdateReqVO updateReqVO);

    KlmbPage<SysFileExtraRespVO> convert(KlmbPage<SysFileExtraDO> page);

    List<SysFileExtraRespVO> convert(List<SysFileExtraDO> list);

    SysFileExtraRespVO convert(SysFileExtraDO saveDO);

    SysFileExtraQueryDTO convert(SysFileExtraPageReqVO reqVO);


    SysFileExtraQueryDTO convert(SysFileExtraReqVO reqVO);

    SysFileExtraQueryDTO convert(SysFileExtraScrollPageReqVO reqVO);


    KlmbScrollPage<SysFileExtraRespVO> convert(KlmbScrollPage<SysFileExtraDO> page);


}
