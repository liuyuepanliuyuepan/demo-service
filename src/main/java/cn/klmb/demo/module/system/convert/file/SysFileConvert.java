package cn.klmb.demo.module.system.convert.file;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.module.system.controller.admin.file.vo.file.SysFilePageReqVO;
import cn.klmb.demo.module.system.controller.admin.file.vo.file.SysFileRespVO;
import cn.klmb.demo.module.system.dto.file.SysFileQueryDTO;
import cn.klmb.demo.module.system.entity.file.SysFileDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 文件类型转换类
 *
 * @author liuyuepan
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
