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
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailPageReqVO;
import cn.klmb.crm.module.contract.convert.detail.ContractDetailConvert;
import cn.klmb.crm.module.contract.dao.detail.ContractDetailMapper;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import cn.klmb.crm.module.contract.service.star.ContractStarService;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.entity.userpoolrelation.MemberUserPoolRelationDO;
import cn.klmb.crm.module.member.entity.userstar.MemberUserStarDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


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
    public ContractDetailServiceImpl(XxlJobApiUtils xxlJobApiUtils, SysUserService sysUserService,
            ContractDetailMapper mapper, ContractStarService contractStarService) {
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.sysUserService = sysUserService;
        this.contractStarService = contractStarService;
        this.mapper = mapper;
    }


    @Override
    public String saveDefinition(ContractDetailDO saveDO) {
        String bizId = "";
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        saveDO.setOwnerUserId(userId);
        saveDO.setStatus(1);
        // todo 合同编号的规律
        saveDO.setNum("");
        if (super.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }

        // 发送合同到期提醒
        xxlJobApiUtils.changeTask(
                XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                        .executorHandler("customerContactReminderHandler").author(saveDO.getContactsId())
                        .ownerUserId(saveDO.getOwnerUserId())
                        .bizId(bizId).nextTime(saveDO.getEndTime()).name(saveDO.getName())
                        .operateType(1)
                        .messageType(CrmEnum.CUSTOMER.getRemarks())
                        .contactsType(CrmEnum.CONTRACT.getType()).build());
        return bizId;
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


}
