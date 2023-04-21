package cn.klmb.crm.module.member.service.team;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MembersTeamSelectVO;
import cn.klmb.crm.module.member.dto.team.MemberTeamQueryDTO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
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
     * @param crmEnum          对应类型
     * @param memberTeamSaveBO data
     */
    void addMember(CrmEnum crmEnum, MemberTeamSaveBO memberTeamSaveBO);


    /**
     * 获取团队成员
     *
     * @param crmEnum     对应类型
     * @param typeId      对应类型ID
     * @param ownerUserId 负责人id
     * @return data
     */
    List<MembersTeamSelectVO> getMembers(CrmEnum crmEnum, String typeId, String ownerUserId);

    /**
     * 删除团队成员
     *
     * @param crmEnum          对应类型
     * @param memberTeamSaveBO data
     */
    void deleteMember(CrmEnum crmEnum, MemberTeamSaveBO memberTeamSaveBO);


    /**
     * 退出团队
     *
     * @param crmEnum 对应类型
     * @param typeId  对应类型ID
     */
    void exitTeam(CrmEnum crmEnum, String typeId);

}
