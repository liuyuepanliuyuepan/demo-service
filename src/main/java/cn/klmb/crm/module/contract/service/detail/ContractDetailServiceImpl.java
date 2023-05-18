package cn.klmb.crm.module.contract.service.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.job.dto.XxlJobChangeTaskDTO;
import cn.klmb.crm.framework.job.util.XxlJobApiUtils;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.service.detail.BusinessDetailService;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailFullRespVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailPageReqVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailRespVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailScrollPageReqVO;
import cn.klmb.crm.module.contract.convert.detail.ContractDetailConvert;
import cn.klmb.crm.module.contract.dao.detail.ContractDetailMapper;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import cn.klmb.crm.module.contract.enums.ContractErrorCodeConstants;
import cn.klmb.crm.module.contract.service.product.ContractProductService;
import cn.klmb.crm.module.contract.service.star.ContractStarService;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.user.vo.CrmChangeOwnerUserBO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.entity.config.SysConfigDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.enums.config.SysConfigKeyEnum;
import cn.klmb.crm.module.system.service.config.SysConfigService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    private final ContractProductService contractProductService;

    private final MemberUserService memberUserService;

    private final BusinessDetailService businessDetailService;

    private final MemberContactsService memberContactsService;

    public ContractDetailServiceImpl(XxlJobApiUtils xxlJobApiUtils,
            @Lazy SysUserService sysUserService, ContractDetailMapper mapper,
            @Lazy ContractStarService contractStarService, SysConfigService sysConfigService,
            @Lazy MemberTeamService memberTeamService,
            ContractProductService contractProductService, MemberUserService memberUserService,
            BusinessDetailService businessDetailService,
            MemberContactsService memberContactsService) {
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.sysUserService = sysUserService;
        this.contractStarService = contractStarService;
        this.sysConfigService = sysConfigService;
        this.memberTeamService = memberTeamService;
        this.contractProductService = contractProductService;
        this.memberUserService = memberUserService;
        this.businessDetailService = businessDetailService;
        this.memberContactsService = memberContactsService;
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
        // todo 合同编号的规律
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
                        .messageType(CrmEnum.CONTRACT.getRemarks())
                        .contactsType(CrmEnum.CONTRACT.getType()).build());
        return saveDO;
    }

    @Override
    public void changeOwnerUser(CrmChangeOwnerUserBO changOwnerUserBO) {
        //逻辑分为两步 1. 变更负责人  2. 将原负责人移出 或者转为团队成员 3.同时变更定时任务
        List<String> bizIds = changOwnerUserBO.getBizIds();
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        bizIds.forEach(bizId -> {
            ContractDetailDO contractDetailDO = super.getByBizId(bizId);
            String oldOwnerUserId = contractDetailDO.getOwnerUserId();
            if (!StrUtil.equals(oldOwnerUserId, changOwnerUserBO.getOwnerUserId())) {
                //添加新负责人
                memberTeamService.addSingleMember(CrmEnum.CONTRACT.getType(), bizId,
                        changOwnerUserBO.getOwnerUserId(), 3,
                        null);
                if (Objects.equals(2, changOwnerUserBO.getTransferType()) && !StrUtil.equals(
                        oldOwnerUserId, changOwnerUserBO.getOwnerUserId())) {
                    memberTeamService.addSingleMember(CrmEnum.CONTRACT.getType(), bizId,
                            oldOwnerUserId, changOwnerUserBO.getPower(),
                            changOwnerUserBO.getExpiresTime());
                }
                contractDetailDO.setOwnerUserId(changOwnerUserBO.getOwnerUserId());
                super.updateDO(contractDetailDO);

                if (Objects.equals(1, changOwnerUserBO.getTransferType())) {
                    MemberTeamSaveBO memberTeamSaveBO = new MemberTeamSaveBO();
                    memberTeamSaveBO.setUserIds(
                            Collections.singletonList(oldOwnerUserId));
                    memberTeamSaveBO.setBizIds(Collections.singletonList(bizId));
                    memberTeamSaveBO.setType(CrmEnum.CONTRACT.getType());
                    memberTeamService.deleteMember(memberTeamSaveBO);
                }

                //更新定时任务
                xxlJobApiUtils.changeTaskOwnerUser(
                        XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm")
                                .title("crm执行器")
                                .executorHandler("customerContactReminderHandler")
                                .author("liuyuepan")
                                .ownerUserId(changOwnerUserBO.getOwnerUserId())
                                .bizId(bizId)
                                .contactsType(CrmEnum.CONTRACT.getType()).build());

            }

        });


    }

    @Override
    public ContractDetailFullRespVO page(ContractDetailPageReqVO reqVO) {
        ContractDetailFullRespVO fullRespVO = new ContractDetailFullRespVO();
        BigDecimal contractMoney = new BigDecimal("0.00");
        KlmbPage<ContractDetailRespVO> klmbPage = KlmbPage.<ContractDetailRespVO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        klmbPage.setContent(Collections.emptyList());
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
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setKeyword(reqVO.getKeyword());
                PageDTO<ContractDetailRespVO> pageResult = mapper.list(queryDTO,
                        new PageDTO<>(klmbPage.getPageNo(), klmbPage.getPageSize()));
                klmbPage = KlmbPage.<ContractDetailRespVO>builder()
                        .pageNo((int) pageResult.getCurrent())
                        .pageSize((int) pageResult.getSize())
                        .content(pageResult.getRecords())
                        .totalPages((int) pageResult.getPages())
                        .totalElements(pageResult.getTotal())
                        .build();
                List<ContractDetailRespVO> contractDetailRespVOS = mapper.listV1(queryDTO);
                if (CollUtil.isNotEmpty(contractDetailRespVOS)) {
                    contractMoney = contractDetailRespVOS.stream()
                            .map(ContractDetailRespVO::getMoney)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                }
            }
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            queryDTO.setOwnerUserIds(Arrays.asList(userId));
            queryDTO.setKeyword(reqVO.getKeyword());
            PageDTO<ContractDetailRespVO> pageResult = mapper.list(queryDTO,
                    new PageDTO<>(klmbPage.getPageNo(), klmbPage.getPageSize()));
            klmbPage = KlmbPage.<ContractDetailRespVO>builder()
                    .pageNo((int) pageResult.getCurrent())
                    .pageSize((int) pageResult.getSize())
                    .content(pageResult.getRecords())
                    .totalPages((int) pageResult.getPages())
                    .totalElements(pageResult.getTotal())
                    .build();
            List<ContractDetailRespVO> contractDetailRespVOS = mapper.listV1(queryDTO);
            if (CollUtil.isNotEmpty(contractDetailRespVOS)) {
                contractMoney = contractDetailRespVOS.stream()
                        .map(ContractDetailRespVO::getMoney)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            queryDTO.setOwnerUserIds(childUserIds);
            queryDTO.setKeyword(reqVO.getKeyword());
            if (CollUtil.isNotEmpty(queryDTO.getOwnerUserIds())) {
                PageDTO<ContractDetailRespVO> pageResult = mapper.list(queryDTO,
                        new PageDTO<>(klmbPage.getPageNo(), klmbPage.getPageSize()));
                klmbPage = KlmbPage.<ContractDetailRespVO>builder()
                        .pageNo((int) pageResult.getCurrent())
                        .pageSize((int) pageResult.getSize())
                        .content(pageResult.getRecords())
                        .totalPages((int) pageResult.getPages())
                        .totalElements(pageResult.getTotal())
                        .build();
                List<ContractDetailRespVO> contractDetailRespVOS = mapper.listV1(queryDTO);
                if (CollUtil.isNotEmpty(contractDetailRespVOS)) {
                    contractMoney = contractDetailRespVOS.stream()
                            .map(ContractDetailRespVO::getMoney)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }
            }

        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<ContractStarDO> contractStarDOS = contractStarService.list(
                    new LambdaQueryWrapper<ContractStarDO>().eq(ContractStarDO::getUserId,
                            userId).eq(ContractStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(contractStarDOS)) {
                List<String> collect = contractStarDOS.stream()
                        .map(ContractStarDO::getContractId).collect(
                                Collectors.toList());
                queryDTO.setBizIds(collect);
                queryDTO.setKeyword(reqVO.getKeyword());
                PageDTO<ContractDetailRespVO> pageResult = mapper.list(queryDTO,
                        new PageDTO<>(klmbPage.getPageNo(), klmbPage.getPageSize()));
                klmbPage = KlmbPage.<ContractDetailRespVO>builder()
                        .pageNo((int) pageResult.getCurrent())
                        .pageSize((int) pageResult.getSize())
                        .content(pageResult.getRecords())
                        .totalPages((int) pageResult.getPages())
                        .totalElements(pageResult.getTotal())
                        .build();
                List<ContractDetailRespVO> contractDetailRespVOS = mapper.listV1(queryDTO);
                if (CollUtil.isNotEmpty(contractDetailRespVOS)) {
                    contractMoney = contractDetailRespVOS.stream()
                            .map(ContractDetailRespVO::getMoney)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                }
            }
        }
        List<ContractDetailRespVO> content = klmbPage.getContent();
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                List<ContractStarDO> contractStarList = contractStarService.list(
                        new LambdaQueryWrapper<ContractStarDO>().eq(
                                        ContractStarDO::getContractId, e.getBizId())
                                .eq(ContractStarDO::getUserId, userId)
                                .eq(ContractStarDO::getDeleted, false));
                e.setStar(CollUtil.isNotEmpty(contractStarList));
            });
        }

        fullRespVO.setKlmbPage(klmbPage);
        fullRespVO.setContractMoney(contractMoney);
        return fullRespVO;
    }

    @Override
    public KlmbScrollPage<ContractDetailRespVO> pageScroll(ContractDetailScrollPageReqVO reqVO) {
        KlmbScrollPage<ContractDetailDO> klmbPage = KlmbScrollPage.<ContractDetailDO>builder()
                .lastBizId(reqVO.getLastBizId()).pageSize(reqVO.getPageSize()).asc(reqVO.getAsc())
                .build();
        klmbPage.setContent(Collections.emptyList());
        List<String> contractIds = Collections.EMPTY_LIST;
        if (StrUtil.isNotBlank(reqVO.getKeyword())) {
            //根据关键字查询客户id
            List<String> memberUserIds = memberUserService.findMemberUserIdByName(
                    reqVO.getKeyword());
            //根据客户id查询合同信息
            if (CollUtil.isNotEmpty(memberUserIds)) {
                contractIds = this.findDetailByCustomerIds(memberUserIds);
            }
        }

        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        List<String> childUserIds = sysUserService.queryChildUserId(userId);
        ContractDetailQueryDTO queryDTO = ContractDetailConvert.INSTANCE.convert(reqVO);
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLContract(childUserIds, userId);
            if (CollUtil.isNotEmpty(contractIds)
                    && CollUtil.isNotEmpty(bizIds)) {
                bizIds = (List<String>) CollUtil.intersection(contractIds, bizIds);
            }
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                klmbPage = super.pageScroll(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            List<ContractDetailDO> list = super.list(
                    new LambdaQueryWrapper<ContractDetailDO>().eq(ContractDetailDO::getOwnerUserId,
                            userId).eq(ContractDetailDO::getDeleted, false));
            if (CollUtil.isNotEmpty(list)) {
                List<String> bizIds = list.stream().map(ContractDetailDO::getBizId)
                        .collect(Collectors.toList());
                if (CollUtil.isNotEmpty(contractIds) && CollUtil.isNotEmpty(bizIds)) {
                    bizIds = (List<String>) CollUtil.intersection(contractIds, bizIds);
                }
                if (CollUtil.isNotEmpty(bizIds)) {
                    queryDTO.setBizIds(bizIds);
                    klmbPage = super.pageScroll(queryDTO, klmbPage);
                }
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            List<ContractDetailDO> list = super.list(
                    new LambdaQueryWrapper<ContractDetailDO>().in(ContractDetailDO::getOwnerUserId,
                            childUserIds).eq(ContractDetailDO::getDeleted, false));
            if (CollUtil.isNotEmpty(list)) {
                List<String> bizIds = list.stream().map(ContractDetailDO::getBizId)
                        .collect(Collectors.toList());
                if (CollUtil.isNotEmpty(contractIds) && CollUtil.isNotEmpty(bizIds)) {
                    bizIds = (List<String>) CollUtil.intersection(contractIds, bizIds);
                }
                if (CollUtil.isNotEmpty(bizIds)) {
                    queryDTO.setBizIds(bizIds);
                    klmbPage = super.pageScroll(queryDTO, klmbPage);
                }
            }

        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<ContractStarDO> contractStarDOS = contractStarService.list(
                    new LambdaQueryWrapper<ContractStarDO>().eq(ContractStarDO::getUserId, userId)
                            .eq(ContractStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(contractStarDOS)) {
                List<String> bizIds = contractStarDOS.stream().map(ContractStarDO::getContractId)
                        .collect(Collectors.toList());

                if (CollUtil.isNotEmpty(contractIds) && CollUtil.isNotEmpty(bizIds)) {
                    bizIds = (List<String>) CollUtil.intersection(contractIds, bizIds);
                }
                if (CollUtil.isNotEmpty(bizIds)) {
                    queryDTO.setBizIds(bizIds);
                    klmbPage = super.pageScroll(queryDTO, klmbPage);
                }
            }
        }
        List<ContractDetailDO> content = klmbPage.getContent();
        if (CollUtil.isNotEmpty(content)) {
            for (ContractDetailDO contractDetailDO : content) {
                //客户名称 memberUserName
                if (StrUtil.isNotBlank(contractDetailDO.getMemberUserId())) {
                    MemberUserDO memberUserDO = memberUserService.getByBizId(
                            contractDetailDO.getMemberUserId());
                    if (ObjectUtil.isNotNull(memberUserDO)) {
                        contractDetailDO.setMemberUserName(memberUserDO.getName());
                    }
                }
                //商机名称 businessName
                if (StrUtil.isNotBlank(contractDetailDO.getBusinessId())) {
                    BusinessDetailDO businessDetailDO = businessDetailService.getByBizId(
                            contractDetailDO.getBusinessId());
                    if (ObjectUtil.isNotNull(businessDetailDO)) {
                        contractDetailDO.setBusinessName(businessDetailDO.getBusinessName());
                    }
                }
                //客户签约人名称 contactsName
                if (StrUtil.isNotBlank(contractDetailDO.getContactsId())) {
                    MemberContactsDO memberContactsDO = memberContactsService.getByBizId(
                            contractDetailDO.getContactsId());
                    if (ObjectUtil.isNotNull(memberContactsDO)) {
                        contractDetailDO.setContactsName(memberContactsDO.getName());
                    }
                }
                //公司签约人名称 companyUserName
                if (StrUtil.isNotBlank(contractDetailDO.getCompanyUserId())) {
                    SysUserDO sysUserDO = sysUserService.getByBizId(
                            contractDetailDO.getCompanyUserId());
                    if (ObjectUtil.isNotNull(sysUserDO)) {
                        contractDetailDO.setCompanyUserName(sysUserDO.getNickname());
                    }
                }
                //负责人 ownerUserName
                if (StrUtil.isNotBlank(contractDetailDO.getOwnerUserId())) {
                    SysUserDO sysUserDO = sysUserService.getByBizId(
                            contractDetailDO.getOwnerUserId());
                    if (ObjectUtil.isNotNull(sysUserDO)) {
                        contractDetailDO.setOwnerUserName(sysUserDO.getNickname());
                    }
                }
                //创建人名称  creatorName
                if (StrUtil.isNotBlank(contractDetailDO.getCreator())) {
                    SysUserDO sysUserDO = sysUserService.getByBizId(contractDetailDO.getCreator());
                    if (ObjectUtil.isNotNull(sysUserDO)) {
                        contractDetailDO.setCreatorName(sysUserDO.getNickname());
                    }
                }
                //是否标星  star
                List<ContractStarDO> contractStarList = contractStarService.list(
                        new LambdaQueryWrapper<ContractStarDO>().eq(ContractStarDO::getContractId,
                                        contractDetailDO.getBizId()).eq(ContractStarDO::getUserId, userId)
                                .eq(ContractStarDO::getDeleted, false));
                contractDetailDO.setStar(CollUtil.isNotEmpty(contractStarList));
            }


        }

        KlmbScrollPage<ContractDetailRespVO> respPage = new KlmbScrollPage<>();
        respPage = ContractDetailConvert.INSTANCE.convert(klmbPage);
        if (CollUtil.isEmpty(respPage.getContent())) {
            respPage.setContent(Collections.EMPTY_LIST);
        }
        return respPage;
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        List<ContractDetailDO> entities = this.listByBizIds(bizIds);
        if (CollUtil.isEmpty(entities)) {
            return;
        }
        super.removeBatchByIds(entities);
        //同时删除商机产品关系集合
        contractProductService.removeContractProduct(bizIds);
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
    public void star(String bizId) {
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        LambdaQueryWrapper<ContractStarDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractStarDO::getContractId, bizId);
        wrapper.eq(ContractStarDO::getUserId, userId);
        wrapper.eq(ContractStarDO::getDeleted, false);
        ContractStarDO star = contractStarService.getOne(wrapper);
        if (star == null) {
            star = new ContractStarDO();
            star.setContractId(bizId);
            star.setUserId(userId);
            contractStarService.save(star);
        } else {
            contractStarService.removeById(star.getId());
        }
    }

    @Override
    public ContractDetailRespVO findDetailByBizId(String bizId) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        ContractDetailRespVO respVO = mapper.findDetailByBizId(bizId);
        List<ContractStarDO> contractStarList = contractStarService.list(
                new LambdaQueryWrapper<ContractStarDO>().eq(ContractStarDO::getContractId, bizId)
                        .eq(ContractStarDO::getUserId, userId)
                        .eq(ContractStarDO::getDeleted, false));
        respVO.setStar(CollUtil.isNotEmpty(contractStarList));
        //根据合同id查询合同与产品的关系集合
        respVO.setContractProductRespList(
                contractProductService.getContractProductByContractId(bizId));
        return respVO;
    }

    @Override
    public List<String> findDetailByCustomerIds(List<String> customerIds) {
        List<String> collect = Collections.EMPTY_LIST;
        List<ContractDetailDO> list = super.list(
                new LambdaQueryWrapper<ContractDetailDO>().in(ContractDetailDO::getMemberUserId,
                        customerIds).eq(ContractDetailDO::getDeleted, false));
        if (CollUtil.isNotEmpty(list)) {
            collect = list.stream().map(ContractDetailDO::getBizId).collect(Collectors.toList());
        }
        return collect;
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
