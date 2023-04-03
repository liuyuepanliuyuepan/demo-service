package cn.klmb.crm.module.member.entity.team;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * crm团队成员 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_team", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberTeamDO extends KlmbBaseDO {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 权限(1 只读 2 读写 3 负责人)
     */
    private Integer power;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;
    /**
     * 类型，同crm类型
     */
    private Integer type;
    /**
     * 对应类型主键ID
     */
    private String typeId;

}
