package cn.klmb.demo.module.system.convert.permission;

import cn.klmb.demo.module.system.controller.admin.permission.vo.role.SysRolePageReqVO;
import cn.klmb.demo.module.system.dto.permission.SchRoleQueryQueryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统角色转换类
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@Mapper
public interface SchRoleConvert {

    SchRoleConvert INSTANCE = Mappers.getMapper(SchRoleConvert.class);

    SchRoleQueryQueryDTO convert(SysRolePageReqVO reqVO);
}
