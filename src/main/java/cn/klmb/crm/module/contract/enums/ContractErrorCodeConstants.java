package cn.klmb.crm.module.contract.enums;


import cn.klmb.crm.framework.common.exception.ErrorCode;

/**
 * Contract 错误码枚举类 ，使用 1-012-000-000 ~ 1-013-000-000 段
 *
 * @author zhouzhipeng
 * @date 2023/4/20
 */
public interface ContractErrorCodeConstants {

    ErrorCode MEMBER_USER_NOT_EXISTS = new ErrorCode(1005000001, "客户不存在");
}
