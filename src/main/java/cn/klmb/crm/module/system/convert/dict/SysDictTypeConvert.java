package cn.klmb.crm.module.system.convert.dict;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypePageReqVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypeRespVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypeSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypeSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypeUpdateReqVO;
import cn.klmb.crm.module.system.dto.dict.SysDictTypeQueryDTO;
import cn.klmb.crm.module.system.entity.dict.SysDictTypeDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典类型转换类
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@Mapper
public interface SysDictTypeConvert {

    SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

    SysDictTypeDO convert(SysDictTypeSaveReqVO saveReqVO);

    SysDictTypeDO convert(SysDictTypeUpdateReqVO updateReqVO);

    SysDictTypeRespVO convert(SysDictTypeDO sysDictTypeDO);

    List<SysDictTypeSimpleRespVO> convert(List<SysDictTypeDO> list);

    SysDictTypeQueryDTO convert(SysDictTypePageReqVO reqVO);

    KlmbPage<SysDictTypeRespVO> convert(KlmbPage<SysDictTypeDO> page);
}
