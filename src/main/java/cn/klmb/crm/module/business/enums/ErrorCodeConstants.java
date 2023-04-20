package cn.klmb.crm.module.business.enums;

import cn.klmb.crm.framework.common.exception.ErrorCode;

/**
 * business 错误码枚举类 ，使用 1-008-000-000 ~ 1-010-000-000 段
 *
 * @author liuyuepan
 * @date 2023/4/18
 */
public interface ErrorCodeConstants {

    // ========== 商机信息 1008000000 ==========
    ErrorCode BUSINESS_NOT_EXISTS = new ErrorCode(1008000001, "商机不存在");

}
