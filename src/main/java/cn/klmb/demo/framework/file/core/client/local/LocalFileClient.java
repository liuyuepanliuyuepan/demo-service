package cn.klmb.demo.framework.file.core.client.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.klmb.demo.framework.file.core.client.AbstractFileClient;
import java.io.File;

/**
 * 本地文件客户端
 *
 * @author 快乐萌宝
 */
public class LocalFileClient extends AbstractFileClient<LocalFileClientConfig> {

    public LocalFileClient(String id, LocalFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // 补全风格。例如说 Linux 是 /，Windows 是 \
        if (!config.getBasePath().endsWith(File.separator)) {
            config.setBasePath(config.getBasePath() + File.separator);
        }
    }

    @Override
    public String upload(byte[] content, String path, String bizId, String type) {
        // 执行写入
        String filePath = getFilePath(path);
        FileUtil.writeBytes(content, filePath);
        // 拼接返回路径
        if (BooleanUtil.isTrue(this.config.getStaticFile())) { // 静态文件
            return config.getDomain() + "/" + path;
        } else {
            return super.formatFileUrl(config.getDomain(), bizId);
        }
    }

    @Override
    public void delete(String path) {
        String filePath = getFilePath(path);
        FileUtil.del(filePath);
    }

    @Override
    public byte[] getContent(String path) {
        String filePath = getFilePath(path);
        return FileUtil.readBytes(filePath);
    }

    private String getFilePath(String path) {
        return config.getBasePath() + path;
    }

}
