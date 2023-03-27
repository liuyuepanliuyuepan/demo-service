package cn.klmb.crm.framework.base.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定keyword查询字段
 *
 * @author shilinchuan
 * @date 2022/5/7
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoKeywordQuery {

    /**
     * 字段名称(如果为空则按成员名称查询)
     *
     * @return 返回字段名称
     */
    String fieldName() default "";

}
