package cn.klmb.demo.module.system.dao.file;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.file.SysFileConfigQueryDTO;
import cn.klmb.demo.module.system.entity.file.SysFileConfigDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-文件配置
 *
 * @author liuyuepan
 * @date 2022/12/6
 */
@Mapper
public interface SysFileConfigMapper extends KlmbBaseMapper<SysFileConfigDO, SysFileConfigQueryDTO> {

    default SysFileConfigDO selectByMaster() {
        return selectOne(
                new LambdaQueryWrapper<SysFileConfigDO>().eq(SysFileConfigDO::getMaster, true));
    }

}
