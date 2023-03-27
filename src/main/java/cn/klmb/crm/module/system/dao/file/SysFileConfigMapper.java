package cn.klmb.crm.module.system.dao.file;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.file.SysFileConfigQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileConfigDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-文件配置
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@Mapper
public interface SysFileConfigMapper extends KlmbBaseMapper<SysFileConfigDO, SysFileConfigQueryDTO> {

    default SysFileConfigDO selectByMaster() {
        return selectOne(
                new LambdaQueryWrapper<SysFileConfigDO>().eq(SysFileConfigDO::getMaster, true));
    }

}
