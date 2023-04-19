package cn.klmb.crm.module.business.enums;

import cn.klmb.crm.framework.common.core.IntArrayValuable;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuyuepan 场景枚举
 */
@Getter
@AllArgsConstructor
public enum BusinessStatusEnum implements IntArrayValuable {

    WIN(1, "赢单商机"),

    LOSE(2, "输单商机"),

    INVALID(3, "无效商机"),

    ING(4, "进行中的商机"),
    ;


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BusinessStatusEnum::getType)
            .toArray();
    /**
     * 类型
     */
    private final Integer type;
    /**
     * 名称
     */
    private final String name;


    @Override
    public int[] array() {
        return new int[0];
    }
}
