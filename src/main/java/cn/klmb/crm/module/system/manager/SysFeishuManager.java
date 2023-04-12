package cn.klmb.crm.module.system.manager;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.klmb.crm.framework.common.util.json.JsonUtils;
import cn.klmb.crm.module.system.dto.feishu.FeishuAccessTokenDTO;
import cn.klmb.crm.module.system.dto.feishu.FeishuMinAppResultDTO;
import cn.klmb.crm.module.system.dto.feishu.FeishuWebResultDTO;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
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

    @Value("${feishu.api.endpoint}")
    private String endpoint;

    @Value("${feishu.api.appId}")
    private String appId;

    @Value("${feishu.api.appSecret}")
    private String appSecret;


    public String getAccessToken() {
        FeishuAccessTokenDTO build = FeishuAccessTokenDTO.builder().appId(appId)
                .appSecret(appSecret).build();
        HashMap<String, String> headers = new HashMap<>(1);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        String result = HttpUtil.createPost(endpoint + "/auth/v3/tenant_access_token/internal")
                .addHeaders(headers).body(JsonUtils.toJsonString(build)).execute().charset("utf-8")
                .body();
        log.info("飞书自建应用获取tenant_access_token的返回结果为{}", result);
        JSONObject entries = JSONUtil.parseObj(result);
        if (Integer.parseInt(entries.get("code").toString()) != 0) {
            throw exception(ErrorCodeConstants.TENANT_ACCESS_TOKEN_ERROR);
        }
        return entries.get("tenant_access_token").toString();
    }

    /**
     * 小程序免登
     *
     * @param code
     * @return
     */
    public FeishuMinAppResultDTO code2session(String code) {
        String accessToken = getAccessToken();
        HashMap<String, String> headers = new HashMap<>(1);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Authorization", "Bearer " + accessToken);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("code", code);
        String result = HttpUtil.createPost(endpoint + "/mina/v2/tokenLoginValidate")
                .addHeaders(headers)
                .body(jsonObject.toString()).execute().charset("utf-8").body();
        log.info("飞书小程序自建应用的登录获取用户身份返回结果为{}", result);
        JSONObject entries = JSONUtil.parseObj(result);
        if (Integer.parseInt(entries.get("code").toString()) != 0) {
            throw exception(ErrorCodeConstants.MIN_APP_TOKEN_LOGIN_VALIDATE);
        }
        return JsonUtils.parseObject(entries.get("data").toString(), FeishuMinAppResultDTO.class);
    }


    /**
     * 飞书网页登录
     *
     * @param code
     * @return
     */
    public FeishuWebResultDTO authenV1AccessToken(String code) {
        String accessToken = getAccessToken();
        HashMap<String, String> headers = new HashMap<>(1);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Authorization", "Bearer " + accessToken);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("code", code);
        jsonObject.set("grant_type", "authorization_code");
        String result = HttpUtil.createPost(endpoint + "/authen/v1/access_token")
                .addHeaders(headers)
                .body(jsonObject.toString()).execute().charset("utf-8").body();
        log.info("飞书网页登录获取用户身份返回结果为{}", result);
        JSONObject entries = JSONUtil.parseObj(result);
        if (Integer.parseInt(entries.get("code").toString()) != 0) {
            throw exception(ErrorCodeConstants.WEB_AUTHEN_ACCESS_TOKEN);
        }
        return JsonUtils.parseObject(entries.get("data").toString(), FeishuWebResultDTO.class);
    }


}
