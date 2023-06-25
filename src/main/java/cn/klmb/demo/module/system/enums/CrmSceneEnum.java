package cn.klmb.demo.module.system.enums;

import cn.klmb.demo.framework.common.core.IntArrayValuable;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuyuepan 场景枚举
 */
@Getter
@AllArgsConstructor
public enum CrmSceneEnum implements IntArrayValuable {

    ALL(1, "全部"),
    SELF(2, "自己负责的"),
    CHILD(3, "下属负责的"),
    STAR(4, "关注的"),

    WIN(5, "赢单商机"),

    LOSE(6, "输单商机"),

    INVALID(7, "无效商机"),

    ING(8, "进行中的商机"),

    ON_SHELF(9, "上架的产品"),

    UNDER_SHELF(10, "下架的产品"),
    ;


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmSceneEnum::getType)
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
