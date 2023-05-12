package cn.klmb.crm.module.contract.service.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.pojo.SortingField;
import cn.klmb.crm.framework.job.dto.XxlJobChangeTaskDTO;
import cn.klmb.crm.framework.job.util.XxlJobApiUtils;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.business.entity.userstar.BusinessUserStarDO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractChangeOwnerUserVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailFullRespVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailPageReqVO;
import cn.klmb.crm.module.contract.convert.detail.ContractDetailConvert;
import cn.klmb.crm.module.contract.dao.detail.ContractDetailMapper;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import cn.klmb.crm.module.contract.enums.ContractErrorCodeConstants;
import cn.klmb.crm.module.contract.service.star.ContractStarService;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.system.entity.config.SysConfigDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.enums.config.SysConfigKeyEnum;
import cn.klmb.crm.module.system.service.config.SysConfigService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 合同详情 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ContractDetailServiceImpl extends
        KlmbBaseServiceImpl<ContractDetailDO, ContractDetailQueryDTO, ContractDetailMapper> implements
        ContractDetailService {

    private final XxlJobApiUtils xxlJobApiUtils;
    private final SysUserService sysUserService;
    private final ContractStarService contractStarService;
    private final SysConfigService sysConfigService;
    private final MemberTeamService memberTeamService;

    public ContractDetailServiceImpl(XxlJobApiUtils xxlJobApiUtils,
            @Lazy SysUserService sysUserService,
            ContractDetailMapper mapper, @Lazy ContractStarService contractStarService,
            SysConfigService sysConfigService, @Lazy MemberTeamService memberTeamService) {
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.sysUserService = sysUserService;
        this.contractStarService = contractStarService;
        this.sysConfigService = sysConfigService;
        this.memberTeamService = memberTeamService;
        this.mapper = mapper;
    }


    @Override
    public ContractDetailDO saveDefinition(ContractDetailDO saveDO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        if (saveDO.getStartTime() != null && saveDO.getEndTime() != null
                && saveDO.getStartTime().compareTo(saveDO.getEndTime()) > 0) {
            throw exception(ContractErrorCodeConstants.CONTRACT_DATE_ERROR);
        }
        saveDO.setOwnerUserId(userId);
        saveDO.setStatus(1);
        saveDO.setLastTime(saveDO.getEndTime());
        // todo 合同编号的规律
        saveDO.setNum("");
        // todo 操作记录记录
        // todo 合同活动
        super.saveDO(saveDO);

        // 获取合同的系统参数，到期提示时间的获取
        SysConfigDO sysConfigDO = sysConfigService.getByConfigKey(
                SysConfigKeyEnum.CONTRACT_REMINDER.getType());
        // 发送合同到期提醒
        xxlJobApiUtils.changeTask(
                XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                        .executorHandler("customerContactReminderHandler").author("liuyuepan")
                        .ownerUserId(saveDO.getOwnerUserId())
                        .bizId(saveDO.getBizId()).nextTime(saveDO.getEndTime())
                        .name(saveDO.getName())
                        .operateType(1)
                        .offsetValue(sysConfigDO.getValue())
                        .messageType(CrmEnum.CUSTOMER.getRemarks())
                        .contactsType(CrmEnum.CONTRACT.getType()).build());
        return saveDO;
    }

    @Override
    public KlmbPage<ContractDetailDO> pageDefinition(ContractDetailPageReqVO reqVO) {
//        //获取当前用户id
//        String userId = reqVO.getUserId();
//        List<String> childUserIds = sysUserService.queryChildUserId(
//                userId);
//        KlmbPage<ContractDetailDO> klmbPage = KlmbPage.<ContractDetailDO>builder()
//                .pageNo(reqVO.getPageNo())
//                .pageSize(reqVO.getPageSize())
//                .sortingFields(reqVO.getSortingFields())
//                .build();
//
//        ContractDetailQueryDTO queryDTO = ContractDetailConvert.INSTANCE.convert(reqVO);
//        queryDTO.setMemberUserIds(reqVO.getMemberUserIds());
//        // 下属负责的
//        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
//            if (CollUtil.isEmpty(childUserIds)) {
//                klmbPage.setContent(Collections.EMPTY_LIST);
//                return klmbPage;
//            } else {
//                queryDTO.setOwnerUserIds(childUserIds);
//                return page(queryDTO, klmbPage);
//            }
//        }
//        // 自己负责的
//        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
//            queryDTO.setOwnerUserId(userId);
//            return page(queryDTO, klmbPage);
//        }
//        //全部
//        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
//            childUserIds.add(userId);
//            queryDTO.setOwnerUserIds(childUserIds);
//            return page(queryDTO, klmbPage);
//        }
//        // 关注的
//        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
//            List<ContractStarDO> contractStarDOList = contractStarService.list(
//                    new LambdaQueryWrapper<ContractStarDO>().eq(ContractStarDO::getUserId,
//                            userId).eq(ContractStarDO::getDeleted, false));
//            if (CollUtil.isNotEmpty(contractStarDOList)) {
//                List<String> contractIds = contractStarDOList.stream()
//                        .map(ContractStarDO::getContractId)
//                        .collect(Collectors.toList());
//                //查询 公海中是否存在这些客户,如果存在剔除掉该客户
//                if (CollUtil.isNotEmpty(contractIds)) {
//                    queryDTO.setBizIds(contractIds);
//                    return page(queryDTO, klmbPage);
//                }
//            } else {
//                klmbPage.setContent(Collections.EMPTY_LIST);
//                return klmbPage;
//            }
//        }
//        klmbPage.setContent(Collections.EMPTY_LIST);
        return null;
    }

    @Override
    public KlmbPage<ContractDetailDO> page(ContractDetailQueryDTO queryDTO,
            KlmbPage<ContractDetailDO> klmbPage) {
        //什么几把破代码啊，你自测过了吗
        LambdaQueryWrapper<ContractDetailDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ContractDetailDO::getMemberUserId, queryDTO.getMemberUserIds())
                .or().like(ContractDetailDO::getName, queryDTO.getKeyword()).or()
                .like(ContractDetailDO::getNum, queryDTO.getKeyword());
        wrapper.in(ContractDetailDO::getOwnerUserId, queryDTO.getOwnerUserIds());
        // 创建分页
        PageDTO<ContractDetailDO> page = new PageDTO<>(klmbPage.getPageNo(),
                klmbPage.getPageSize());
        // 排序字段
        if (!CollectionUtil.isEmpty(klmbPage.getSortingFields())) {
            page.addOrder(klmbPage.getSortingFields().stream()
                    .map(sortingField -> SortingField.ORDER_ASC.equals(sortingField.getOrder())
                            ? OrderItem.asc(sortingField.getField())
                            : OrderItem.desc(sortingField.getField()))
                    .collect(Collectors.toList()));
        } else {
            page.addOrder(OrderItem.desc("create_time"));
        }
        // 创建查询条件
        PageDTO<ContractDetailDO> pageResult = super.page(page, wrapper);

        return KlmbPage.<ContractDetailDO>builder().pageNo((int) pageResult.getCurrent())
                .pageSize((int) pageResult.getSize()).content(pageResult.getRecords())
                .totalPages((int) pageResult.getPages()).totalElements(pageResult.getTotal())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeOwnerUser(ContractChangeOwnerUserVO crmChangeOwnerUserVO) {
        // 修改负责人
        if (crmChangeOwnerUserVO.getBizIds().size() == 0) {
            return;
        }
        crmChangeOwnerUserVO.getBizIds().forEach(e -> {
            ContractDetailDO contract = super.getByBizId(e);
            // 旧的负责人
            String oldOwnerUserId = contract.getOwnerUserId();

            if (contract.getStatus() == 8) {
                throw exception(ContractErrorCodeConstants.CONTRACT_TRANSFER_ERROR);
            }
            SysConfigDO sysConfigDO = sysConfigService.getByConfigKey(
                    SysConfigKeyEnum.CONTRACT_REMINDER.getType());
            // 删除之前的定时任务
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .bizId(e).operateType(3)
                            .messageType(CrmEnum.CONTRACT.getRemarks())
                            .contactsType(CrmEnum.CONTRACT.getType()).build());

            contract.setOwnerUserId(crmChangeOwnerUserVO.getOwnerUserId());
            // todo 操作记录
            super.updateDO(contract);
            // 新增定时任务
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .ownerUserId(contract.getOwnerUserId())
                            .bizId(contract.getBizId()).nextTime(contract.getEndTime())
                            .name(contract.getName())
                            .operateType(1)
                            .messageType(CrmEnum.CUSTOMER.getRemarks())
                            .offsetValue(sysConfigDO.getValue())
                            .contactsType(CrmEnum.CONTRACT.getType()).build());
            // todo 团队成员的增加和删除
            LambdaQueryWrapper<MemberTeamDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(MemberTeamDO::getTypeId, e);
            wrapper.eq(MemberTeamDO::getType, CrmEnum.CONTRACT.getType());
            wrapper.eq(MemberTeamDO::getUserId, oldOwnerUserId);
            wrapper.eq(MemberTeamDO::getDeleted, false);
            MemberTeamDO old = memberTeamService.getOne(wrapper);
            if (2 == crmChangeOwnerUserVO.getTransferType()) {
                // 当前合同 增加该成员未团队
                if (ObjectUtil.isNotNull(old)) {
                    old.setPower(crmChangeOwnerUserVO.getPower());
                }
                memberTeamService.updateDO(old);
            } else {
                // 移出成员团队
                memberTeamService.removeById(old);
            }
            // 保存新的 先查存在不存在
            LambdaQueryWrapper<MemberTeamDO> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(MemberTeamDO::getTypeId, e);
            wrapper1.eq(MemberTeamDO::getType, CrmEnum.CONTRACT.getType());
            wrapper1.eq(MemberTeamDO::getUserId, crmChangeOwnerUserVO.getOwnerUserId());
            wrapper1.eq(MemberTeamDO::getDeleted, false);
            MemberTeamDO existOwn = memberTeamService.getOne(wrapper);
            if (ObjectUtil.isNotNull(existOwn)) {
                existOwn.setPower(3);
                memberTeamService.updateDO(existOwn);
            } else {
                MemberTeamDO memberTeamDO = MemberTeamDO.builder()
                        .power(3).type(CrmEnum.CONTRACT.getType()).typeId(e)
                        .userId(crmChangeOwnerUserVO.getOwnerUserId())
                        .build();
                memberTeamService.saveDO(memberTeamDO);
            }
        });
    }

    @Override
    public ContractDetailFullRespVO page(ContractDetailPageReqVO reqVO) {
        Integer sceneId = reqVO.getSceneId();
        String keyword = reqVO.getKeyword();
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        List<String> childUserIds = sysUserService.queryChildUserId(
                userId);
        ContractDetailQueryDTO queryDTO = new ContractDetailQueryDTO();

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLContract(childUserIds, userId);
            queryDTO.setBizIds(bizIds);
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            queryDTO.setOwnerUserId(userId);

        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            queryDTO.setOwnerUserIds(childUserIds);
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
//            List<BusinessUserStarDO> businessUserStarDOS = businessUserStarService.list(
//                    new LambdaQueryWrapper<BusinessUserStarDO>().eq(BusinessUserStarDO::getUserId,
//                            userId).eq(BusinessUserStarDO::getDeleted, false));
//            if (CollUtil.isNotEmpty(businessUserStarDOS)) {
//                List<String> collect = businessUserStarDOS.stream()
//                        .map(BusinessUserStarDO::getBusinessId).collect(
//                                Collectors.toList());
//                queryDTO.setBizIds(collect);
//                klmbPage = super.page(queryDTO, klmbPage);
//                //查询符合条件的商机总金额
//                businessDetailDOS = super.list(queryDTO);
            }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(bizIds);
        bizIds.forEach(e -> {
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .bizId(e).operateType(3)
                            .messageType(CrmEnum.CONTRACT.getRemarks())
                            .contactsType(CrmEnum.CONTRACT.getType()).build());
        });

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDO(ContractDetailDO entity) {
        ContractDetailDO contractDetailDO = super.getByBizId(entity.getBizId());
        LocalDateTime endTime = contractDetailDO.getEndTime();
        // todo 对状态有一些判断  已通过的合同 作废后才能编辑
        boolean success = super.updateDO(entity);
        if (!endTime.isEqual(entity.getEndTime())) {
            SysConfigDO sysConfigDO = sysConfigService.getByConfigKey(
                    SysConfigKeyEnum.CONTRACT_REMINDER.getType());
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .ownerUserId(entity.getOwnerUserId())
                            .bizId(entity.getBizId()).nextTime(entity.getEndTime())
                            .name(entity.getName()).operateType(2)
                            .offsetValue(sysConfigDO.getValue())
                            .messageType(CrmEnum.CONTRACT.getRemarks())
                            .contactsType(CrmEnum.CONTRACT.getType()).build());
        }
        return success;
    }

    /**
     * 获取当前用户下的全部合同的bizId
     *
     * @param childUserIds
     * @param userId
     * @return
     */
    private List<String> getALLContract(List<String> childUserIds, String userId) {
        List<String> bizIds = new ArrayList<>();
        //根据用户集合查询该用户们负责的商机
        List<ContractDetailDO> contractDetailList = super.list(
                new LambdaQueryWrapper<ContractDetailDO>().in(ContractDetailDO::getOwnerUserId,
                        childUserIds).eq(ContractDetailDO::getDeleted, false));

        if (CollUtil.isNotEmpty(contractDetailList)) {
            bizIds = CollUtil.unionAll(
                    contractDetailList.stream().map(ContractDetailDO::getBizId)
                            .collect(Collectors.toList()), bizIds);
        }
        //根据当前用户查询团队成员表中商机id,根据商机id查询客户负责人id
        List<MemberTeamDO> memberTeamDOList = memberTeamService.list(
                new LambdaQueryWrapper<MemberTeamDO>().eq(MemberTeamDO::getUserId, userId)
                        .eq(MemberTeamDO::getType, CrmEnum.CONTRACT.getType())
                        .eq(MemberTeamDO::getDeleted, false));
        if (CollUtil.isNotEmpty(memberTeamDOList)) {
            bizIds = CollUtil.unionAll(memberTeamDOList.stream().map(MemberTeamDO::getTypeId)
                    .collect(Collectors.toList()), bizIds);
        }
        return bizIds;
    }


}
