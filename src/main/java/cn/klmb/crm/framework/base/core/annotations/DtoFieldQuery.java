package cn.klmb.crm.framework.base.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义DTO查询字段注解
 *
 * @author shilinchuan
 * @date 2022/5/7
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoFieldQuery {

    /**
     * 字段名称(如果为空则按成员名称查询)
     *
     * @return 返回字段名称
     */
    String fieldName() default "";

    /**
     * 查询类型
     *
     * @return 返回查询类型
     */
    Operator queryType() default Operator.EQ;

    /**
     * 时间类型格式化字符串
     *
     * @return 返回格式化字符串
     */
    String format() default "yyyy-MM-dd HH:mm:ss";

    enum Operator {
        /**
         * =
         */
        EQ,
        /**
         * <>
         */
        NE,
        /**
         * >
         */
        GT,
        /**
         * >=
         */
        GE,
        /**
         * <
         */
        LT,
        /**
         * <=
         */
        LE,
        /**
         * between
         */
        BETWEEN,
        /**
         * not between
         */
        NOT_BETWEEN,
        /**
         * like
         */
        LIKE,
        /**
         * not like
         */
        NOT_LIKE,
        /**
         * like '%xxx'
         */
        LIKE_LEFT,
        /**
         * like 'xxx%'
         */
        LIKE_RIGHT,
        /**
         * is null
         */
        IS_NULL,
        /**
         * is not null
         */
        IS_NOT_NULL,
        /**
         * in
         */
        IN,
        /**
         * not in
         */
        NOT_IN;

        Operator() {
        }
    }

}
