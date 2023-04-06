package cn.klmb.crm.module.member.service.teamactivity;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.member.dto.teamactivity.MemberTeamActivityQueryDTO;
import cn.klmb.crm.module.member.entity.teamactivity.MemberTeamActivityDO;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * crm团队成员活动 Service 接口
 *
 * @author 超级管理员
 */
public interface MemberTeamActivityService extends
        KlmbBaseService<MemberTeamActivityDO, MemberTeamActivityQueryDTO> {


    /**
     * 提供给子类实现自己的业务
     * <p>
     * 直接调用了 {@link IService#save}
     *
     * @param saveDO 实体
     * @return 实体
     */
    String saveActivityDO(MemberTeamActivityDO saveDO);


    /**
     * 提供给子类实现自己的业务，根据需要实现
     *
     * @param saveDO 实体
     * @return 实体
     */
    boolean updateDO(MemberTeamActivityDO saveDO);
}
