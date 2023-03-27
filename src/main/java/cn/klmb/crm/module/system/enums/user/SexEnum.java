package cn.klmb.crm.module.system.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别的枚举值
 *
 * @author 快乐萌宝
 */
@Getter
@AllArgsConstructor
public enum SexEnum {

    /**
     * 男
     */
    MALE(1),
    /**
     * 女
     */
    FEMALE(2),
    /**
     * 未知
     */
    UNKNOWN(3);

    /**
     * 性别
     */
    private final Integer sex;

}
