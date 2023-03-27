package cn.klmb.crm.module.system.dto.oauth2;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.crm.framework.common.enums.UserTypeEnum;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * oauth2刷新令牌
 *
 * @author shilinchuan
 * @date 2022/12/01
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysOAuth2RefreshTokenQueryDTO extends KlmbBaseQueryDTO {

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
    @DtoFieldQuery
    private LocalDateTime expiresTime;

}
