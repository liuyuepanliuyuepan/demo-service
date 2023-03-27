package cn.klmb.crm.framework.common.enums;

import cn.klmb.crm.framework.common.core.IntArrayValuable;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 终端的枚举
 *
 * @author 快乐萌宝
 */
@RequiredArgsConstructor
@Getter
public enum TerminalEnum implements IntArrayValuable {

    //TODO terminal 重复，请参考 '订单来源终端：[1:小程序 2:H5 3:iOS 4:安卓]'
    MINI_PROGRAM(1, "小程序"),
    H5(2, "H5"),
    IOS(3, "iOS"),
    ANDROID(3, "安卓"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TerminalEnum::getTerminal)
            .toArray();

    /**
     * 终端
     */
    private final Integer terminal;
    /**
     * 终端名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
