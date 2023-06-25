package cn.klmb.demo.module.system.dto.oauth2;

import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.demo.framework.common.enums.UserTypeEnum;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * oauth2访问令牌
 *
 * @author liuyuepan
 * @date 2022/12/01
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysOAuth2AccessTokenQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 访问令牌
     */
    @DtoFieldQuery
    private String accessToken;
    /**
     * 刷新令牌
     */
    @DtoFieldQuery
    private String refreshToken;
    /**
     * 用户编号
     */
    @DtoFieldQuery
    private String userId;
    /**
     * 用户类型
     *
     * 枚举 {@link UserTypeEnum}
     */
    @DtoFieldQuery
    private Integer userType;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

}
