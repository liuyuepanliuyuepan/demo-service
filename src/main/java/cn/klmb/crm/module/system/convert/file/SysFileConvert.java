package cn.klmb.crm.module.system.convert.file;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.system.controller.admin.file.vo.file.SysFilePageReqVO;
import cn.klmb.crm.module.system.controller.admin.file.vo.file.SysFileRespVO;
import cn.klmb.crm.module.system.dto.file.SysFileQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 文件类型转换类
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@Mapper
public interface SysFileConvert {

    SysFileConvert INSTANCE = Mappers.getMapper(SysFileConvert.class);

    SysFileRespVO convert(SysFileDO sysFileDO);

    KlmbPage<SysFileRespVO> convert(KlmbPage<SysFileDO> page);

    SysFileQueryDTO convert(SysFilePageReqVO reqVO);

    List<SysFileRespVO> convert(List<SysFileDO> coursewareFiles);
}
