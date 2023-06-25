package cn.klmb.demo.module.system.service.file;

import cn.klmb.demo.framework.base.core.service.KlmbBaseService;
import cn.klmb.demo.framework.file.core.client.FileClient;
import cn.klmb.demo.framework.file.core.client.FileClientConfig;
import cn.klmb.demo.module.system.dto.file.SysFileConfigQueryDTO;
import cn.klmb.demo.module.system.entity.file.SysFileConfigDO;
import java.util.Map;


/**
 * 系统管理-文件配置
 *
 * @author liuyuepan
 * @date 2022/12/6
 */
public interface SysFileConfigService extends KlmbBaseService<SysFileConfigDO, SysFileConfigQueryDTO> {

    /**
     * 更新文件配置为 Master
     *
     * @param bizId 编号
     */
    void updateFileConfigMaster(String bizId);

    /**
     * 获得指定编号的文件客户端
     *
     * @param bizId 配置编号
     * @return 文件客户端
     */
    FileClient getFileClient(String bizId);

    /**
     * 获得 Master 文件客户端
     *
     * @return 文件客户端
     */
    FileClient getMasterFileClient();

    FileClientConfig parseClientConfig(Integer storage, Map<String, Object> config);

}

