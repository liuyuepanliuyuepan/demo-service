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

    ErrorCode OWNER_USER_NOT_NULL = new ErrorCode(1003000005, "负责人不能为空！");

    ErrorCode MEMBER_USER_NOT_NULL = new ErrorCode(1003000006, "客户名称不能为空！");

    ErrorCode DUPLICATE_NAME = new ErrorCode(1003000007, "存在重复的负责人名称，请在负责人后面拼上手机号并且两者之间用&字符隔开！");


    // ========== 客户信息 1003001000 ==========
    ErrorCode MEMBER_DELIVERY_NOT_EXISTS = new ErrorCode(1003001001, "客户收货地址不存在");

    // ========== 客户信息 1004000000 ==========
    ErrorCode MEMBER_TEAM_DELETE_ERROR = new ErrorCode(1004000001, "负责人不能退出团队！");


    // ========== 公海信息 1005000000 ==========
    ErrorCode POOL_ID_NOT_EXISTS = new ErrorCode(1005000001, "公海id不能为空！");

}
