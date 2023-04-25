package cn.klmb.crm.module.member.service.team;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.Const;
import cn.klmb.crm.framework.common.util.servlet.ApplicationContextHolder;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.service.detail.BusinessDetailService;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamReqVO;
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

    private final BusinessDetailService businessDetailService;

    public MemberTeamServiceImpl(MemberTeamMapper mapper, SysUserService sysUserService,
            @Lazy MemberUserService memberUserService,
            @Lazy BusinessDetailService businessDetailService) {
        this.sysUserService = sysUserService;
        this.memberUserService = memberUserService;
        this.businessDetailService = businessDetailService;
        this.mapper = mapper;
    }


    /**
     * 添加团队成员
     *
     * @param memberTeamSaveBO data
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMember(MemberTeamSaveBO memberTeamSaveBO) {
        addMember(memberTeamSaveBO, false, new ArrayList<>());
    }


    private void addMember(MemberTeamSaveBO memberTeamSaveBO, boolean append,
            List<MemberTeamDO> teamMembers) {
        if (StrUtil.isNotBlank(memberTeamSaveBO.getTime())) {
            LocalDateTime parse = LocalDateTime.parse(memberTeamSaveBO.getTime(),
                    DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
            memberTeamSaveBO.setExpiresTime(parse);
        } else {
            memberTeamSaveBO.setExpiresTime(null);
        }
//        if (memberTeamSaveBO.getPower() != 1 && memberTeamSaveBO.getPower() != 2) {
//            throw exception(ErrorCodeConstants.SYSTEM_NO_VALID);
//        }
        if (memberTeamSaveBO.getUserIds().size() == 0) {
            return;
        }
        for (String bizId : memberTeamSaveBO.getBizIds()) {

            List<String> memberIds = new ArrayList<>(memberTeamSaveBO.getUserIds());
            LambdaQueryWrapper<MemberTeamDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(MemberTeamDO::getUserId)
                    .eq(MemberTeamDO::getType, memberTeamSaveBO.getType())
                    .eq(MemberTeamDO::getTypeId, bizId).in(MemberTeamDO::getUserId, memberIds);
            List<String> userIds = listObjs(wrapper, TypeUtils::castToString);
            if (userIds.size() > 0) {
                lambdaUpdate()
                        .set(MemberTeamDO::getPower, memberTeamSaveBO.getPower())
                        .set(MemberTeamDO::getExpiresTime, memberTeamSaveBO.getExpiresTime())
                        .in(MemberTeamDO::getUserId, userIds).update();
                memberIds.removeAll(userIds);
            }
            Object[] objects = getTypeName(memberTeamSaveBO.getType(), bizId);
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
                memberTeamDO.setType(memberTeamSaveBO.getType());
                memberTeamDO.setTypeId(bizId);
                memberTeamDO.setCreateTime(LocalDateTime.now());
                memberTeamDO.setExpiresTime(memberTeamSaveBO.getExpiresTime());
                memberTeamDO.setUserId(memberId);
                teamMembers.add(memberTeamDO);
            }
            if (memberTeamSaveBO.getChangeType() != null && memberTeamSaveBO.getType() == 2) {
                if (memberTeamSaveBO.getChangeType().contains(1)) {
                    LambdaQueryWrapper<MemberContactsDO> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(MemberContactsDO::getCustomerId, bizId);
                    queryWrapper.select(MemberContactsDO::getBizId);
                    List<String> bizIds = ApplicationContextHolder.getBean(
                                    MemberContactsService.class)
                            .listObjs(queryWrapper, TypeUtils::castToString);
                    addMember(new MemberTeamSaveBO(bizIds, memberTeamSaveBO),
                            true, teamMembers);
                }
            }
        }
        if (!append) {
            saveBatch(teamMembers, Const.BATCH_SAVE_SIZE);
        }
    }

    private Object[] getTypeName(Integer type, String typeId) {
        switch (type) {
            case 2: {
                MemberUserDO memberUserDO = ApplicationContextHolder.getBean(
                                MemberUserService.class)
                        .lambdaQuery()
                        .select(MemberUserDO::getOwnerUserId, MemberUserDO::getName)
                        .eq(MemberUserDO::getBizId, typeId)
                        .one();
                return new Object[]{memberUserDO.getOwnerUserId(), memberUserDO.getName()};
            }
            case 3: {
                MemberContactsDO contacts = ApplicationContextHolder.getBean(
                                MemberContactsService.class)
                        .lambdaQuery()
                        .select(MemberContactsDO::getOwnerUserId, MemberContactsDO::getName)
                        .eq(MemberContactsDO::getBizId, typeId)
                        .one();
                return new Object[]{contacts.getOwnerUserId(), contacts.getName()};
            }

            case 5: {
                BusinessDetailDO businessDetailDO = ApplicationContextHolder.getBean(
                                BusinessDetailService.class)
                        .lambdaQuery()
                        .select(BusinessDetailDO::getOwnerUserId, BusinessDetailDO::getBusinessName)
                        .eq(BusinessDetailDO::getBizId, typeId)
                        .one();
                return new Object[]{businessDetailDO.getOwnerUserId(),
                        businessDetailDO.getBusinessName()};
            }
            default: {
                return new Object[0];
            }
        }
    }


    /**
     * 获取团队成员
     *
     * @param reqVO
     * @return data
     */
    @Override
    public List<MembersTeamSelectVO> getMembers(MemberTeamReqVO reqVO) {
        Integer type = reqVO.getType();
        String typeId = reqVO.getTypeId();
        String ownerUserId = null;
        switch (type) {
            case 2: {
                MemberUserDO memberUserDO = memberUserService.getByBizId(typeId);
                if (memberUserDO == null) {
                    throw exception(
                            cn.klmb.crm.module.member.enums.ErrorCodeConstants.MEMBER_USER_NOT_EXISTS);
                }
                ownerUserId = memberUserDO.getOwnerUserId();
                break;
            }

            case 5: {
                BusinessDetailDO businessDetailDO = businessDetailService.getByBizId(typeId);
                if (businessDetailDO == null) {
                    throw exception(
                            cn.klmb.crm.module.business.enums.ErrorCodeConstants.BUSINESS_NOT_EXISTS);
                }
                ownerUserId = businessDetailDO.getOwnerUserId();
                break;
            }
            default:
        }

        List<MembersTeamSelectVO> selectVOS = new ArrayList<>();
        List<MemberTeamDO> teamMembers = lambdaQuery().eq(MemberTeamDO::getType, type)
                .eq(MemberTeamDO::getTypeId, typeId).list();
        for (MemberTeamDO teamMember : teamMembers) {
            if (Objects.equals(teamMember.getUserId(), ownerUserId)) {
                MembersTeamSelectVO selectVO = new MembersTeamSelectVO();
                SysUserRespVO sysUserRespVO = sysUserService.getUserDetailByUserId(
                        ownerUserId);
                if (ObjectUtil.isNotNull(sysUserRespVO)) {
                    selectVO.setNickName(sysUserRespVO.getNickname());
                    selectVO.setDeptName(sysUserRespVO.getDeptName());
                }
                selectVO.setUserId(ownerUserId);
                selectVO.setExpiresTime(null);
                selectVO.setPower(3);
                selectVOS.add(selectVO);
            }
            if (ObjectUtil.notEqual(teamMember.getUserId(), ownerUserId)) {
                MembersTeamSelectVO selectVO = new MembersTeamSelectVO();
                SysUserRespVO sysUserRespVO = sysUserService.getUserDetailByUserId(
                        teamMember.getUserId());
                if (ObjectUtil.isNotNull(selectVO)) {
                    selectVO.setDeptName(sysUserRespVO.getDeptName());
                    selectVO.setNickName(sysUserRespVO.getNickname());
                }
                selectVO.setUserId(teamMember.getUserId());
                selectVO.setPower(teamMember.getPower());
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
    public void deleteMember(MemberTeamSaveBO memberTeamSaveBO) {
        Integer type = memberTeamSaveBO.getType();
        for (String bizId : memberTeamSaveBO.getBizIds()) {
            if (memberTeamSaveBO.getChangeType() != null
                    && Objects.equals(type, CrmEnum.CUSTOMER.getType())) {
                if (memberTeamSaveBO.getChangeType().contains(1)) {
                    LambdaQueryWrapper<MemberContactsDO> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(MemberContactsDO::getCustomerId, bizId);
                    queryWrapper.select(MemberContactsDO::getBizId);
                    List<String> customerIds = ApplicationContextHolder.getBean(
                                    MemberContactsService.class)
                            .listObjs(queryWrapper, TypeUtils::castToString);
                    memberTeamSaveBO.setType(CrmEnum.CONTACTS.getType());
                    deleteMember(new MemberTeamSaveBO(customerIds, memberTeamSaveBO));
                }
            }
            deleteMembers(type, bizId, memberTeamSaveBO.getUserIds());
        }
    }

    private void deleteMembers(Integer type, String typeId, List<String> memberIds) {
        Object[] objects = getTypeName(type, typeId);
        if (objects.length == 0) {
            return;
        }
        if (memberIds.contains(objects[0])) {
            throw exception(
                    cn.klmb.crm.module.member.enums.ErrorCodeConstants.MEMBER_TEAM_DELETE_ERROR);
        }
        lambdaUpdate()
                .eq(MemberTeamDO::getType, type)
                .eq(MemberTeamDO::getTypeId, typeId)
                .in(MemberTeamDO::getUserId, memberIds).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exitTeam(MemberTeamReqVO reqVO) {
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        deleteMembers(reqVO.getType(), reqVO.getTypeId(), Collections.singletonList(userId));
    }


}
