package cn.klmb.crm.module.contract.service.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.job.dto.XxlJobChangeTaskDTO;
import cn.klmb.crm.framework.job.util.XxlJobApiUtils;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractChangeOwnerUserVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailPageReqVO;
import cn.klmb.crm.module.contract.convert.detail.ContractDetailConvert;
import cn.klmb.crm.module.contract.dao.detail.ContractDetailMapper;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import cn.klmb.crm.module.contract.enums.ContractErrorCodeConstants;
import cn.klmb.crm.module.contract.service.star.ContractStarService;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.entity.userpoolrelation.MemberUserPoolRelationDO;
import cn.klmb.crm.module.member.entity.userstar.MemberUserStarDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDateTime;
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
    public ContractDetailServiceImpl(XxlJobApiUtils xxlJobApiUtils, @Lazy SysUserService sysUserService,
            ContractDetailMapper mapper, @Lazy ContractStarService contractStarService) {
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.sysUserService = sysUserService;
        this.contractStarService = contractStarService;
        this.mapper = mapper;
    }


    @Override
    public ContractDetailDO saveDefinition(ContractDetailDO saveDO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        saveDO.setOwnerUserId(userId);
        saveDO.setStatus(1);
        // todo 合同编号的规律
        saveDO.setNum("");

        // todo 操作记录记录
        super.saveDO(saveDO);

        // 发送合同到期提醒
        xxlJobApiUtils.changeTask(
                XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                        .executorHandler("contractEndReminderHandler").author("zzp")
                        .ownerUserId(saveDO.getOwnerUserId())
                        .bizId(saveDO.getBizId()).nextTime(saveDO.getEndTime()).name(saveDO.getName())
                        .operateType(1)
                        .messageType(CrmEnum.CUSTOMER.getRemarks())
                        .contactsType(CrmEnum.CONTRACT.getType()).build());
        return saveDO;
    }

    @Override
    public KlmbPage<ContractDetailDO> pageDefinition(ContractDetailPageReqVO reqVO) {
        //获取当前用户id
        String userId = reqVO.getUserId();
        List<String> childUserIds = sysUserService.queryChildUserId(
                userId);
        KlmbPage<ContractDetailDO> klmbPage = KlmbPage.<ContractDetailDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .sortingFields(reqVO.getSortingFields())
                .build();

        ContractDetailQueryDTO queryDTO = ContractDetailConvert.INSTANCE.convert(reqVO);
        // 下属负责的
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            if (CollUtil.isEmpty(childUserIds)) {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            } else {
                queryDTO.setOwnerUserIds(childUserIds);
                KlmbPage<ContractDetailDO> page = super.page(queryDTO, klmbPage);
                return page;
            }
        }
        // 自己负责的
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            queryDTO.setOwnerUserId(userId);
            KlmbPage<ContractDetailDO> page = super.page(queryDTO, klmbPage);
            return page;
        }
        //全部
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            childUserIds.add(userId);
            queryDTO.setOwnerUserIds(childUserIds);
            KlmbPage<ContractDetailDO> page = super.page(queryDTO, klmbPage);
            return page;
        }
        // 关注的0
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<ContractStarDO> contractStarDOList = contractStarService.list(
                    new LambdaQueryWrapper<ContractStarDO>().eq(ContractStarDO::getUserId,
                            userId).eq(ContractStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(contractStarDOList)) {
                List<String> contractIds = contractStarDOList.stream()
                        .map(ContractStarDO::getContractId)
                        .collect(Collectors.toList());
                //查询 公海中是否存在这些客户,如果存在剔除掉该客户
                if (CollUtil.isNotEmpty(contractIds)) {
                    queryDTO.setBizIds(contractIds);
                    KlmbPage<ContractDetailDO> page = super.page(queryDTO, klmbPage);
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
    @Transactional(rollbackFor = Exception.class)
    public void changeOwnerUser(ContractChangeOwnerUserVO crmChangeOwnerUserVO) {
        // 修改负责人
        if (crmChangeOwnerUserVO.getBizIds().size() == 0) {
            return;
        }
        crmChangeOwnerUserVO.getBizIds().forEach(e->{
            ContractDetailDO contract = super.getByBizId(e);

            if (contract.getCheckStatus() == 8) {
                throw exception(ContractErrorCodeConstants.CONTRACT_TRANSFER_ERROR);
            }
            // 删除之前的定时任务
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("contractEndReminderHandler").author("zhouzhipeng")
                            .bizId(e).operateType(3)
                            .messageType(CrmEnum.CONTRACT.getRemarks())
                            .contactsType(CrmEnum.CONTRACT.getType()).build());

            contract.setOwnerUserId(crmChangeOwnerUserVO.getOwnerUserId());
            // todo 操作记录
            super.updateDO(contract);
            // 新增定时任务
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("contractEndReminderHandler").author("zhouzhipeng")
                            .ownerUserId(contract.getOwnerUserId())
                            .bizId(contract.getBizId()).nextTime(contract.getEndTime()).name(contract.getName())
                            .operateType(1)
                            .messageType(CrmEnum.CUSTOMER.getRemarks())
                            .contactsType(CrmEnum.CONTRACT.getType()).build());
            // 团队成员的增加和删除
            if (2 == crmChangeOwnerUserVO.getTransferType() ) {
                // 当前合同 增加该成员未团队
            } else {
                // 移出成员团队
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(bizIds);
        bizIds.forEach(e -> {
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("contractEndReminderHandler").author("zhouzhipeng")
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
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("contractEndReminderHandler").author("zhouzhipeng")
                            .ownerUserId(entity.getOwnerUserId())
                            .bizId(entity.getBizId()).nextTime(entity.getEndTime())
                            .name(entity.getName()).operateType(2)
                            .messageType(CrmEnum.CONTRACT.getRemarks())
                            .contactsType(CrmEnum.CONTRACT.getType()).build());
        }
        return success;
    }
}
