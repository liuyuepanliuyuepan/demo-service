package cn.klmb.crm.module.system.manager;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.klmb.crm.framework.common.util.json.JsonUtils;
import cn.klmb.crm.module.system.dto.feishu.FeishuAccessTokenDTO;
import cn.klmb.crm.module.system.dto.feishu.FeishuMessageRemind;
import cn.klmb.crm.module.system.dto.feishu.FeishuMessageRemind.Content;
import cn.klmb.crm.module.system.dto.feishu.FeishuMinAppResultDTO;
import cn.klmb.crm.module.system.dto.feishu.FeishuWebResultDTO;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.contact.v3.model.GetUserReq;
import com.lark.oapi.service.contact.v3.model.GetUserResp;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
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

    @Value("${feishu.api.secret}")
    private String secret;


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


    /**
     * 发送CRM客户联系时间提醒的飞书通知（异步）
     *
     * @param content 消息内容 内容大小不能超过30KB，自动分段发送
     */
    public void sendMsg(String content) {
        ThreadUtil.execAsync(() -> {
            long timestamp = System.currentTimeMillis() / 1000;
            String sign = this.genSign(timestamp);
            if (StrUtil.isBlank(sign)) {
                log.error("CRM客户联系时间提醒消息-飞书，签名生成错误！");
                return;
            }
            String msgCode = RandomUtil.randomNumbers(6);
            String[] contentArray = StrUtil.split(content, 30000);
            List<String> respList = new ArrayList<>();
            for (int i = 0; i < contentArray.length; i++) {
                String c = contentArray[i];
                if (contentArray.length > 1) {
                    c = "[" + msgCode + "-" + i + "]\n" + c;
                }
                FeishuMessageRemind reqFeishu = FeishuMessageRemind.builder()
                        .content(new Content(c))
                        .sign(sign)
                        .timestamp(timestamp)
                        .build();
                respList.add(HttpUtil.post(
                        endpoint + "/bot/v2/hook/ae635f1a-ae69-4785-9e5e-2ebdae33809d",
                        JSONUtil.toJsonStr(reqFeishu), 5000));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }
            log.info("CRM客户联系时间提醒消息-飞书，内容【{}】，响应结果集【{}】", content,
                    CollUtil.join(respList, ","));
        });
    }

    /**
     * 生成签名 设定后，发送的请求是需要签名验证来保障来源可信。 签名的算法：把 timestamp + "\n" + 密钥 当做签名字符串，使用 HmacSHA256 算法计算签名，再进行
     * Base64 编码。
     *
     * @param timestamp 秒
     * @return 签名
     */
    private String genSign(long timestamp) {
        try {
            //把timestamp+"\n"+密钥当做签名字符串
            String stringToSign = timestamp + "\n" + secret;
            //使用HmacSHA256算法计算签名
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(
                    new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(new byte[]{});
            return new String(Base64.encodeBase64(signData));
        } catch (Exception ignored) {
        }
        return null;
    }


    public void getUserInfo() {
        Client client = Client.newBuilder(appId, appSecret)
                .requestTimeout(3, TimeUnit.SECONDS) // 设置httpclient 超时时间，默认永不超时
                .logReqAtDebug(true) // 在 debug 模式下会打印 http 请求和响应的 headers,body 等信息。
                .build();
        try {
            log.info("lalalalalalalalaalalalal");
            GetUserResp resp = client.contact().user()
                    .get(GetUserReq.newBuilder().userId("2a341556").userIdType("user_id").build());
            // 处理服务端错误
            if (!resp.success()) {
                log.info(String.format("code:%s,msg:%s,reqId:%s"
                        , resp.getCode(), resp.getMsg(), resp.getRequestId()));
                return;
            }
            // 业务数据处理
            System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
