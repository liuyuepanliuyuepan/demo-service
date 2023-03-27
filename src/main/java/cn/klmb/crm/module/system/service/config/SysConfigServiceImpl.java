package cn.klmb.crm.module.system.service.config;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.CONFIG_KEY_DUPLICATE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.CONFIG_NOT_EXISTS;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.config.SysConfigMapper;
import cn.klmb.crm.module.system.dto.config.SysConfigQueryDTO;
import cn.klmb.crm.module.system.entity.config.SysConfigDO;
import cn.klmb.crm.module.system.enums.config.SysConfigTypeEnum;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 * 系统管理-配置管理
 *
 * @author shilinchuan
 * @date 2022/12/4
 */
@Service
public class SysConfigServiceImpl extends
        KlmbBaseServiceImpl<SysConfigDO, SysConfigQueryDTO, SysConfigMapper> implements
        SysConfigService {


    public SysConfigServiceImpl(SysConfigMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean saveDO(SysConfigDO sysConfigDO) {
        // 校验正确性
        checkCreateOrUpdate(null, sysConfigDO.getConfigKey());
        // 插入参数配置
        sysConfigDO.setType(SysConfigTypeEnum.CUSTOM.getType());
        return super.saveDO(sysConfigDO);
    }

    @Override
    public boolean updateDO(SysConfigDO sysSysConfigDO) {
        // 校验正确性
        checkCreateOrUpdate(sysSysConfigDO.getBizId(), null); // 不允许更新 key
        return super.updateDO(sysSysConfigDO);
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        bizIds.forEach(bizId -> {
            // 校验配置存在
            SysConfigDO config = checkConfigExists(bizId);
            // 内置配置，不允许删除
            if (SysConfigTypeEnum.SYSTEM.getType().equals(config.getType())) {
                throw exception(CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE);
            }
        });
        super.removeByBizIds(bizIds);
    }

    @Override
    public SysConfigDO getByConfigKey(String configKey) {
        return mapper.selectByConfigKey(configKey);
    }

    private void checkCreateOrUpdate(String id, String key) {
        // 校验自己存在
        checkConfigExists(id);
        // 校验参数配置 key 的唯一性
        if (StrUtil.isNotEmpty(key)) {
            checkConfigKeyUnique(id, key);
        }
    }

    public SysConfigDO checkConfigExists(String id) {
        if (id == null) {
            return null;
        }
        SysConfigDO config = mapper.selectByBizId(id);
        if (config == null) {
            throw exception(CONFIG_NOT_EXISTS);
        }
        return config;
    }

    public void checkConfigKeyUnique(String id, String key) {
        SysConfigDO config = mapper.selectByConfigKey(key);
        if (config == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的参数配置
        if (id == null) {
            throw exception(CONFIG_KEY_DUPLICATE);
        }
        if (ObjectUtil.notEqual(config.getBizId(), id)) {
            throw exception(CONFIG_KEY_DUPLICATE);
        }
    }

}