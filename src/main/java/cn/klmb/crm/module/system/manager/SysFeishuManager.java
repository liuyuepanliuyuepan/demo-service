package cn.klmb.crm.module.system.manager;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.klmb.crm.framework.common.util.json.JsonUtils;
import cn.klmb.crm.module.system.dto.feishu.FeishuAccessTokenDTO;
import cn.klmb.crm.module.system.dto.feishu.FeishuApiResultDTO;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 飞书
 *
 * @author liuyuepan
 * @date 2023/4/12
 */
@Slf4j
@Component
public class SysFeishuManager {

    @Value("${feishu.api.url}")
    private String url;

    @Value("${feishu.api.appId}")
    private String appId;

    @Value("${feishu.api.appSecret}")
    private String appSecret;


    public String getAccessToken() {
        FeishuAccessTokenDTO build = FeishuAccessTokenDTO.builder().appId(appId)
                .appSecret(appSecret).build();
        HashMap<String, String> headers = new HashMap<>(1);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        String result = HttpUtil.createPost(url + "/auth/v3/tenant_access_token/internal")
                .addHeaders(headers).body(JsonUtils.toJsonString(build)).execute().charset("utf-8")
                .body();
        log.info("飞书自建应用获取tenant_access_token的返回结果为{}", result);
        JSONObject entries = JSONUtil.parseObj(result);
        return entries.get("tenant_access_token").toString();
    }


    public FeishuApiResultDTO code2session(String code) {
        String accessToken = getAccessToken();
        accessToken = "t-7f1bcd13fc57d46bac21793a18e560";
        HashMap<String, String> headers = new HashMap<>(1);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Authorization", "Bearer " + accessToken);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("code", code);
        String result = HttpUtil.createPost(url + "/mina/v2/tokenLoginValidate").addHeaders(headers)
                .body(jsonObject.toString()).execute().charset("utf-8").body();
        log.info("飞书小程序自建应用的登录获取用户身份返回结果为{}", result);
        JSONObject entries = JSONUtil.parseObj(result);
        return JsonUtils.parseObject(entries.get("data").toString(), FeishuApiResultDTO.class);
    }


}
