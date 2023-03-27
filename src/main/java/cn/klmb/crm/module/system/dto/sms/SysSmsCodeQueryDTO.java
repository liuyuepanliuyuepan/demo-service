package cn.klmb.crm.module.system.dto.sms;

import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsCodeDO;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统管理-短信验证码
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysSmsCodeQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String code;
    /**
     * 发送场景
     * <p>
     * 枚举 {@link SysSmsCodeDO}
     */
    private Integer scene;
    /**
     * 创建 IP
     */
    private String createIp;
    /**
     * 今日发送的第几条
     */
    private Integer todayIndex;
    /**
     * 是否使用
     */
    private Boolean used;
    /**
     * 使用时间
     */
    private LocalDateTime usedTime;
    /**
     * 使用 IP
     */
    private String usedIp;

}
