package cn.klmb.crm.module.system.dao.dept;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseTreeMapper;
import cn.klmb.crm.module.system.dto.dept.SysDeptQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统部门
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Mapper
public interface SysDeptMapper extends KlmbBaseTreeMapper<SysDeptDO, SysDeptQueryDTO> {

}
