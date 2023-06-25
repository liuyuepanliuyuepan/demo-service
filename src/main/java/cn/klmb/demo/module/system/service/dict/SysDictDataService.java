package cn.klmb.demo.module.system.service.dict;

import cn.klmb.demo.framework.base.core.service.KlmbBaseService;
import cn.klmb.demo.module.system.dto.dict.SysDictDataQueryDTO;
import cn.klmb.demo.module.system.entity.dict.SysDictDataDO;
import java.util.Collection;


/**
 * 系统管理-字典数据
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
public interface SysDictDataService extends KlmbBaseService<SysDictDataDO, SysDictDataQueryDTO> {

    /**
     * 获得指定字典类型的数据数量
     *
     * @param dictType 字典类型
     * @return 数据数量
     */
    long countByDictType(String dictType);

    /**
     * 校验字典数据们是否有效。如下情况，视为无效： 1. 字典数据不存在 2. 字典数据被禁用
     *
     * @param dictType 字典类型
     * @param values   字典数据值的数组
     */
    void validDictDatas(String dictType, Collection<String> values);

    /**
     * 获得指定的字典数据
     *
     * @param dictType 字典类型
     * @param value    字典数据值
     * @return 字典数据
     */
    SysDictDataDO getByDictTypeAndValue(String dictType, String value);

    /**
     * 获得指定的字典数据
     *
     * @param dictType 字典类型
     * @param label    字典数据标签
     * @return 字典数据
     */
    SysDictDataDO getByDictTypeAndLabel(String dictType, String label);
}

