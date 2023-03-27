package cn.klmb.crm.module.system.dao.dict;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.dict.SysDictTypeQueryDTO;
import cn.klmb.crm.module.system.entity.dict.SysDictTypeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-字典类型
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@Mapper
public interface SysDictTypeMapper extends KlmbBaseMapper<SysDictTypeDO, SysDictTypeQueryDTO> {

    default SysDictTypeDO selectByType(String type) {
        return selectOne(SysDictTypeDO::getType, type);
    }

    default SysDictTypeDO selectByName(String name) {
        return selectOne(SysDictTypeDO::getName, name);
    }

}
