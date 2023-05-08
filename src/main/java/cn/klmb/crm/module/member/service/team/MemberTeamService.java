package cn.klmb.crm.module.member.service.team;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamReqVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MembersTeamSelectVO;
import cn.klmb.crm.module.member.dto.team.MemberTeamQueryDTO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import java.time.LocalDateTime;
import java.util.List;


/**
 * crm团队成员 Service 接口
 *
 * @author 超级管理员
 */
public interface MemberTeamService extends KlmbBaseService<MemberTeamDO, MemberTeamQueryDTO> {


    /**
     * 添加团队成员
     *
     * @param memberTeamSaveBO data
     */
    void addMember(MemberTeamSaveBO memberTeamSaveBO);


    /**
     * 获取团队成员
     *
     * @param reqVO
     * @return data
     */
    List<MembersTeamSelectVO> getMembers(MemberTeamReqVO reqVO);

    /**
     * 删除团队成员
     *
     * @param memberTeamSaveBO data
     */
    void deleteMember(MemberTeamSaveBO memberTeamSaveBO);


    /**
     * 退出团队
     *
     * @param reqVO
     */
    void exitTeam(MemberTeamReqVO reqVO);

    /**
     * 添加单条团队成员数据
     *
     * @param type   对应类型
     * @param typeId 对应类型ID
     * @param userId 用户ID
     * @param power  读写类型
     */
    void addSingleMember(Integer type, String typeId, String userId, Integer power,
            LocalDateTime expiresTime);


}
