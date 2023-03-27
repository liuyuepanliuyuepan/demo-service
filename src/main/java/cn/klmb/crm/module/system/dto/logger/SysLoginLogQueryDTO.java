package cn.klmb.crm.module.system.dto.logger;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.crm.framework.common.enums.UserTypeEnum;
import cn.klmb.crm.module.system.enums.logger.LoginLogTypeEnum;
import cn.klmb.crm.module.system.enums.logger.LoginResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统管理-登录日志
 *
 * @author shilinchuan
 * @date 2022/12/12
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysLoginLogQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 日志类型
     * <p>
     * 枚举 {@link LoginLogTypeEnum}
     */
    @DtoFieldQuery
    private Integer logType;
    /**
     * 链路追踪编号
     */
    @DtoFieldQuery
    private String traceId;
    /**
     * 用户编号
     */
    @DtoFieldQuery
    private String userId;
    /**
     * 用户类型
     * <p>
     * 枚举 {@link UserTypeEnum}
     */
    @DtoFieldQuery
    private Integer userType;
    /**
     * 用户账号
     * <p>
     * 冗余，因为账号可以变更
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String username;
    /**
     * 登录结果
     * <p>
     * 枚举 {@link LoginResultEnum}
     */
    @DtoFieldQuery
    private Integer result;
    /**
     * 登录结果
     * <p>
     * 枚举 {@link LoginResultEnum}
     */
    @DtoFieldQuery(queryType = Operator.GT, fieldName = "result")
    private Integer minResult;
    /**
     * 用户 IP
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

}
