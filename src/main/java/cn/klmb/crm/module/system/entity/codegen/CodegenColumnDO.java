package cn.klmb.crm.module.system.entity.codegen;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.crm.module.system.enums.codegen.CodegenColumnHtmlTypeEnum;
import cn.klmb.crm.module.system.enums.codegen.CodegenColumnListConditionEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 代码生成 column 字段定义
 *
 * @author shilinchuan
 * @date 2022/12/28
 */
@TableName("codegen_column")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class CodegenColumnDO extends KlmbBaseDO {

    /**
     * 表编号
     *
     * 关联 {@link CodegenTableDO#getId()}
     */
    private Long tableId;

    // ========== 表相关字段 ==========

    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段类型
     */
    private String dataType;
    /**
     * 字段描述
     */
    private String columnComment;
    /**
     * 是否允许为空
     */
    private Boolean nullable;
    /**
     * 是否主键
     */
    private Boolean primaryKey;
    /**
     * 是否自增
     */
    private Boolean autoIncrement;
    /**
     * 排序
     */
    private Integer ordinalPosition;

    // ========== Java 相关字段 ==========

    /**
     * Java 属性类型
     *
     * 例如说 String、Boolean 等等
     */
    private String javaType;
    /**
     * Java 属性名
     */
    private String javaField;
    /**
     * 字典类型
     *
     * 关联 DictTypeDO 的 type 属性
     */
    private String dictType;
    /**
     * 数据示例，主要用于生成 Swagger 注解的 example 字段
     */
    private String example;

    // ========== CRUD 相关字段 ==========

    /**
     * 是否为 Create 创建操作的字段
     */
    private Boolean createOperation;
    /**
     * 是否为 Update 更新操作的字段
     */
    private Boolean updateOperation;
    /**
     * 是否为 List 查询操作的字段
     */
    private Boolean listOperation;
    /**
     * List 查询操作的条件类型
     *
     * 枚举 {@link CodegenColumnListConditionEnum}
     */
    private String listOperationCondition;
    /**
     * 是否为 List 查询操作的返回字段
     */
    private Boolean listOperationResult;

    // ========== UI 相关字段 ==========

    /**
     * 显示类型
     *
     * 枚举 {@link CodegenColumnHtmlTypeEnum}
     */
    private String htmlType;

}
