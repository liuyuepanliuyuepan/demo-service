package cn.klmb.demo.framework.common.util.number;

import cn.hutool.core.util.StrUtil;

/**
 * 数字的工具类，补全 {@link cn.hutool.core.util.NumberUtil} 的功能
 *
 * @author 快乐萌宝
 */
public class NumberUtils {

    public static Long parseLong(String str) {
        return StrUtil.isNotEmpty(str) ? Long.valueOf(str) : null;
    }

}
