package cn.klmb.demo.module.system.entity.logger;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.demo.framework.common.enums.UserTypeEnum;
import cn.klmb.demo.module.system.enums.logger.LoginLogTypeEnum;
import cn.klmb.demo.module.system.enums.logger.LoginResultEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统管理-登录日志
 *
 * @author liuyuepan
 * @date 2022/12/12
 */
@TableName("sys_login_log")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysLoginLogDO extends KlmbBaseDO {

    /**
     * 日志类型
     * <p>
     * 枚举 {@link LoginLogTypeEnum}
     */
    private Integer logType;
    /**
     * 链路追踪编号
     */
    private String traceId;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 用户类型
     * <p>
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 用户账号
     * <p>
     * 冗余，因为账号可以变更
     */
    private String username;
    /**
     * 登录结果
     * <p>
     * 枚举 {@link LoginResultEnum}
     */
    private Integer result;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

}
