package cn.klmb.crm.module.member.service.team;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.Const;
import cn.klmb.crm.framework.common.util.servlet.ApplicationContextHolder;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.service.detail.ContractDetailService;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MembersTeamSelectVO;
import cn.klmb.crm.module.member.dao.team.MemberTeamMapper;
import cn.klmb.crm.module.member.dto.team.MemberTeamQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserRespVO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * crm团队成员 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberTeamServiceImpl extends
        KlmbBaseServiceImpl<MemberTeamDO, MemberTeamQueryDTO, MemberTeamMapper> implements
        MemberTeamService {

    private final SysUserService sysUserService;

    private final MemberUserService memberUserService;

    private final SysDeptService sysDeptService;

    public MemberTeamServiceImpl(MemberTeamMapper mapper, SysUserService sysUserService,
            @Lazy MemberUserService memberUserService, SysDeptService sysDeptService) {
        this.sysUserService = sysUserService;
        this.memberUserService = memberUserService;
        this.sysDeptService = sysDeptService;
        this.mapper = mapper;
    }


    /**
     * 添加团队成员
     *
     * @param crmEnum          对应类型
     * @param memberTeamSaveBO data
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMember(CrmEnum crmEnum, MemberTeamSaveBO memberTeamSaveBO) {
        addMember(crmEnum, memberTeamSaveBO, false, new ArrayList<>());
    }


    private void addMember(CrmEnum crmEnum, MemberTeamSaveBO memberTeamSaveBO, boolean append,
            List<MemberTeamDO> teamMembers) {
        if (StrUtil.isNotBlank(memberTeamSaveBO.getTime())) {
            LocalDateTime parse = LocalDateTime.parse(memberTeamSaveBO.getTime(),
                    DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
            memberTeamSaveBO.setExpiresTime(parse);
        } else {
            memberTeamSaveBO.setExpiresTime(null);
        }
        if (memberTeamSaveBO.getPower() != 1 && memberTeamSaveBO.getPower() != 2) {
            throw exception(ErrorCodeConstants.SYSTEM_NO_VALID);
        }
        if (memberTeamSaveBO.getUserIds().size() == 0) {
            return;
        }
        for (String bizId : memberTeamSaveBO.getBizIds()) {

            List<String> memberIds = new ArrayList<>(memberTeamSaveBO.getUserIds());
            LambdaQueryWrapper<MemberTeamDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(MemberTeamDO::getUserId).eq(MemberTeamDO::getType, crmEnum.getType())
                    .eq(MemberTeamDO::getTypeId, bizId).in(MemberTeamDO::getUserId, memberIds);
            List<String> userIds = listObjs(wrapper, TypeUtils::castToString);
            if (userIds.size() > 0) {
                lambdaUpdate()
                        .set(MemberTeamDO::getPower, memberTeamSaveBO.getPower())
                        .set(MemberTeamDO::getExpiresTime, memberTeamSaveBO.getExpiresTime())
                        .in(MemberTeamDO::getUserId, userIds).update();
                memberIds.removeAll(userIds);
            }
            Object[] objects = getTypeName(crmEnum, bizId);
            if (objects.length == 0) {
                continue;
            }
            memberIds.removeIf(memberId -> Objects.equals(objects[0], memberId));
            if (memberIds.size() == 0) {
                continue;
            }
            for (String memberId : memberIds) {
                MemberTeamDO memberTeamDO = new MemberTeamDO();
                memberTeamDO.setPower(memberTeamSaveBO.getPower());
                memberTeamDO.setType(crmEnum.getType());
                memberTeamDO.setTypeId(bizId);
                memberTeamDO.setCreateTime(LocalDateTime.now());
                memberTeamDO.setExpiresTime(memberTeamSaveBO.getExpiresTime());
                memberTeamDO.setUserId(memberId);
                teamMembers.add(memberTeamDO);
            }
            if (memberTeamSaveBO.getChangeType() != null && crmEnum == CrmEnum.CUSTOMER) {
                if (memberTeamSaveBO.getChangeType().contains(1)) {
                    LambdaQueryWrapper<MemberContactsDO> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(MemberContactsDO::getCustomerId, bizId);
                    queryWrapper.select(MemberContactsDO::getBizId);
                    List<String> bizIds = ApplicationContextHolder.getBean(
                                    MemberContactsService.class)
                            .listObjs(queryWrapper, TypeUtils::castToString);
                    addMember(CrmEnum.CONTACTS, new MemberTeamSaveBO(bizIds, memberTeamSaveBO),
                            true, teamMembers);
                }
            }
        }
        if (!append) {
            saveBatch(teamMembers, Const.BATCH_SAVE_SIZE);
        }
    }

    private Object[] getTypeName(CrmEnum crmEnum, String typeId) {
        switch (crmEnum) {
            case CUSTOMER: {
                MemberUserDO memberUserDO = ApplicationContextHolder.getBean(
                                MemberUserService.class)
                        .lambdaQuery()
                        .select(MemberUserDO::getOwnerUserId, MemberUserDO::getName)
                        .eq(MemberUserDO::getBizId, typeId)
                        .one();
                return new Object[]{memberUserDO.getOwnerUserId(), memberUserDO.getName()};
            }
            case CONTACTS: {
                MemberContactsDO contacts = ApplicationContextHolder.getBean(
                                MemberContactsService.class)
                        .lambdaQuery()
                        .select(MemberContactsDO::getOwnerUserId, MemberContactsDO::getName)
                        .eq(MemberContactsDO::getBizId, typeId)
                        .one();
                return new Object[]{contacts.getOwnerUserId(), contacts.getName()};
            }
            case CONTRACT: {
                ContractDetailDO contractDetailDO = ApplicationContextHolder.getBean(
                                ContractDetailService.class)
                        .lambdaQuery()
                        .select(ContractDetailDO::getOwnerUserId, ContractDetailDO::getName)
                        .eq(ContractDetailDO::getBizId, typeId)
                        .one();
                return new Object[]{contractDetailDO.getOwnerUserId(), contractDetailDO.getName()};
            }
            default: {
                return new Object[0];
            }
        }
    }


    /**
     * 获取团队成员
     *
     * @param crmEnum 对应类型
     * @param typeId  对应类型ID
     * @return data
     */
    @Override
    public List<MembersTeamSelectVO> getMembers(CrmEnum crmEnum, String typeId,
            String ownerUserId) {
        List<MembersTeamSelectVO> selectVOS = new ArrayList<>();
        if (StrUtil.isBlank(ownerUserId)) {
            return Collections.emptyList();
        }
        List<MemberTeamDO> teamMembers = lambdaQuery().eq(MemberTeamDO::getType, crmEnum.getType())
                .eq(MemberTeamDO::getTypeId, typeId).list();
        for (MemberTeamDO teamMember : teamMembers) {
            if (Objects.equals(teamMember.getUserId(), ownerUserId)) {
                SysUserRespVO sysUserRespVO = sysUserService.getUserDetailByUserId(
                        ownerUserId);
                MembersTeamSelectVO selectVO = new MembersTeamSelectVO();
                selectVO.setUserId(ownerUserId);
                selectVO.setNickName(sysUserRespVO.getNickname());
                selectVO.setExpiresTime(null);
                selectVO.setPower(3);
                selectVO.setDeptName(sysUserRespVO.getDeptName());
                selectVOS.add(selectVO);
            }
            if (ObjectUtil.notEqual(teamMember.getUserId(), ownerUserId)) {
                MembersTeamSelectVO selectVO = new MembersTeamSelectVO();
                SysUserRespVO sysUserRespVO = sysUserService.getUserDetailByUserId(
                        teamMember.getUserId());
                selectVO.setUserId(teamMember.getUserId());
                selectVO.setPower(teamMember.getPower());
                selectVO.setDeptName(sysUserRespVO.getDeptName());
                selectVO.setNickName(sysUserRespVO.getNickname());
                if (ObjectUtil.isNotNull(teamMember.getExpiresTime())) {
                    selectVO.setExpiresTime(teamMember.getExpiresTime()
                            .format(DateTimeFormatter.ofPattern(
                                    DatePattern.NORM_DATETIME_PATTERN)));
                }
                selectVOS.add(selectVO);
            }
        }
        return selectVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMember(CrmEnum crmEnum, MemberTeamSaveBO memberTeamSaveBO) {
        for (String bizId : memberTeamSaveBO.getBizIds()) {
            if (memberTeamSaveBO.getChangeType() != null && crmEnum == CrmEnum.CUSTOMER) {
                if (memberTeamSaveBO.getChangeType().contains(1)) {
                    LambdaQueryWrapper<MemberContactsDO> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(MemberContactsDO::getCustomerId, bizId);
                    queryWrapper.select(MemberContactsDO::getBizId);
                    List<String> customerIds = ApplicationContextHolder.getBean(
                                    MemberContactsService.class)
                            .listObjs(queryWrapper, TypeUtils::castToString);
                    deleteMember(CrmEnum.CONTACTS,
                            new MemberTeamSaveBO(customerIds, memberTeamSaveBO));
                }
            }
            deleteMembers(crmEnum, bizId, memberTeamSaveBO.getUserIds());
        }
    }

    private void deleteMembers(CrmEnum crmEnum, String typeId, List<String> memberIds) {
        Object[] objects = getTypeName(crmEnum, typeId);
        if (objects.length == 0) {
            return;
        }
        if (memberIds.contains(objects[0])) {
            throw exception(
                    cn.klmb.crm.module.member.enums.ErrorCodeConstants.MEMBER_TEAM_DELETE_ERROR);
        }
        lambdaUpdate()
                .eq(MemberTeamDO::getType, crmEnum.getType())
                .eq(MemberTeamDO::getTypeId, typeId)
                .in(MemberTeamDO::getUserId, memberIds).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exitTeam(CrmEnum crmEnum, String typeId) {
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        deleteMembers(crmEnum, typeId, Collections.singletonList(userId));
    }


}
