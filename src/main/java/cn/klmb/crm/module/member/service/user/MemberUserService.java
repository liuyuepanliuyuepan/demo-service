package cn.klmb.crm.module.member.service.user;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;


/**
 * 客户-用户 Service 接口
 *
 * @author 超级管理员
 */
public interface MemberUserService extends KlmbBaseService<MemberUserDO, MemberUserQueryDTO> {

    /**
     * 校验用户是否存在
     *
     * @param bizId 业务ID
     */
    void checkMemberUserExists(String bizId);

}
