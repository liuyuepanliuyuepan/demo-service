package cn.klmb.crm.module.system.convert.dict;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.system.controller.admin.dict.vo.data.SysDictDataPageReqVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.data.SysDictDataRespVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.data.SysDictDataSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.data.SysDictDataSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.data.SysDictDataUpdateReqVO;
import cn.klmb.crm.module.system.dto.SysDictDataRespDTO;
import cn.klmb.crm.module.system.dto.dict.SysDictDataQueryDTO;
import cn.klmb.crm.module.system.entity.dict.SysDictDataDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典数据转换类
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@Mapper
public interface SysDictDataConvert {

    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    SysDictDataDO convert(SysDictDataSaveReqVO saveReqVO);

    SysDictDataDO convert(SysDictDataUpdateReqVO updateReqVO);

    List<SysDictDataSimpleRespVO> convert(List<SysDictDataDO> list);

    SysDictDataRespVO convert(SysDictDataDO saveDO);

    SysDictDataQueryDTO convert(SysDictDataPageReqVO reqVO);

    KlmbPage<SysDictDataRespVO> convert(KlmbPage<SysDictDataDO> page);

    SysDictDataRespDTO convert1(SysDictDataDO saveDO);
}
