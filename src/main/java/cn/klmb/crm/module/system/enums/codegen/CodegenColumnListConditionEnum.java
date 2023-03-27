package cn.klmb.crm.module.system.enums.codegen;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代码生成器的字段过滤条件枚举
 */
@AllArgsConstructor
@Getter
public enum CodegenColumnListConditionEnum {

    EQ("="),
    NE("!="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT_BETWEEN"),
    LIKE("LIKE"),
    NOT_LIKE("NOT_LIKE"),
    LIKE_LEFT("LIKE_LEFT"),
    LIKE_RIGHT("LIKE_RIGHT"),
    IS_NULL("IS_NULL"),
    IS_NOT_NULL("IS_NOT_NULL"),
    IN("IN"),
    NOT_IN("NOT_IN");

    /**
     * 条件
     */
    private final String condition;

}
