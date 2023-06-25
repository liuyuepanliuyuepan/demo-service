package cn.klmb.demo.module.system.dao.dept;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseTreeMapper;
import cn.klmb.demo.module.system.dto.dept.SysDeptQueryDTO;
import cn.klmb.demo.module.system.entity.dept.SysDeptDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统部门
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@Mapper
public interface SysDeptMapper extends KlmbBaseTreeMapper<SysDeptDO, SysDeptQueryDTO> {

}
