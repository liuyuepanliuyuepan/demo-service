package cn.klmb.demo.module.system.dao.file;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.file.SysFileQueryDTO;
import cn.klmb.demo.module.system.entity.file.SysFileDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-文件
 *
 * @author liuyuepan
 * @date 2022/12/6
 */
@Mapper
public interface SysFileMapper extends KlmbBaseMapper<SysFileDO, SysFileQueryDTO> {

}
