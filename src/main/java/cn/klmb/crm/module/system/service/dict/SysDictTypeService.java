package cn.klmb.crm.module.system.service.dict;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.dict.SysDictTypeQueryDTO;
import cn.klmb.crm.module.system.entity.dict.SysDictTypeDO;


/**
 * 系统管理-字典类型
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
public interface SysDictTypeService extends KlmbBaseService<SysDictTypeDO, SysDictTypeQueryDTO> {

    /**
     * 获得字典类型详情
     *
     * @param type 字典类型
     * @return 字典类型详情
     */
    SysDictTypeDO getByType(String type);

}

