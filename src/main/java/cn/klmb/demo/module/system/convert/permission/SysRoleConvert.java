package cn.klmb.demo.module.system.convert.permission;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.module.system.controller.admin.permission.vo.role.SysRolePageReqVO;
import cn.klmb.demo.module.system.controller.admin.permission.vo.role.SysRoleRespVO;
import cn.klmb.demo.module.system.controller.admin.permission.vo.role.SysRoleSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.permission.vo.role.SysRoleSimpleRespVO;
import cn.klmb.demo.module.system.controller.admin.permission.vo.role.SysRoleUpdateReqVO;
import cn.klmb.demo.module.system.dto.permission.SysRoleQueryDTO;
import cn.klmb.demo.module.system.entity.permission.SysRoleDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统角色转换类
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@Mapper
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleDO convert(SysRoleSaveReqVO bean);

    SysRoleDO convert01(SysRoleUpdateReqVO bean);

    SysRoleRespVO convert01(SysRoleDO bean);

    SysRoleQueryDTO convert(SysRolePageReqVO bean);

    List<SysRoleSimpleRespVO> convert01(List<SysRoleDO> beans);

    KlmbPage<SysRoleRespVO> convert(KlmbPage<SysRoleDO> page);

}
