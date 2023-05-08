package cn.klmb.crm.module.product.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.klmb.crm.framework.common.core.IntArrayValuable;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上下架类型枚举
 */
@AllArgsConstructor
@Getter
public enum ShelfStatusEnum implements IntArrayValuable {

    ON_SHELF(1, "上架"), // 上架
    UNDER_SHELF(0, "下架"); // 下架

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ShelfStatusEnum::getValue)
            .toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    public static ShelfStatusEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue().equals(value),
                ShelfStatusEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
