package cn.klmb.crm.module.system.service.file;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.framework.file.core.client.FileClient;
import cn.klmb.crm.module.system.dto.file.SysFileQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileDO;


/**
 * 系统管理-文件
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
public interface SysFileService extends KlmbBaseService<SysFileDO, SysFileQueryDTO> {

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param name    文件名称
     * @param path    文件路径
     * @param content 文件内容
     * @return 文件路径
     */
    SysFileDO saveFile(String name, String path, byte[] content);

    /**
     * 保存文件到静态文件存储，并返回文件的访问路径（通过nginx可以直接访问到的地址）
     *
     * @param name    文件名称
     * @param path    文件路径
     * @param content 文件内容
     * @return 文件路径
     */
    SysFileDO saveFileStatic(String name, String path, byte[] content);

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param name    文件名称
     * @param path    文件路径
     * @param content 文件内容
     * @param client  文件客户端
     * @return 文件路径
     */
    SysFileDO saveFile(String name, String path, byte[] content, FileClient client);

    /**
     * 删除文件
     *
     * @param bizId 编号
     */
    void deleteFile(String bizId) throws Exception;

    /**
     * 获得文件内容
     *
     * @param configId 配置编号
     * @param path     文件路径
     * @return 文件内容
     */
    byte[] getFileContent(String configId, String path) throws Exception;

}

