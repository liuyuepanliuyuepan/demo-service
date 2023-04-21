package cn.klmb.crm.module.member.service.user;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.job.dto.XxlJobChangeTaskDTO;
import cn.klmb.crm.framework.job.entity.XxlJobInfo;
import cn.klmb.crm.framework.job.entity.XxlJobTaskManagerInfo;
import cn.klmb.crm.framework.job.util.XxlJobApiUtils;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPoolBO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserScrollPageReqVO;
import cn.klmb.crm.module.member.convert.user.MemberUserConvert;
import cn.klmb.crm.module.member.dao.user.MemberUserMapper;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.record.MemberOwnerRecordDO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.entity.userpoolrelation.MemberUserPoolRelationDO;
import cn.klmb.crm.module.member.entity.userstar.MemberUserStarDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.record.MemberOwnerRecordService;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.member.service.userpool.MemberUserPoolService;
import cn.klmb.crm.module.member.service.userpoolrelation.MemberUserPoolRelationService;
import cn.klmb.crm.module.member.service.userstar.MemberUserStarService;
import cn.klmb.crm.module.system.entity.config.SysConfigDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.enums.config.SysConfigKeyEnum;
import cn.klmb.crm.module.system.service.config.SysConfigService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 客户-用户 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Slf4j
public class MemberUserServiceImpl extends
        KlmbBaseServiceImpl<MemberUserDO, MemberUserQueryDTO, MemberUserMapper> implements
        MemberUserService {

    private final SysUserService sysUserService;

    private final MemberUserStarService memberUserStarService;

    private final MemberTeamService memberTeamService;

    private final XxlJobApiUtils xxlJobApiUtils;
    private final MemberOwnerRecordService memberOwnerRecordService;

    private final MemberUserPoolRelationService relationService;

    private final MemberContactsService memberContactsService;

    private final MemberUserPoolService memberUserPoolService;

    private final SysConfigService sysConfigService;


    public MemberUserServiceImpl(SysUserService sysUserService, MemberUserMapper mapper,
            MemberUserStarService memberUserStarService, @Lazy MemberTeamService memberTeamService,
            XxlJobApiUtils xxlJobApiUtils, MemberOwnerRecordService memberOwnerRecordService,
            MemberUserPoolRelationService relationService,
            @Lazy MemberContactsService memberContactsService,
            MemberUserPoolService memberUserPoolService, SysConfigService sysConfigService) {
        this.sysUserService = sysUserService;
        this.memberUserStarService = memberUserStarService;
        this.memberTeamService = memberTeamService;
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.memberOwnerRecordService = memberOwnerRecordService;
        this.relationService = relationService;
        this.memberContactsService = memberContactsService;
        this.memberUserPoolService = memberUserPoolService;
        this.sysConfigService = sysConfigService;
        this.mapper = mapper;
    }


    @Override
    public KlmbPage<MemberUserDO> page(MemberUserPageReqVO reqVO) {
        //获取当前用户id
        String userId = reqVO.getUserId();
        List<String> childUserIds = sysUserService.queryChildUserId(
                userId);
        KlmbPage<MemberUserDO> klmbPage = KlmbPage.<MemberUserDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .sortingFields(reqVO.getSortingFields())
                .build();
        MemberUserQueryDTO queryDTO = MemberUserConvert.INSTANCE.convert(reqVO);
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            if (CollUtil.isEmpty(childUserIds)) {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            } else {
                queryDTO.setOwnerUserIds(childUserIds);
                KlmbPage<MemberUserDO> page = super.page(queryDTO, klmbPage);
                return page;
            }
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            queryDTO.setOwnerUserId(userId);
            KlmbPage<MemberUserDO> page = super.page(queryDTO, klmbPage);
            return page;
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = new ArrayList<>();
            //根据用户id集合查询用户id集合负责的客户信息
            List<MemberUserDO> userDOList = super.list(
                    new LambdaQueryWrapper<MemberUserDO>().in(MemberUserDO::getOwnerUserId,
                            childUserIds).eq(MemberUserDO::getDeleted, false));
            if (CollUtil.isNotEmpty(userDOList)) {
                bizIds = CollUtil.unionAll(userDOList.stream().map(MemberUserDO::getBizId)
                        .collect(Collectors.toList()), bizIds);
            }
            //根据当前用户查询团队成员表中客户id,根据客户id查询客户负责人id
            List<MemberTeamDO> memberTeamDOList = memberTeamService.list(
                    new LambdaQueryWrapper<MemberTeamDO>().eq(MemberTeamDO::getUserId, userId)
                            .eq(MemberTeamDO::getType, CrmEnum.CUSTOMER.getType())
                            .eq(MemberTeamDO::getDeleted, false));
            if (CollUtil.isNotEmpty(memberTeamDOList)) {
                bizIds = CollUtil.unionAll(memberTeamDOList.stream().map(MemberTeamDO::getTypeId)
                        .collect(Collectors.toList()), bizIds);
            }
            if (CollUtil.isNotEmpty(bizIds)) {
                //查询 公海中是否存在这些客户,如果存在剔除掉该客户
                List<MemberUserPoolRelationDO> relationDOS = relationService.list(
                        new LambdaQueryWrapper<MemberUserPoolRelationDO>().in(
                                        MemberUserPoolRelationDO::getCustomerId, bizIds)
                                .eq(MemberUserPoolRelationDO::getDeleted, false));
                if (CollUtil.isNotEmpty(relationDOS)) {
                    List<String> collect = relationDOS.stream()
                            .map(MemberUserPoolRelationDO::getCustomerId)
                            .collect(Collectors.toList());
                    bizIds = CollUtil.subtractToList(bizIds, collect);
                }
                if (CollUtil.isNotEmpty(bizIds)) {
                    queryDTO.setBizIds(bizIds);
                    return super.page(queryDTO, klmbPage);
                }
            } else {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            }
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<MemberUserStarDO> memberUserStarDOS = memberUserStarService.list(
                    new LambdaQueryWrapper<MemberUserStarDO>().eq(MemberUserStarDO::getUserId,
                            userId).eq(MemberUserStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(memberUserStarDOS)) {
                List<String> customerIds = memberUserStarDOS.stream()
                        .map(MemberUserStarDO::getCustomerId)
                        .collect(Collectors.toList());
                //查询 公海中是否存在这些客户,如果存在剔除掉该客户
                List<MemberUserPoolRelationDO> relationDOS = relationService.list(
                        new LambdaQueryWrapper<MemberUserPoolRelationDO>().in(
                                        MemberUserPoolRelationDO::getCustomerId, customerIds)
                                .eq(MemberUserPoolRelationDO::getDeleted, false));
                if (CollUtil.isNotEmpty(relationDOS)) {
                    List<String> collect = relationDOS.stream()
                            .map(MemberUserPoolRelationDO::getCustomerId).collect(
                                    Collectors.toList());
                    customerIds = CollUtil.subtractToList(customerIds, collect);
                }
                if (CollUtil.isNotEmpty(customerIds)) {
                    queryDTO.setBizIds(customerIds);
                    KlmbPage<MemberUserDO> page = super.page(queryDTO, klmbPage);
                    return page;
                }
            } else {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            }
        }
        klmbPage.setContent(Collections.EMPTY_LIST);
        return klmbPage;
    }

    @Override
    public KlmbScrollPage<MemberUserDO> pageScroll(MemberUserScrollPageReqVO reqVO) {
        //获取当前用户id
        String userId = reqVO.getUserId();
        List<String> childUserIds = sysUserService.queryChildUserId(
                userId);
        KlmbScrollPage<MemberUserDO> klmbPage = KlmbScrollPage.<MemberUserDO>builder()
                .lastBizId(reqVO.getLastBizId())
                .pageSize(reqVO.getPageSize())
                .asc(reqVO.getAsc())
                .build();

        MemberUserQueryDTO queryDTO = MemberUserConvert.INSTANCE.convert(reqVO);
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            if (CollUtil.isEmpty(childUserIds)) {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            } else {
                queryDTO.setOwnerUserIds(childUserIds);
                KlmbScrollPage<MemberUserDO> page = super.pageScroll(queryDTO, klmbPage);
                return page;
            }
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            queryDTO.setOwnerUserId(userId);
            KlmbScrollPage<MemberUserDO> page = super.pageScroll(queryDTO, klmbPage);
            return page;
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = new ArrayList<>();
            //根据用户id集合查询用户id集合负责的客户信息
            List<MemberUserDO> userDOList = super.list(
                    new LambdaQueryWrapper<MemberUserDO>().in(MemberUserDO::getOwnerUserId,
                            childUserIds).eq(MemberUserDO::getDeleted, false));
            if (CollUtil.isNotEmpty(userDOList)) {
                bizIds = CollUtil.unionAll(userDOList.stream().map(MemberUserDO::getBizId)
                        .collect(Collectors.toList()), bizIds);
            }
            //根据当前用户查询团队成员表中客户id,根据客户id查询客户负责人id
            List<MemberTeamDO> memberTeamDOList = memberTeamService.list(
                    new LambdaQueryWrapper<MemberTeamDO>().eq(MemberTeamDO::getUserId, userId)
                            .eq(MemberTeamDO::getType, CrmEnum.CUSTOMER.getType())
                            .eq(MemberTeamDO::getDeleted, false));
            if (CollUtil.isNotEmpty(memberTeamDOList)) {
                bizIds = CollUtil.unionAll(memberTeamDOList.stream().map(MemberTeamDO::getTypeId)
                        .collect(Collectors.toList()), bizIds);
            }
            if (CollUtil.isNotEmpty(bizIds)) {
                //查询 公海中是否存在这些客户,如果存在剔除掉该客户
                List<MemberUserPoolRelationDO> relationDOS = relationService.list(
                        new LambdaQueryWrapper<MemberUserPoolRelationDO>().in(
                                        MemberUserPoolRelationDO::getCustomerId, bizIds)
                                .eq(MemberUserPoolRelationDO::getDeleted, false));
                if (CollUtil.isNotEmpty(relationDOS)) {
                    List<String> collect = relationDOS.stream()
                            .map(MemberUserPoolRelationDO::getCustomerId)
                            .collect(Collectors.toList());
                    bizIds = CollUtil.subtractToList(bizIds, collect);
                }
                if (CollUtil.isNotEmpty(bizIds)) {
                    queryDTO.setBizIds(bizIds);
                    return super.pageScroll(queryDTO, klmbPage);
                }
            } else {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            }
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<MemberUserStarDO> memberUserStarDOS = memberUserStarService.list(
                    new LambdaQueryWrapper<MemberUserStarDO>().eq(MemberUserStarDO::getUserId,
                            userId).eq(MemberUserStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(memberUserStarDOS)) {
                List<String> customerIds = memberUserStarDOS.stream()
                        .map(MemberUserStarDO::getCustomerId)
                        .collect(Collectors.toList());
                //查询 公海中是否存在这些客户,如果存在剔除掉该客户
                List<MemberUserPoolRelationDO> relationDOS = relationService.list(
                        new LambdaQueryWrapper<MemberUserPoolRelationDO>().in(
                                        MemberUserPoolRelationDO::getCustomerId, customerIds)
                                .eq(MemberUserPoolRelationDO::getDeleted, false));
                if (CollUtil.isNotEmpty(relationDOS)) {
                    List<String> collect = relationDOS.stream()
                            .map(MemberUserPoolRelationDO::getCustomerId).collect(
                                    Collectors.toList());
                    customerIds = CollUtil.subtractToList(customerIds, collect);
                }
                if (CollUtil.isNotEmpty(customerIds)) {
                    queryDTO.setBizIds(customerIds);
                    return super.pageScroll(queryDTO, klmbPage);
                }
            } else {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            }
        }
        klmbPage.setContent(Collections.EMPTY_LIST);
        return klmbPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDealStatus(Integer dealStatus, List<String> bizIds) {
        List<MemberUserDO> memberUserDOS = super.listByBizIds(bizIds);
        memberUserDOS.forEach(e -> {
            e.setDealStatus(dealStatus);
            if (ObjectUtil.equals(dealStatus, 1)) {
                e.setDealTime(LocalDateTime.now());
            }
        });
        super.updateBatchByBizId(memberUserDOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void star(String bizId) {
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        LambdaQueryWrapper<MemberUserStarDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberUserStarDO::getCustomerId, bizId);
        wrapper.eq(MemberUserStarDO::getUserId, userId);
        wrapper.eq(MemberUserStarDO::getDeleted, false);
        MemberUserStarDO star = memberUserStarService.getOne(wrapper);
        if (star == null) {
            star = new MemberUserStarDO();
            star.setCustomerId(bizId);
            star.setUserId(userId);
            memberUserStarService.save(star);
        } else {
            memberUserStarService.removeById(star.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveCustomer(MemberUserDO saveDO) {
        String bizId = "";
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        saveDO.setOwnerUserId(userId);
        saveDO.setDealStatus(0);
        if (super.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        memberTeamService.saveDO(
                MemberTeamDO.builder().power(3).userId(userId).type(CrmEnum.CUSTOMER.getType())
                        .typeId(bizId).build());

        SysConfigDO sysConfigDO = sysConfigService.getByConfigKey(
                SysConfigKeyEnum.CONTACTS_REMINDER.getType());

        changeTask(XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                .executorHandler("customerContactReminderHandler").author("liuyuepan")
                .ownerUserId(saveDO.getOwnerUserId())
                .bizId(bizId).nextTime(saveDO.getNextTime()).name(saveDO.getName())
                .operateType(1)
                .messageType(CrmEnum.CUSTOMER.getRemarks())
                .contactsType(CrmEnum.CUSTOMER.getType())
                .offsetValue(sysConfigDO.getValue()).build());
        return bizId;
    }

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTimeUtil.parse("2020-04-17T11:21:00");
        LocalDateTime end = LocalDateTimeUtil.parse("2020-04-17T13:00:00");

        Duration between = LocalDateTimeUtil.between(start, end);
        long l = between.toMinutes();
        System.out.println(l);
        System.out.println(between.toMillis());
        System.out.println(between.toDays());
        long a = 0L;
        LocalDateTime offset = LocalDateTimeUtil.offset(end, -a, ChronoUnit.DAYS);
        System.out.println(offset);
        System.out.println(end);
// 365
        between.toDays();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(bizIds);
        bizIds.forEach(e -> {
            changeTask(XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                    .executorHandler("customerContactReminderHandler").author("liuyuepan")
                    .bizId(e).operateType(3)
                    .messageType(CrmEnum.CUSTOMER.getRemarks())
                    .contactsType(CrmEnum.CUSTOMER.getType()).build());
        });

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDO(MemberUserDO entity) {
        MemberUserDO memberUserDO = super.getByBizId(entity.getBizId());
        LocalDateTime nextTime = memberUserDO.getNextTime();
        boolean success = super.updateDO(entity);
        if (!nextTime.isEqual(entity.getNextTime())
                && LocalDateTimeUtil.toEpochMilli(entity.getNextTime()) != 0) {
            SysConfigDO sysConfigDO = sysConfigService.getByConfigKey(
                    SysConfigKeyEnum.CONTACTS_REMINDER.getType());
            changeTask(XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                    .executorHandler("customerContactReminderHandler").author("liuyuepan")
                    .ownerUserId(entity.getOwnerUserId())
                    .bizId(entity.getBizId()).nextTime(entity.getNextTime())
                    .name(entity.getName()).operateType(2)
                    .messageType(CrmEnum.CUSTOMER.getRemarks())
                    .contactsType(CrmEnum.CUSTOMER.getType())
                    .offsetValue(sysConfigDO.getValue())
                    .build());
        }
        if (LocalDateTimeUtil.toEpochMilli(entity.getNextTime()) == 0) {
            changeTask(XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                    .executorHandler("customerContactReminderHandler").author("liuyuepan")
                    .bizId(entity.getBizId()).operateType(3)
                    .messageType(CrmEnum.CUSTOMER.getRemarks())
                    .contactsType(CrmEnum.CUSTOMER.getType()).build());
        }
        return success;
    }


	@Override
	public List<MemberUserDO> nearbyMember(String lng, String lat, Integer type, Integer radius,
		String ownerUserId) {
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isEmpty(ownerUserId)) {
            ownerUserId = userId;
        }
        List<String> childUserIds = sysUserService.queryChildUserId(
                userId);
        if (ObjectUtil.isNull(type)) {
            type = 1;
        }
        List<MemberUserDO> list = mapper
                .nearbyMember(lng, lat, type, radius, ownerUserId, childUserIds);
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(e -> {
                MemberContactsDO memberContactsDO = memberContactsService.getByBizId(
                        e.getContactsId());
                if (ObjectUtil.isNotNull(memberContactsDO)) {
                    e.setContactsName(memberContactsDO.getName());
                    e.setContactsMobile(memberContactsDO.getMobile());
                }
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                if (ObjectUtil.isNotNull(sysUserDO)) {
                    e.setOwnerUserName(sysUserDO.getNickname());
                }
                if (StrUtil.equals(e.getModel(), "公海") && StrUtil.isNotBlank(
                        e.getPreOwnerUserId())) {
                    SysUserDO userDO = sysUserService.getByBizId(e.getPreOwnerUserId());
                    e.setPreOwnerUserName(
                            ObjectUtil.isNotNull(userDO) ? userDO.getNickname() : null);
                }
            });
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPool(MemberUserPoolBO poolBO) {
        poolBO.setPoolId("0");
        if (poolBO.getCustomerIds().size() == 0) {
            return;
        }
        // String userId = WebFrameworkUtils.getLoginUserId();
        List<MemberOwnerRecordDO> ownerRecordList = new ArrayList<>();
        List<MemberUserPoolRelationDO> poolRelationList = new ArrayList<>();
        for (String bizId : poolBO.getCustomerIds()) {
            MemberUserDO memberUserDO = super.getByBizId(bizId);
            if (StrUtil.isBlank(memberUserDO.getOwnerUserId())) {
                continue;
            }

            MemberOwnerRecordDO memberOwnerRecordDO = new MemberOwnerRecordDO();
            memberOwnerRecordDO.setTypeId(bizId);
            memberOwnerRecordDO.setType(CrmEnum.CUSTOMER_POOL.getType());
            memberOwnerRecordDO.setPreOwnerUserId(memberUserDO.getOwnerUserId());
            ownerRecordList.add(memberOwnerRecordDO);
            lambdaUpdate().set(MemberUserDO::getOwnerUserId, null)
                    .set(MemberUserDO::getPreOwnerUserId, memberUserDO.getOwnerUserId())
                    .set(MemberUserDO::getPoolTime, LocalDateTime.now())
                    .set(MemberUserDO::getIsReceive, null)
                    .eq(MemberUserDO::getBizId, memberUserDO.getBizId()).update();
            MemberUserPoolRelationDO relation = new MemberUserPoolRelationDO();
            relation.setCustomerId(bizId);
            relation.setPoolId(poolBO.getPoolId());
            poolRelationList.add(relation);

            //同时判断客户是否存在定时任务，如果存在删除该定时任务
            removeXxlJobTask(bizId);

            //前负责人踢出团队
            MemberTeamSaveBO memberTeamSaveBO = new MemberTeamSaveBO();
            memberTeamSaveBO.setUserIds(
                    Collections.singletonList(memberUserDO.getOwnerUserId()));
            memberTeamSaveBO.setBizIds(Collections.singletonList(bizId));
            memberTeamService.deleteMember(CrmEnum.CUSTOMER, memberTeamSaveBO);

        }
        if (ownerRecordList.size() > 0) {
            memberOwnerRecordService.saveBatchDO(ownerRecordList);
        }
        if (poolRelationList.size() > 0) {
            relationService.saveBatchDO(poolRelationList);
        }
        LambdaUpdateWrapper<MemberContactsDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(MemberContactsDO::getOwnerUserId, null);
        wrapper.in(MemberContactsDO::getCustomerId, poolBO.getCustomerIds());
        memberContactsService.update(wrapper);


    }


    public void removeXxlJobTask(String bizId) {
        XxlJobTaskManagerInfo xxlJobTaskManagerInfo = xxlJobApiUtils.getTaskManagerInfo(
                XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                        .executorHandler("customerContactReminderHandler").author("liuyuepan")
                        .build());
        if (ObjectUtil.isNotNull(xxlJobTaskManagerInfo) && CollUtil.isNotEmpty(
                xxlJobTaskManagerInfo.getData())) {
            List<XxlJobInfo> data = xxlJobTaskManagerInfo.getData();
            for (XxlJobInfo datum : data) {
                List<String> split = StrUtil.split(datum.getExecutorParam(), CharUtil.COMMA);
                if (CollUtil.contains(split, bizId)) {
                    xxlJobApiUtils.deleteTask(datum.getId());
                }
            }
        }

    }


    /**
     * 领取或分配客户
     *
     * @param poolBO bo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getCustomersByIds(MemberUserPoolBO poolBO) {
        poolBO.setPoolId("0");
        if (poolBO.getCustomerIds().size() == 0) {
            return;
        }
        if (StrUtil.isBlank(poolBO.getUserId())) {
            poolBO.setUserId(WebFrameworkUtils.getLoginUserId());
        }
        List<MemberOwnerRecordDO> records = new ArrayList<>();
        for (String id : poolBO.getCustomerIds()) {
            MemberOwnerRecordDO memberOwnerRecordDO = new MemberOwnerRecordDO();
            memberOwnerRecordDO.setTypeId(id);
            memberOwnerRecordDO.setType(CrmEnum.CUSTOMER_POOL.getType());
            memberOwnerRecordDO.setPostOwnerUserId(poolBO.getUserId());
            memberOwnerRecordDO.setCreateTime(LocalDateTime.now());
            records.add(memberOwnerRecordDO);

            //为 领取人创建团队
            MemberTeamSaveBO memberTeamSaveBO = new MemberTeamSaveBO();
            memberTeamSaveBO.setPower(3);
            memberTeamSaveBO.setBizIds(Collections.singletonList(id));
            memberTeamSaveBO.setUserIds(Collections.singletonList(poolBO.getUserId()));
            memberTeamService.addMember(CrmEnum.CUSTOMER, memberTeamSaveBO);
        }
        if (records.size() > 0) {
            memberOwnerRecordService.saveBatchDO(records);
        }
        LambdaQueryWrapper<MemberUserPoolRelationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(MemberUserPoolRelationDO::getCustomerId, poolBO.getCustomerIds());
        relationService.remove(wrapper);
        memberContactsService.lambdaUpdate()
                .set(MemberContactsDO::getOwnerUserId, poolBO.getUserId())
                .in(MemberContactsDO::getCustomerId, poolBO.getCustomerIds()).update();

        super.update(new LambdaUpdateWrapper<MemberUserDO>().set(MemberUserDO::getOwnerUserId,
                        poolBO.getUserId())
                .set(MemberUserDO::getFollowup, 0)
                .set(MemberUserDO::getReceiveTime, LocalDateTime.now())
                .set(MemberUserDO::getUpdateTime, LocalDateTime.now())
                .set(MemberUserDO::getIsReceive, poolBO.getIsReceive())
                .in(MemberUserDO::getBizId, poolBO.getCustomerIds()));


    }

    @Override
    public void changeTask(XxlJobChangeTaskDTO xxlJobChangeTaskDTO) {
        xxlJobApiUtils.changeTask(
                xxlJobChangeTaskDTO
        );
    }
}
