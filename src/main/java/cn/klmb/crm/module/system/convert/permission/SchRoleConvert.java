package cn.klmb.crm.module.system.convert.permission;

import cn.klmb.crm.module.system.controller.admin.permission.vo.role.SysRolePageReqVO;
import cn.klmb.crm.module.system.dto.permission.SchRoleQueryQueryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统角色转换类
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@Mapper
public interface SchRoleConvert {

    SchRoleConvert INSTANCE = Mappers.getMapper(SchRoleConvert.class);

    SchRoleQueryQueryDTO convert(SysRolePageReqVO reqVO);
}
