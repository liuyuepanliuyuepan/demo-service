package cn.klmb.crm.module.system.service.file;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.FILE_CONFIG_NOT_EXISTS;

import cn.hutool.core.util.ObjectUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.util.json.JsonUtils;
import cn.klmb.crm.framework.common.util.validation.ValidationUtils;
import cn.klmb.crm.framework.file.core.client.FileClient;
import cn.klmb.crm.framework.file.core.client.FileClientConfig;
import cn.klmb.crm.framework.file.core.client.FileClientFactory;
import cn.klmb.crm.framework.file.core.enums.FileStorageEnum;
import cn.klmb.crm.module.system.dao.file.SysFileConfigMapper;
import cn.klmb.crm.module.system.dto.file.SysFileConfigQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileConfigDO;
import java.util.List;
import java.util.Map;
import javax.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 系统管理-文件配置
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@Service
public class SysFileConfigServiceImpl extends
        KlmbBaseServiceImpl<SysFileConfigDO, SysFileConfigQueryDTO, SysFileConfigMapper> implements
        SysFileConfigService {

    private final Validator validator;
    private final FileClientFactory fileClientFactory;

    public SysFileConfigServiceImpl(Validator validator, FileClientFactory fileClientFactory,
            SysFileConfigMapper mapper) {
        this.validator = validator;
        this.fileClientFactory = fileClientFactory;
        this.mapper = mapper;
    }

    @Override
    public boolean saveDO(SysFileConfigDO sysFileConfigDO) {
        // 插入
        sysFileConfigDO.setMaster(false);
        return super.saveDO(sysFileConfigDO);
    }

    @Override
    public boolean updateDO(SysFileConfigDO sysFileConfigDO) {
        // 校验存在
        validateFileConfigExists(sysFileConfigDO.getBizId());
        // 更新
        return super.updateDO(sysFileConfigDO);
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(bizIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileConfigMaster(String bizId) {
        // 校验存在
        this.validateFileConfigExists(bizId);
        // 更新其它为非 master
        mapper.updateBatch(SysFileConfigDO.builder()
                .master(false)
                .build());
        // 更新
        super.updateByBizId(SysFileConfigDO.builder()
                .bizId(bizId)
                .master(true)
                .build());
    }

    @Override
    public FileClient getFileClient(String bizId) {
        return this.getFileClient(mapper.selectByBizId(bizId));
    }

    @Override
    public FileClient getMasterFileClient() {
        return this.getFileClient(mapper.selectByMaster());
    }

    @Override
    public FileClientConfig parseClientConfig(Integer storage, Map<String, Object> config) {
        // 获取配置类
        Class<? extends FileClientConfig> configClass = FileStorageEnum.getByStorage(storage)
                .getConfigClass();
        FileClientConfig clientConfig = JsonUtils.parseObject2(JsonUtils.toJsonString(config),
                configClass);
        // 参数校验
        ValidationUtils.validate(validator, clientConfig);
        // 设置参数
        return clientConfig;
    }

    private void validateFileConfigExists(String id) {
        SysFileConfigDO config = mapper.selectByBizId(id);
        if (config == null) {
            throw exception(FILE_CONFIG_NOT_EXISTS);
        }
    }

    private FileClient getFileClient(SysFileConfigDO config) {
        if (ObjectUtil.isNull(config)) {
            throw exception(FILE_CONFIG_NOT_EXISTS);
        }
        return fileClientFactory.createFileClient(config.getBizId(), config.getStorage(),
                config.getConfig());
    }

}