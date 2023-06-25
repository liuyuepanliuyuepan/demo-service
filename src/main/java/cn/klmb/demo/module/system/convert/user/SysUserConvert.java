package cn.klmb.demo.module.system.convert.user;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.demo.module.system.controller.admin.user.vo.SysUserPageReqVO;
import cn.klmb.demo.module.system.controller.admin.user.vo.SysUserRespVO;
import cn.klmb.demo.module.system.controller.admin.user.vo.SysUserSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.user.vo.SysUserScrollPageReqVO;
import cn.klmb.demo.module.system.controller.admin.user.vo.SysUserSimpleRespVO;
import cn.klmb.demo.module.system.controller.admin.user.vo.SysUserUpdateReqVO;
import cn.klmb.demo.module.system.dto.user.SysUserQueryDTO;
import cn.klmb.demo.module.system.entity.user.SysUserDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户转换类
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserDO convert(SysUserSaveReqVO bean);

    SysUserDO convert01(SysUserUpdateReqVO bean);

    SysUserRespVO convert01(SysUserDO bean);

    SysUserQueryDTO convert(SysUserPageReqVO bean);

    SysUserQueryDTO convert(SysUserScrollPageReqVO bean);

    List<SysUserSimpleRespVO> convert01(List<SysUserDO> beans);

    KlmbPage<SysUserRespVO> convert(KlmbPage<SysUserDO> page);

    KlmbScrollPage<SysUserRespVO> convert(KlmbScrollPage<SysUserDO> page);

    List<SysUserDO> convertList(List<SysUserSaveReqVO> saveReqVO);

}
