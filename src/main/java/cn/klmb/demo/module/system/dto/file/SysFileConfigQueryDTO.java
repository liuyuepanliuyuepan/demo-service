package cn.klmb.demo.module.system.dto.file;

import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.demo.framework.file.core.client.FileClientConfig;
import cn.klmb.demo.framework.file.core.enums.FileStorageEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统管理-文件配置
 *
 * @author liuyuepan
 * @date 2022/12/6
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysFileConfigQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 配置名
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 存储器
     * <p>
     * 枚举 {@link FileStorageEnum}
     */
    @DtoFieldQuery
    private Integer storage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否为主配置
     * <p>
     * 由于我们可以配置多个文件配置，默认情况下，使用主配置进行文件的上传
     */
    @DtoFieldQuery
    private Boolean master;

    /**
     * 支付渠道配置
     */
    private FileClientConfig config;

}
