package cn.klmb.demo.framework.common.util.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * 反射工具类
 *
 * @author liuyuepan
 * @date 2022/04/11
 */
public class ReflectUtils {

    /**
     * 获取模板类型
     *
     * @param clazz 类
     * @param index 模板序号(从0开始)
     * @return 返回模板类型
     */
    public static <T> Class<T> findTemplateClass(Class<?> clazz, int index) {
        if (null == clazz) {
            return null;
        }
        Type[] typeArguments = Optional.ofNullable(clazz.getGenericSuperclass())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> (ParameterizedType) t)
                .map(ParameterizedType::getActualTypeArguments)
                .orElse(null);
        Class<T> templateClass = Optional.ofNullable(typeArguments)
                .filter(t -> t[index] instanceof Class)
                .map(t -> (Class<T>) t[index])
                .orElse(null);
        if (null != templateClass) {
            return templateClass;
        }
        return findTemplateClass(clazz.getSuperclass(), index);
    }

}
