package cn.klmb.crm.module.member.service.user;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.job.dto.XxlJobChangeTaskDTO;
import cn.klmb.crm.framework.job.util.XxlJobApiUtils;
import cn.klmb.crm.framework.mq.message.WebSocketServer;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.klmb.crm.module.member.convert.user.MemberUserConvert;
import cn.klmb.crm.module.member.dao.user.MemberUserMapper;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.entity.userstar.MemberUserStarDO;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.member.service.userstar.MemberUserStarService;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.config.SysConfigService;
import cn.klmb.crm.module.system.service.notify.SysNotifyMessageService;
import cn.klmb.crm.module.system.service.notify.SysNotifySendService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * 客户-用户 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberUserServiceImpl extends
        KlmbBaseServiceImpl<MemberUserDO, MemberUserQueryDTO, MemberUserMapper> implements
        MemberUserService {

    private final SysUserService sysUserService;

    private final MemberUserStarService memberUserStarService;


    private final MemberTeamService memberTeamService;

    private final XxlJobApiUtils xxlJobApiUtils;

    private static final String COMMA = ",";

    private final SysConfigService sysConfigService;


    private final SysNotifySendService sysNotifySendService;

    private final SysNotifyMessageService sysNotifyMessageService;


    private final WebSocketServer webSocketServer;


    public MemberUserServiceImpl(SysUserService sysUserService, MemberUserMapper mapper,
            MemberUserStarService memberUserStarService, @Lazy MemberTeamService memberTeamService,
            XxlJobApiUtils xxlJobApiUtils, SysConfigService sysConfigService,
            SysNotifySendService sysNotifySendService,
            SysNotifyMessageService sysNotifyMessageService, WebSocketServer webSocketServer) {
        this.sysUserService = sysUserService;
        this.memberUserStarService = memberUserStarService;
        this.memberTeamService = memberTeamService;
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.sysConfigService = sysConfigService;
        this.sysNotifySendService = sysNotifySendService;
        this.sysNotifyMessageService = sysNotifyMessageService;
        this.webSocketServer = webSocketServer;
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
            queryDTO.setOwnerUserIds(childUserIds);
            KlmbPage<MemberUserDO> page = super.page(queryDTO, klmbPage);
            return page;
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<MemberUserStarDO> memberUserStarDOS = memberUserStarService.list(
                    new LambdaQueryWrapper<MemberUserStarDO>().eq(MemberUserStarDO::getUserId,
                            userId).eq(MemberUserStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(memberUserStarDOS)) {
                List<String> customerIds = memberUserStarDOS.stream()
                        .map(MemberUserStarDO::getCustomerId)
                        .collect(Collectors.toList());
                queryDTO.setBizIds(customerIds);
                KlmbPage<MemberUserDO> page = super.page(queryDTO, klmbPage);
                return page;
            } else {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            }
        }
        klmbPage.setContent(Collections.EMPTY_LIST);
        return klmbPage;
    }

    @Override
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

        xxlJobApiUtils.changeTask(
                XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                        .executorHandler("customerContactReminderHandler").author("liuyuepan")
                        .ownerUserId(saveDO.getOwnerUserId())
                        .bizId(bizId).nextTime(saveDO.getNextTime()).name(saveDO.getName())
                        .operateType(1)
                        .messageType(CrmEnum.CUSTOMER.getRemarks())
                        .contactsType(CrmEnum.CUSTOMER.getType()).build());
        return bizId;
    }

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTimeUtil.parse("2020-02-02T01:00:00");
        LocalDateTime end = LocalDateTimeUtil.parse("2020-03-01T00:30:00");

        Duration between = LocalDateTimeUtil.between(start, end);
        long l = between.toHours();
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
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(bizIds);
        bizIds.forEach(e -> {
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .bizId(e).operateType(3)
                            .messageType(CrmEnum.CUSTOMER.getRemarks())
                            .contactsType(CrmEnum.CUSTOMER.getType()).build());
        });

    }

    @Override
    public boolean updateDO(MemberUserDO entity) {
        MemberUserDO memberUserDO = super.getByBizId(entity.getBizId());
        LocalDateTime nextTime = memberUserDO.getNextTime();
        boolean success = super.updateDO(entity);
        if (!nextTime.isEqual(entity.getNextTime())) {
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .ownerUserId(entity.getOwnerUserId())
                            .bizId(entity.getBizId()).nextTime(entity.getNextTime())
                            .name(entity.getName()).operateType(2)
                            .messageType(CrmEnum.CUSTOMER.getRemarks())
                            .contactsType(CrmEnum.CUSTOMER.getType()).build());
        }
        return success;
    }
}
