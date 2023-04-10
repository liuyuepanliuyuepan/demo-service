package cn.klmb.crm.module.member.service.user;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.job.entity.XxlJobGroup;
import cn.klmb.crm.framework.job.entity.XxlJobInfo;
import cn.klmb.crm.framework.job.entity.XxlJobResponseInfo;
import cn.klmb.crm.framework.job.entity.XxlJobTaskManagerInfo;
import cn.klmb.crm.framework.job.util.CronUtil;
import cn.klmb.crm.framework.job.util.XxlJobApiUtils;
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
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    public MemberUserServiceImpl(SysUserService sysUserService, MemberUserMapper mapper,
            MemberUserStarService memberUserStarService, @Lazy MemberTeamService memberTeamService,
            XxlJobApiUtils xxlJobApiUtils) {
        this.sysUserService = sysUserService;
        this.memberUserStarService = memberUserStarService;
        this.memberTeamService = memberTeamService;
        this.xxlJobApiUtils = xxlJobApiUtils;
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
        changeTask(saveDO.getOwnerUserId(), bizId, saveDO.getNextTime(), saveDO.getName(), 1, "客户");
        return bizId;
    }


    @Override
    public boolean updateDO(MemberUserDO entity) {
        boolean success = super.updateDO(entity);
        MemberUserDO memberUserDO = super.getByBizId(entity.getBizId());
        LocalDateTime nextTime = memberUserDO.getNextTime();
        if (!nextTime.isEqual(entity.getNextTime())) {
            changeTask(entity.getOwnerUserId(), entity.getBizId(), entity.getNextTime(),
                    entity.getName(), 2, "客户");
        }
        return success;
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(bizIds);
        bizIds.forEach(e -> {
            changeTask(null, e, null, null, 3, "客户");
        });

    }


    /**
     * @param ownerUserId 负责人id
     * @param bizId       消息来源的主键id (比如：客户、联系人的主键id)
     * @param nextTime    下次联系时间
     * @param name        消息来源的名称 (比如：客户、联系人的名字)
     * @param operateType 1新增，2编辑，3删除
     * @param messageType 例如：客户、联系人
     */
    private void changeTask(String ownerUserId, String bizId, LocalDateTime nextTime, String name,
            Integer operateType, String messageType) {
        XxlJobGroup xxlJobGroup = new XxlJobGroup();
        xxlJobGroup.setAppname("xxl-job-executor-crm");
        xxlJobGroup.setTitle("crm执行器");
        List<XxlJobGroup> xxlJobGroups = xxlJobApiUtils.selectActuator(xxlJobGroup);
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroups.get(0).getId());
        xxlJobInfo.setExecutorHandler("customerContactReminderHandler");
        xxlJobInfo.setAuthor("liuyuepan");
        XxlJobTaskManagerInfo xxlJobTaskManagerInfo = xxlJobApiUtils.selectTask(xxlJobInfo);
        if (operateType != 3 && ObjectUtil.isNotNull(nextTime)
                && LocalDateTimeUtil.toEpochMilli(nextTime) != 0) {
            if (LocalDateTime.now().isAfter(nextTime)) {
                throw exception(
                        cn.klmb.crm.module.member.enums.ErrorCodeConstants.USER_NEXT_TIME_ERROR);
            }
            String nextTimeStr = nextTime
                    .format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
            xxlJobInfo.setJobDesc(
                    StrUtil.format("{}{}下次联系时间{}定时任务！", messageType, name, nextTimeStr));
            xxlJobInfo.setScheduleConf(CronUtil.onlyOnce(nextTimeStr));
            List<String> list = Arrays.asList(ownerUserId,
                    (CrmEnum.CUSTOMER.getType().toString()), bizId);
            xxlJobInfo.setExecutorParam(CollUtil.join(list, COMMA));
            if (operateType == 2 && ObjectUtil.isNotNull(xxlJobTaskManagerInfo)
                    && CollUtil.isNotEmpty(
                    xxlJobTaskManagerInfo.getData())) {
                List<XxlJobInfo> data = xxlJobTaskManagerInfo.getData();
                for (XxlJobInfo datum : data) {
                    String executorParam = datum.getExecutorParam();
                    List<String> split = StrUtil.split(executorParam, CharUtil.COMMA);
                    String s = split.get(2);
                    if (StrUtil.equals(bizId, s)) {
                        datum.setScheduleConf(xxlJobInfo.getScheduleConf());
                        datum.setJobDesc(xxlJobInfo.getJobDesc());
                        datum.setExecutorParam(xxlJobInfo.getExecutorParam());
                        xxlJobApiUtils.editTask(datum);
                        xxlJobApiUtils.startTask(datum.getId());
                    }
                }
            }

            if (operateType == 1) {
                XxlJobResponseInfo task = xxlJobApiUtils.createTask(xxlJobInfo);
                if (ObjectUtil.isNotNull(task) && StrUtil.isNotBlank(task.getContent())) {
                    xxlJobApiUtils.startTask(Long.parseLong(task.getContent()));
                }
            }
        }
        if (operateType == 3) {
            if (ObjectUtil.isNotNull(xxlJobTaskManagerInfo) && CollUtil.isNotEmpty(
                    xxlJobTaskManagerInfo.getData())) {
                List<XxlJobInfo> data = xxlJobTaskManagerInfo.getData();
                for (XxlJobInfo datum : data) {
                    String executorParam = datum.getExecutorParam();
                    List<String> split = StrUtil.split(executorParam, CharUtil.COMMA);
                    String s = split.get(2);
                    if (StrUtil.equals(bizId, s)) {
                        xxlJobApiUtils.deleteTask(datum.getId());
                    }
                }
            }
        }

    }
}
