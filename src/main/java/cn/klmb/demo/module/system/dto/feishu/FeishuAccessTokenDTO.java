package cn.klmb.demo.module.system.dto.feishu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeishuAccessTokenDTO {

    @JsonProperty(value = "app_id")
    private String appId;

    @JsonProperty(value = "app_secret")
    private String appSecret;
}
