package cn.klmb.crm.module.member.enums;

import cn.klmb.crm.framework.common.core.IntArrayValuable;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户类型
 *
 * @author 快乐萌宝
 */
@Getter
@AllArgsConstructor
public enum MemberUserLevelEnum implements IntArrayValuable {

    BIG_B(1, "大B"),
    SMALL_B(2, "小B"),
    C(3, "C端"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(MemberUserLevelEnum::getType)
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
        return ARRAYS;
    }
}
