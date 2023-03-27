package cn.klmb.crm.module.system.service.file;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.FILE_NOT_EXISTS;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.util.io.FileUtils;
import cn.klmb.crm.framework.file.core.client.FileClient;
import cn.klmb.crm.framework.file.core.utils.FileTypeUtils;
import cn.klmb.crm.module.system.dao.file.SysFileMapper;
import cn.klmb.crm.module.system.dto.file.SysFileQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileDO;
import cn.klmb.crm.module.system.enums.file.SysFileClientEnum;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


/**
 * 系统管理-文件
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@Service
public class SysFileServiceImpl extends
        KlmbBaseServiceImpl<SysFileDO, SysFileQueryDTO, SysFileMapper> implements SysFileService {

    private final SysFileConfigService sysFileConfigService;

    public SysFileServiceImpl(SysFileConfigService sysFileConfigService, SysFileMapper mapper) {
        this.sysFileConfigService = sysFileConfigService;
        this.mapper = mapper;
    }

    @Override
    public SysFileDO saveFile(String name, String path, byte[] content) {
        // 上传到文件存储器
        FileClient client = sysFileConfigService.getMasterFileClient();
        Assert.notNull(client, "客户端(master) 不能为空");
        return this.saveFile(name, path, content, client);
    }

    @Override
    public SysFileDO saveFileStatic(String name, String path, byte[] content) {
        FileClient client = sysFileConfigService.getFileClient(SysFileClientEnum.STATIC.getId());
        Assert.notNull(client, "静态文件存储客户端不能为空");
        return this.saveFile(name, path, content, client);
    }

    @Override
    @SneakyThrows
    public SysFileDO saveFile(String name, String path, byte[] content, FileClient client) {
        // 计算默认的 path 名
        String type = FileTypeUtils.getMineType(content, name);
        if (StrUtil.isEmpty(path)) {
            path = FileUtils.generatePath(content, name);
        }
        // 如果 name 为空，则使用 path 填充
        if (StrUtil.isEmpty(name)) {
            name = path;
        }

        // 格式化文件存储路径位 yyyy-MM/dd/HH/
        LocalDateTime now = LocalDateTime.now();
        path = LocalDateTimeUtil.format(now, "yyyyMM") + "/"
                + LocalDateTimeUtil.format(now, "dd") + "/"
                + LocalDateTimeUtil.format(now, "HH") + "/" + path;

        // 文件业务ID
        String bizId = IdUtil.fastSimpleUUID();

        // 上传到文件存储器
        String url = client.upload(content, path, bizId, type);

        // 保存到数据库
        SysFileDO file = SysFileDO.builder()
                .bizId(bizId)
                .configId(client.getId())
                .name(name)
                .path(path)
                .url(url)
                .type(type)
                .size(content.length)
                .build();
        super.saveDO(file);
        return file;
    }

    @Override
    public void deleteFile(String bizId) throws Exception {
        // 校验存在
        SysFileDO file = this.validateFileExists(bizId);

        // 从文件存储器中删除
        FileClient client = sysFileConfigService.getFileClient(file.getConfigId());
        Assert.notNull(client, "客户端({}) 不能为空", file.getConfigId());
        client.delete(file.getPath());

        // 删除记录
        mapper.deleteById(file.getId());
    }

    @Override
    public byte[] getFileContent(String configId, String path) throws Exception {
        FileClient client = sysFileConfigService.getFileClient(configId);
        Assert.notNull(client, "客户端({}) 不能为空", configId);
        return client.getContent(path);
    }

    private SysFileDO validateFileExists(String bizId) {
        SysFileDO fileDO = mapper.selectByBizId(bizId);
        if (fileDO == null) {
            throw exception(FILE_NOT_EXISTS);
        }
        return fileDO;
    }
}