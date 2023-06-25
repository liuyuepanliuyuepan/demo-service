package cn.klmb.demo.module.system.service.dict;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.DICT_DATA_NOT_ENABLE;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.DICT_DATA_NOT_EXISTS;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.DICT_DATA_VALUE_DUPLICATE;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.DICT_TYPE_NOT_ENABLE;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.DICT_TYPE_NOT_EXISTS;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.klmb.demo.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.framework.common.util.collection.CollectionUtils;
import cn.klmb.demo.module.system.dao.dict.SysDictDataMapper;
import cn.klmb.demo.module.system.dto.dict.SysDictDataQueryDTO;
import cn.klmb.demo.module.system.entity.dict.SysDictDataDO;
import cn.klmb.demo.module.system.entity.dict.SysDictTypeDO;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * 系统管理-字典数据
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
@Service
public class SysDictDataServiceImpl extends
        KlmbBaseServiceImpl<SysDictDataDO, SysDictDataQueryDTO, SysDictDataMapper> implements
        SysDictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<SysDictDataDO> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(SysDictDataDO::getDictType)
            .thenComparingInt(SysDictDataDO::getSort);

    private final SysDictTypeService dictTypeService;

    public SysDictDataServiceImpl(@Lazy SysDictTypeService dictTypeService,
            SysDictDataMapper mapper) {
        this.dictTypeService = dictTypeService;
        this.mapper = mapper;
    }

    @Override
    public List<SysDictDataDO> list() {
        List<SysDictDataDO> list = super.list();
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public boolean saveDO(SysDictDataDO sysDictDataDO) {
        // 校验正确性
        checkCreateOrUpdate(null, sysDictDataDO.getValue(), sysDictDataDO.getDictType());
        return super.save(sysDictDataDO);
    }

    @Override
    public boolean updateDO(SysDictDataDO sysDictDataDO) {
        // 校验正确性
        checkCreateOrUpdate(sysDictDataDO.getBizId(), sysDictDataDO.getValue(),
                sysDictDataDO.getDictType());
        return super.updateByBizId(sysDictDataDO);
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(bizIds);
    }

    @Override
    public long countByDictType(String dictType) {
        return mapper.selectCountByDictType(dictType);
    }

    @Override
    public void validDictDatas(String dictType, Collection<String> values) {
        if (CollUtil.isEmpty(values)) {
            return;
        }
        Map<String, SysDictDataDO> dictDataMap = CollectionUtils.convertMap(
                mapper.selectByDictTypeAndValues(dictType, values), SysDictDataDO::getValue);
        // 校验
        values.forEach(value -> {
            SysDictDataDO dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw exception(DICT_DATA_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dictData.getStatus())) {
                throw exception(DICT_DATA_NOT_ENABLE, dictData.getLabel());
            }
        });
    }

    @Override
    public SysDictDataDO getByDictTypeAndValue(String dictType, String value) {
        return mapper.selectByDictTypeAndValue(dictType, value);
    }

    @Override
    public SysDictDataDO getByDictTypeAndLabel(String dictType, String label) {
        return mapper.selectByDictTypeAndLabel(dictType, label);
    }

    private void checkCreateOrUpdate(String id, String value, String dictType) {
        // 校验自己存在
        checkDictDataExists(id);
        // 校验字典类型有效
        checkDictTypeValid(dictType);
        // 校验字典数据的值的唯一性
        checkDictDataValueUnique(id, dictType, value);
    }

    public void checkDictDataValueUnique(String id, String dictType, String value) {
        SysDictDataDO dictData = mapper.selectByDictTypeAndValue(dictType, value);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dictData.getBizId(), id)) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
    }

    public void checkDictDataExists(String id) {
        if (id == null) {
            return;
        }
        SysDictDataDO dictData = mapper.selectByBizId(id);
        if (dictData == null) {
            throw exception(DICT_DATA_NOT_EXISTS);
        }
    }

    public void checkDictTypeValid(String type) {
        SysDictTypeDO dictType = dictTypeService.getByType(type);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
            throw exception(DICT_TYPE_NOT_ENABLE);
        }
    }
}