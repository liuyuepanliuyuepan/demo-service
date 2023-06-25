package cn.klmb.demo.module.system.dto.config;

import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.demo.module.system.enums.config.SysConfigTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统管理-配置管理
 *
 * @author liuyuepan
 * @date 2022/12/5
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysConfigQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 参数分类
     */
    @DtoFieldQuery
    private String category;
    /**
     * 参数名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 参数键名
     */
    @DtoFieldQuery
    private String configKey;
    /**
     * 参数键值
     */
    private String value;
    /**
     * 参数类型
     * <p>
     * 枚举 {@link SysConfigTypeEnum}
     */
    @DtoFieldQuery
    private Integer type;
    /**
     * 是否可见
     * <p>
     * 不可见的参数，一般是敏感参数，前端不可获取
     */
    @DtoFieldQuery
    private Boolean visible;
    /**
     * 备注
     */
    private String remark;

}
