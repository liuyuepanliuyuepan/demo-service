package cn.klmb.crm.module.system.service.dict;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DICT_TYPE_HAS_CHILDREN;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DICT_TYPE_NOT_EXISTS;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.dict.SysDictTypeMapper;
import cn.klmb.crm.module.system.dto.dict.SysDictTypeQueryDTO;
import cn.klmb.crm.module.system.entity.dict.SysDictTypeDO;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * 系统管理-字典类型
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@Service
public class SysDictTypeServiceImpl extends
        KlmbBaseServiceImpl<SysDictTypeDO, SysDictTypeQueryDTO, SysDictTypeMapper> implements
        SysDictTypeService {

    private final SysDictDataService sysDictDataService;

    public SysDictTypeServiceImpl(@Lazy SysDictDataService sysDictDataService,
            SysDictTypeMapper mapper) {
        this.sysDictDataService = sysDictDataService;
        this.mapper = mapper;
    }

    @Override
    public boolean saveDO(SysDictTypeDO sysDictTypeDO) {
        // 校验正确性
        checkCreateOrUpdate(null, sysDictTypeDO.getName(), sysDictTypeDO.getType());
        return super.save(sysDictTypeDO);
    }

    @Override
    public boolean updateDO(SysDictTypeDO sysDictTypeDO) {
        // 校验正确性
        checkCreateOrUpdate(sysDictTypeDO.getBizId(), sysDictTypeDO.getName(), null);
        return super.updateByBizId(sysDictTypeDO);
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        bizIds.forEach(bizId -> {
            // 校验是否存在
            SysDictTypeDO dictType = checkDictTypeExists(bizId);
            // 校验是否有字典数据
            if (sysDictDataService.countByDictType(dictType.getType()) > 0) {
                throw exception(DICT_TYPE_HAS_CHILDREN);
            }
        });
        super.removeByBizIds(bizIds);
    }

    @Override
    public SysDictTypeDO getByType(String type) {
        return mapper.selectByType(type);
    }

    private void checkCreateOrUpdate(String id, String name, String type) {
        // 校验自己存在
        checkDictTypeExists(id);
        // 校验字典类型的名字的唯一性
        checkDictTypeNameUnique(id, name);
        // 校验字典类型的类型的唯一性
        checkDictTypeUnique(id, type);
    }

    public void checkDictTypeNameUnique(String id, String name) {
        SysDictTypeDO dictType = mapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dictType.getBizId(), id)) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
    }

    public void checkDictTypeUnique(String id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        SysDictTypeDO dictType = mapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dictType.getBizId(), id)) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    public SysDictTypeDO checkDictTypeExists(String id) {
        if (id == null) {
            return null;
        }
        SysDictTypeDO dictType = mapper.selectByBizId(id);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }
}