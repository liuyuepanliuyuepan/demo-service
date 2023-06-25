package cn.klmb.demo.module.system.dto.sms;

import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.framework.sms.core.enums.SmsChannelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统管理-短信渠道
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysSmsChannelQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 短信签名
     */
    private String signature;
    /**
     * 渠道编码
     * <p>
     * 枚举 {@link SmsChannelEnum}
     */
    private String code;
    /**
     * 启用状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 短信 API 的账号
     */
    private String apiKey;
    /**
     * 短信 API 的密钥
     */
    private String apiSecret;
    /**
     * 短信发送回调 URL
     */
    private String callbackUrl;

}
