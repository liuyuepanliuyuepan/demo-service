package cn.klmb.crm.framework.sms.core.client;

import cn.klmb.crm.framework.common.exception.ErrorCode;
import cn.klmb.crm.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;
import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @author 快乐萌宝
 * @see SmsCommonResult
 * @see SmsFrameworkErrorCodeConstants
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {

}
