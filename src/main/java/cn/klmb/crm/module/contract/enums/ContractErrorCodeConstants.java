package cn.klmb.crm.module.contract.enums;


import cn.klmb.crm.framework.common.exception.ErrorCode;

/**
 * Contract 错误码枚举类 ，使用 1-012-000-000 ~ 1-013-000-000 段
 *
 * @author zhouzhipeng
 * @date 2023/4/20
 */
public interface ContractErrorCodeConstants {

    ErrorCode CONTRACT_TRANSFER_ERROR = new ErrorCode(1012000001, "已作废的合同不能转移");
    ErrorCode CONTRACT_NOT_EXIST = new ErrorCode(1012000002, "合同不存在");

    ErrorCode CONTRACT_DATE_ERROR = new ErrorCode(1012000003, "合同结束时间应大于开始时间");
}
