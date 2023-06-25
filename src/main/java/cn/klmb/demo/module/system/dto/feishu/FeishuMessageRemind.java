package cn.klmb.demo.module.system.dto.feishu;

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
     * 文本
     */
    private String content;

    /**
     * 消息接收者的ID，ID类型应与查询参数receive_id_type 对应；推荐使用 OpenID
     */
    private String receive_id;


    /**
     * 由开发者生成的唯一字符串序列，用于发送消息请求去重；持有相同uuid的请求1小时内至多成功发送一条消息
     */
    private String uuid;


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
