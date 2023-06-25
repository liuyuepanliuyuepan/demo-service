package cn.klmb.demo.module.system.manager;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.klmb.demo.framework.common.util.json.JsonUtils;
import cn.klmb.demo.module.system.dto.feishu.FeishuAccessTokenDTO;
import cn.klmb.demo.module.system.dto.feishu.FeishuMessageRemind;
import cn.klmb.demo.module.system.dto.feishu.FeishuMessageRemind.Content;
import cn.klmb.demo.module.system.dto.feishu.FeishuMinAppResultDTO;
import cn.klmb.demo.module.system.dto.feishu.FeishuWebResultDTO;
import cn.klmb.demo.module.system.enums.ErrorCodeConstants;
import com.lark.oapi.Client;
import com.lark.oapi.service.contact.v3.model.GetUserReq;
import com.lark.oapi.service.contact.v3.model.GetUserResp;
import com.lark.oapi.service.contact.v3.model.User;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
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
        return "Bearer " + entries.get("tenant_access_token").toString();
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
        headers.put("Authorization", accessToken);
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
        return JSONUtil.toBean(entries.get("data").toString(),
                FeishuMinAppResultDTO.class);
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
        headers.put("Authorization", accessToken);
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
        return JSONUtil.toBean(entries.get("data").toString(),
                FeishuWebResultDTO.class);
    }


    /**
     * 根据飞书用户id查询飞书用户信息
     *
     * @param userId
     * @return
     */
    public User getUserInfo(String userId) {
        try {
            Client client = Client.newBuilder(appId, appSecret)
                    .requestTimeout(3, TimeUnit.SECONDS) // 设置httpclient 超时时间，默认永不超时
                    .logReqAtDebug(true) // 在 debug 模式下会打印 http 请求和响应的 headers,body 等信息。
                    .build();
            GetUserResp resp = client.contact().user()
                    .get(GetUserReq.newBuilder().userId(userId).userIdType("user_id").build());
            // 处理服务端错误
            if (!resp.success()) {
                log.info(String.format("code:%s,msg:%s,reqId:%s"
                        , resp.getCode(), resp.getMsg(), resp.getRequestId()));
                throw exception(ErrorCodeConstants.FEISHU_USER_NOT_EXISTS);
            }
            // 业务数据处理
            return resp.getData().getUser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 发送CRM客户联系时间提醒的飞书通知（异步）
     */
    public void sendMsg(String userId, String content) {
            User userInfo = getUserInfo(userId);
            String msgCode = RandomUtil.randomNumbers(6);
            String[] contentArray = StrUtil.split(content, 30000);
            HashMap<String, String> headers = new HashMap<>(1);
            headers.put("Content-Type", "application/json; charset=UTF-8");
            headers.put("Authorization", getAccessToken());
            for (int i = 0; i < contentArray.length; i++) {
                String c = contentArray[i];
                if (contentArray.length > 1) {
                    c = "[" + msgCode + "-" + i + "]\n" + c;
                }
                FeishuMessageRemind reqFeishu = FeishuMessageRemind.builder()
                        .content(
                                StringEscapeUtils.unescapeJson(JSONUtil.toJsonStr(new Content(c))))
                        .receive_id(userInfo.getOpenId())
                        .uuid(UUID.randomUUID().toString())
                        .build();
                log.info("CRM-飞书机器人入参【{}】", JSONUtil.toJsonStr(reqFeishu));
                String result = HttpUtil.createPost(
                                endpoint + "/im/v1/messages" + "?receive_id_type=open_id")
                        .addHeaders(headers)
                        .body(JSONUtil.toJsonStr(reqFeishu)).execute().charset("utf-8").body();
                log.info("CRM-飞书机器人返回结果【{}】", result);
            }

    }


}
