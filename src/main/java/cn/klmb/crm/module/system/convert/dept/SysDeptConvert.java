package cn.klmb.crm.module.system.convert.dept;

import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptListReqVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptRespVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptTreeReqVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptUpdateReqVO;
import cn.klmb.crm.module.system.dto.dept.SysDeptQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统部门转换类
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@Mapper
public interface SysDeptConvert {

    SysDeptConvert INSTANCE = Mappers.getMapper(SysDeptConvert.class);

    SysDeptDO convert(SysDeptSaveReqVO saveReqVO);

    SysDeptRespVO convert01(SysDeptDO saveDO);

    SysDeptQueryDTO convert(SysDeptListReqVO reqVO);

    List<SysDeptRespVO> convert(List<SysDeptDO> list);

    List<SysDeptSimpleRespVO> convert01(List<SysDeptDO> list);

    SysDeptDO convert01(SysDeptUpdateReqVO updateReqVO);

    SysDeptQueryDTO convert(SysDeptTreeReqVO reqVO);
}
