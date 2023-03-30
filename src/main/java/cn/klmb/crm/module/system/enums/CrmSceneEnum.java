package cn.klmb.crm.module.system.enums;

import cn.klmb.crm.framework.common.core.IntArrayValuable;
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
    TRANSFORM(5, "已转化的线索"),
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
