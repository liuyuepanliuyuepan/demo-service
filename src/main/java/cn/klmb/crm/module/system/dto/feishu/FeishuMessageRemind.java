package cn.klmb.crm.module.system.dto.feishu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CRM联系时间提醒 飞书
 *
 * @author liuyuepan
 * @date 2023/4/13
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FeishuMessageRemind {

    /**
     * 文本
     */
    @Builder.Default
    private String msg_type = "text";

    /**
     * timestamp为距当前时间不超过 1 小时(3600)的时间戳，时间单位s，如：1599360473
     */
    private Long timestamp;

    /**
     * 签名的算法：把 timestamp + "\n" + 密钥 当做签名字符串，使用 HmacSHA256 算法计算签名，再进行 Base64 编码。
     */
    private String sign;

    /**
     * 文本
     */
    private Content content;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Content {

        /**
         * 正文
         */
        private String text;
    }

}
