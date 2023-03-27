package cn.klmb.crm.module.system.entity.oauth2;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.module.system.enums.oauth2.SysOAuth2GrantTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * oauth2客户端
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
@TableName("sys_oauth2_client")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysOAuth2ClientDO extends KlmbBaseDO {

    /**
     * 客户端编号
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String secret;
    /**
     * 应用名
     */
    private String name;
    /**
     * 应用图标
     */
    private String logo;
    /**
     * 应用描述
     */
    private String description;
    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 访问令牌的有效期
     */
    private Integer accessTokenValiditySeconds;
    /**
     * 刷新令牌的有效期
     */
    private Integer refreshTokenValiditySeconds;
    /**
     * 可重定向的 URI 地址
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> redirectUris;
    /**
     * 授权类型（模式）
     * <p>
     * 枚举 {@link SysOAuth2GrantTypeEnum}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> authorizedGrantTypes;
    /**
     * 授权范围
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;
    /**
     * 自动授权的 Scope
     * <p>
     * code 授权时，如果 scope 在这个范围内，则自动通过
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> autoApproveScopes;
    /**
     * 权限
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> authorities;
    /**
     * 资源
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> resourceIds;
    /**
     * 附加信息，JSON 格式
     */
    private String additionalInformation;

}
