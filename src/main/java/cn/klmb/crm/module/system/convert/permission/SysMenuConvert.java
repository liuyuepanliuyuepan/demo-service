package cn.klmb.crm.module.system.convert.permission;

import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuListReqVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuPageReqVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuRespVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuUpdateReqVO;
import cn.klmb.crm.module.system.dto.permission.SysMenuQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysMenuDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统菜单转换类
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@Mapper
public interface SysMenuConvert {

    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuDO convert(SysMenuSaveReqVO bean);

    SysMenuDO convert01(SysMenuUpdateReqVO bean);

    SysMenuRespVO convert01(SysMenuDO bean);

    SysMenuQueryDTO convert(SysMenuPageReqVO bean);

    List<SysMenuRespVO> convert(List<SysMenuDO> beans);

    List<SysMenuSimpleRespVO> convert01(List<SysMenuDO> beans);

    SysMenuQueryDTO convert(SysMenuListReqVO reqVO);
}
