package cn.klmb.crm.module.member.enums;


import cn.klmb.crm.framework.common.exception.ErrorCode;

/**
 * member 错误码枚举类 ，使用 1-003-000-000 ~ 1-004-000-000 段
 *
 * @author shilinchuan
 * @date 2023/3/16
 */
public interface ErrorCodeConstants {

    // ========== 客户信息 1003000000 ==========
    ErrorCode MEMBER_USER_NOT_EXISTS = new ErrorCode(1003000001, "客户不存在");
    ErrorCode MEMBER_USER_TEL_EXISTS = new ErrorCode(1003000002, "客户联系方式【{}】已经存在");
    ErrorCode USER_STAR_NOT_EXISTS = new ErrorCode(1003000003, "用户客户标星关系不存在");
    ErrorCode USER_NEXT_TIME_ERROR = new ErrorCode(1003000004, "下次联系时间要取当前时间以后的！");

    // ========== 客户信息 1003001000 ==========
    ErrorCode MEMBER_DELIVERY_NOT_EXISTS = new ErrorCode(1003001001, "客户收货地址不存在");

    // ========== 客户信息 1004000000 ==========
    ErrorCode MEMBER_TEAM_DELETE_ERROR = new ErrorCode(1004000001, "负责人不能退出团队！");

}
