package cn.klmb.crm.module.product.enums;

import cn.klmb.crm.framework.common.exception.ErrorCode;

/**
 * product 错误码枚举类 ，使用 1-011-000-000 段
 *
 * @author liuyuepan
 * @date 2023/4/18
 */
public interface ErrorCodeConstants {

    // ========== 产品信息 1011000000 ==========
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(1011000001, "产品不存在");

}
