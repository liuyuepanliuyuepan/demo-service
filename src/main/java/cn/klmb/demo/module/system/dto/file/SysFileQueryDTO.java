package cn.klmb.demo.module.system.dto.file;

import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.demo.module.system.entity.file.SysFileConfigDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统管理-文件
 *
 * @author liuyuepan
 * @date 2022/12/6
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysFileQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 配置编号
     * <p>
     * 关联 {@link SysFileConfigDO#getBizId()}
     */
    @DtoFieldQuery
    private String configId;
    /**
     * 原文件名
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 路径，即文件名
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String path;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 文件的 MIME 类型，例如 "application/octet-stream"
     */
    @DtoFieldQuery
    private String type;
    /**
     * 文件大小
     */
    private Integer size;

}
