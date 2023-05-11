package cn.klmb.crm.module.system.dao.fileextra;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.fileextra.SysFileExtraQueryDTO;
import cn.klmb.crm.module.system.entity.fileextra.SysFileExtraDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CRM附件关联 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface SysFileExtraMapper extends KlmbBaseMapper<SysFileExtraDO, SysFileExtraQueryDTO> {

}
