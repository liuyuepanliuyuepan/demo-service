package cn.klmb.crm.module.system.dao.file;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.file.SysFileQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-文件
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@Mapper
public interface SysFileMapper extends KlmbBaseMapper<SysFileDO, SysFileQueryDTO> {

}
