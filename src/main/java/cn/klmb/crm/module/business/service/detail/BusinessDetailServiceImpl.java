package cn.klmb.crm.module.business.service.detail;

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
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailRespVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailScrollPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailUpdateReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.CrmRelevanceBusinessBO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductRespVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductSaveReqVO;
import cn.klmb.crm.module.business.convert.detail.BusinessDetailConvert;
import cn.klmb.crm.module.business.convert.product.BusinessProductConvert;
import cn.klmb.crm.module.business.dao.detail.BusinessDetailMapper;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.entity.contacts.BusinessContactsDO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import cn.klmb.crm.module.business.entity.userstar.BusinessUserStarDO;
import cn.klmb.crm.module.business.enums.BusinessStatusEnum;
import cn.klmb.crm.module.business.service.contacts.BusinessContactsService;
import cn.klmb.crm.module.business.service.product.BusinessProductService;
import cn.klmb.crm.module.business.service.userstar.BusinessUserStarService;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsRespVO;
import cn.klmb.crm.module.member.convert.contacts.MemberContactsConvert;
import cn.klmb.crm.module.member.dto.contacts.MemberContactsQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.contactsstar.MemberContactsStarDO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.contactsstar.MemberContactsStarService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * 商机 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class BusinessDetailServiceImpl extends
        KlmbBaseServiceImpl<BusinessDetailDO, BusinessDetailQueryDTO, BusinessDetailMapper> implements
        BusinessDetailService {

    private final BusinessProductService businessProductService;

    private final SysUserService sysUserService;

    private final MemberTeamService memberTeamService;

    private final BusinessUserStarService businessUserStarService;

    private final MemberUserService memberUserService;

    private final XxlJobApiUtils xxlJobApiUtils;

    private final BusinessContactsService businessContactsService;

    private final MemberContactsService memberContactsService;

    private final MemberContactsStarService memberContactsStarService;

    private final SysConfigService sysConfigService;

    public BusinessDetailServiceImpl(BusinessProductService businessProductService,
            BusinessDetailMapper mapper, SysUserService sysUserService,
            @Lazy MemberTeamService memberTeamService,
            BusinessUserStarService businessUserStarService,
            MemberUserService memberUserService, XxlJobApiUtils xxlJobApiUtils,
            BusinessContactsService businessContactsService,
            MemberContactsService memberContactsService,
            @Lazy MemberContactsStarService memberContactsStarService,
            SysConfigService sysConfigService) {
        this.businessProductService = businessProductService;
        this.sysUserService = sysUserService;
        this.memberTeamService = memberTeamService;
        this.businessUserStarService = businessUserStarService;
        this.memberUserService = memberUserService;
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.businessContactsService = businessContactsService;
        this.memberContactsService = memberContactsService;
        this.memberContactsStarService = memberContactsStarService;
        this.sysConfigService = sysConfigService;
        this.mapper = mapper;
    }

    @Override
    public String saveBusiness(BusinessDetailSaveReqVO saveReqVO) {
        BusinessDetailDO saveDO = BusinessDetailConvert.INSTANCE.convert(saveReqVO);
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        saveDO.setOwnerUserId(userId);
        String bizId = "";
        if (super.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        //获取商机产品关系集合
        List<BusinessProductSaveReqVO> businessProductSaveList = saveReqVO.getBusinessProductSaveList();
        if (CollUtil.isNotEmpty(businessProductSaveList)) {
            String finalBizId = bizId;
            businessProductSaveList.forEach(e -> {
                e.setBusinessId(finalBizId);
                BusinessProductDO businessProductDO = BusinessProductConvert.INSTANCE.convert(e);
                businessProductService.saveDO(businessProductDO);
            });
        }

        memberTeamService.saveDO(
                MemberTeamDO.builder().power(3).userId(userId).type(CrmEnum.BUSINESS.getType())
                        .typeId(bizId).build());

        SysConfigDO sysConfigDO = sysConfigService.getByConfigKey(
                SysConfigKeyEnum.CONTACTS_REMINDER.getType());
        xxlJobApiUtils.changeTask(
                XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                        .executorHandler("customerContactReminderHandler").author("liuyuepan")
                        .ownerUserId(saveDO.getOwnerUserId())
                        .bizId(bizId).nextTime(saveDO.getNextTime()).name(saveDO.getBusinessName())
                        .operateType(1)
                        .messageType(CrmEnum.BUSINESS.getRemarks())
                        .contactsType(CrmEnum.BUSINESS.getType()
                        ).offsetValue(sysConfigDO.getValue()).build());

        return bizId;
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        List<BusinessDetailDO> entities = this.listByBizIds(bizIds);
        if (CollUtil.isEmpty(entities)) {
            return;
        }
        super.removeBatchByIds(entities);
        //同时删除商机产品关系集合
        businessProductService.removeBusinessProduct(bizIds);
        bizIds.forEach(e -> {
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .bizId(e).operateType(3)
                            .messageType(CrmEnum.BUSINESS.getRemarks())
                            .contactsType(CrmEnum.BUSINESS.getType()).build());
        });
    }

    @Override
    public boolean updateBusiness(BusinessDetailUpdateReqVO updateReqVO) {
        BusinessDetailDO businessDetailDO = super.getByBizId(updateReqVO.getBizId());
        LocalDateTime nextTime = businessDetailDO.getNextTime();
        BusinessDetailDO updateDO = BusinessDetailConvert.INSTANCE.convert(updateReqVO);
        boolean success = super.updateDO(updateDO);
        if (success) {
            //删除历史商机产品关系集合
            businessProductService.removeBusinessProduct(
                    Collections.singletonList(updateReqVO.getBizId()));
            //获取商机产品关系集合
            List<BusinessProductSaveReqVO> businessProductSaveList = updateReqVO.getBusinessProductSaveList();
            if (CollUtil.isNotEmpty(businessProductSaveList)) {
                businessProductSaveList.forEach(e -> {
                    BusinessProductDO businessProductDO = BusinessProductConvert.INSTANCE.convert(
                            e);
                    businessProductService.saveDO(businessProductDO);
                });
            }

            if (!nextTime.isEqual(updateDO.getNextTime())) {
                SysConfigDO sysConfigDO = sysConfigService.getByConfigKey(
                        SysConfigKeyEnum.CONTACTS_REMINDER.getType());
                xxlJobApiUtils.changeTask(
                        XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm")
                                .title("crm执行器")
                                .executorHandler("customerContactReminderHandler")
                                .author("liuyuepan")
                                .ownerUserId(updateDO.getOwnerUserId())
                                .bizId(updateDO.getBizId()).nextTime(updateDO.getNextTime())
                                .name(updateDO.getBusinessName()).operateType(2)
                                .messageType(CrmEnum.BUSINESS.getRemarks())
                                .contactsType(CrmEnum.BUSINESS.getType())
                                .offsetValue(sysConfigDO.getValue()).build());
            }
        }

        return success;
    }

    @Override
    public BusinessDetailRespVO getBusinessByBizId(String bizId) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        BusinessDetailDO businessDetailDO = super.getByBizId(bizId);
        if (ObjectUtil.isNotNull(businessDetailDO)) {
            if (StrUtil.isNotBlank(businessDetailDO.getCustomerId())) {
                MemberUserDO memberUserDO = memberUserService.getByBizId(
                        businessDetailDO.getCustomerId());
                businessDetailDO.setCustomerName(
                        ObjectUtil.isNotNull(memberUserDO) ? memberUserDO.getName() : null);
            }
            if (StrUtil.isNotBlank(businessDetailDO.getOwnerUserId())) {
                SysUserDO sysUserDO = sysUserService.getByBizId(businessDetailDO.getOwnerUserId());
                businessDetailDO.setOwnerUserName(
                        ObjectUtil.isNotNull(sysUserDO) ? sysUserDO.getNickname() : null);
            }

            List<BusinessUserStarDO> businessUserStarDOS = businessUserStarService.list(
                    new LambdaQueryWrapper<BusinessUserStarDO>().eq(
                                    BusinessUserStarDO::getBusinessId, bizId)
                            .eq(BusinessUserStarDO::getUserId, userId)
                            .eq(BusinessUserStarDO::getDeleted, false));
            businessDetailDO.setStar(CollUtil.isNotEmpty(businessUserStarDOS));

        }
        BusinessDetailRespVO respVO = BusinessDetailConvert.INSTANCE.convert(businessDetailDO);
        List<BusinessProductRespVO> productRespList = Collections.emptyList();
        if (ObjectUtil.isNotNull(respVO)) {
            productRespList = businessProductService.getBusinessProductByBusinessId(bizId);
        }
        respVO.setBusinessProductRespList(productRespList);
        return respVO;
    }

    @Override
    public KlmbPage<BusinessDetailRespVO> page(BusinessDetailPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        List<String> childUserIds = sysUserService.queryChildUserId(
                userId);
        KlmbPage<BusinessDetailDO> klmbPage = KlmbPage.<BusinessDetailDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();

        BusinessDetailQueryDTO queryDTO = BusinessDetailConvert.INSTANCE.convert(reqVO);
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                klmbPage = super.page(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            queryDTO.setOwnerUserId(userId);
            klmbPage = super.page(queryDTO, klmbPage);
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            if (CollUtil.isNotEmpty(childUserIds)) {
                queryDTO.setOwnerUserIds(childUserIds);
                klmbPage = super.page(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<BusinessUserStarDO> businessUserStarDOS = businessUserStarService.list(
                    new LambdaQueryWrapper<BusinessUserStarDO>().eq(BusinessUserStarDO::getUserId,
                            userId).eq(BusinessUserStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(businessUserStarDOS)) {
                List<String> collect = businessUserStarDOS.stream()
                        .map(BusinessUserStarDO::getBusinessId).collect(
                                Collectors.toList());
                queryDTO.setBizIds(collect);
                klmbPage = super.page(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.WIN.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setBusinessStatus(BusinessStatusEnum.WIN.getType().toString());
                klmbPage = super.page(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.LOSE.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setBusinessStatus(BusinessStatusEnum.LOSE.getType().toString());
                klmbPage = super.page(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.INVALID.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setBusinessStatus(BusinessStatusEnum.INVALID.getType().toString());
                klmbPage = super.page(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ING.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setBusinessStatus(BusinessStatusEnum.ING.getType().toString());
                klmbPage = super.page(queryDTO, klmbPage);
            }
        }
        List<BusinessDetailDO> content = klmbPage.getContent();
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                if (ObjectUtil.isNotNull(sysUserDO)) {
                    e.setOwnerUserName(sysUserDO.getNickname());
                }

                List<BusinessUserStarDO> businessUserStarDOS = businessUserStarService.list(
                        new LambdaQueryWrapper<BusinessUserStarDO>().eq(
                                        BusinessUserStarDO::getBusinessId, e.getBizId())
                                .eq(BusinessUserStarDO::getUserId, userId)
                                .eq(BusinessUserStarDO::getDeleted, false));
                e.setStar(CollUtil.isNotEmpty(businessUserStarDOS));

                if (StrUtil.isNotBlank(e.getCustomerId())) {
                    MemberUserDO memberUserDO = memberUserService.getByBizId(
                            e.getCustomerId());
                    e.setCustomerName(
                            ObjectUtil.isNotNull(memberUserDO) ? memberUserDO.getName() : null);
                }
            });
        }
        return BusinessDetailConvert.INSTANCE.convert(klmbPage);
    }

    @Override
    public KlmbScrollPage<BusinessDetailRespVO> pageScroll(BusinessDetailScrollPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        List<String> childUserIds = sysUserService.queryChildUserId(
                userId);
        KlmbScrollPage<BusinessDetailDO> klmbPage = KlmbScrollPage.<BusinessDetailDO>builder()
                .lastBizId(reqVO.getLastBizId())
                .pageSize(reqVO.getPageSize())
                .asc(reqVO.getAsc())
                .build();

        BusinessDetailQueryDTO queryDTO = BusinessDetailConvert.INSTANCE.convert(reqVO);
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                klmbPage = super.pageScroll(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            queryDTO.setOwnerUserId(userId);
            klmbPage = super.pageScroll(queryDTO, klmbPage);
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            if (CollUtil.isNotEmpty(childUserIds)) {
                queryDTO.setOwnerUserIds(childUserIds);
                klmbPage = super.pageScroll(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<BusinessUserStarDO> businessUserStarDOS = businessUserStarService.list(
                    new LambdaQueryWrapper<BusinessUserStarDO>().eq(BusinessUserStarDO::getUserId,
                            userId).eq(BusinessUserStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(businessUserStarDOS)) {
                List<String> collect = businessUserStarDOS.stream()
                        .map(BusinessUserStarDO::getBusinessId).collect(
                                Collectors.toList());
                queryDTO.setBizIds(collect);
                klmbPage = super.pageScroll(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.WIN.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setBusinessStatus(BusinessStatusEnum.WIN.getType().toString());
                klmbPage = super.pageScroll(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.LOSE.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setBusinessStatus(BusinessStatusEnum.LOSE.getType().toString());
                klmbPage = super.pageScroll(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.INVALID.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setBusinessStatus(BusinessStatusEnum.INVALID.getType().toString());
                klmbPage = super.pageScroll(queryDTO, klmbPage);
            }
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ING.getType())) {
            childUserIds.add(userId);
            List<String> bizIds = getALLBusiness(childUserIds, userId);
            if (CollUtil.isNotEmpty(bizIds)) {
                queryDTO.setBizIds(bizIds);
                queryDTO.setBusinessStatus(BusinessStatusEnum.ING.getType().toString());
                klmbPage = super.pageScroll(queryDTO, klmbPage);
            }
        }
        List<BusinessDetailDO> content = klmbPage.getContent();
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                if (ObjectUtil.isNotNull(sysUserDO)) {
                    e.setOwnerUserName(sysUserDO.getNickname());
                }

                List<BusinessUserStarDO> businessUserStarDOS = businessUserStarService.list(
                        new LambdaQueryWrapper<BusinessUserStarDO>().eq(
                                        BusinessUserStarDO::getBusinessId, e.getBizId())
                                .eq(BusinessUserStarDO::getUserId, userId)
                                .eq(BusinessUserStarDO::getDeleted, false));
                e.setStar(CollUtil.isNotEmpty(businessUserStarDOS));

                if (StrUtil.isNotBlank(e.getCustomerId())) {
                    MemberUserDO memberUserDO = memberUserService.getByBizId(
                            e.getCustomerId());
                    e.setCustomerName(
                            ObjectUtil.isNotNull(memberUserDO) ? memberUserDO.getName() : null);
                }
            });
        }
        KlmbScrollPage<BusinessDetailRespVO> respPage = new KlmbScrollPage<>();
        respPage = BusinessDetailConvert.INSTANCE.convert(klmbPage);
        if (CollUtil.isEmpty(respPage.getContent())) {
            respPage.setContent(Collections.EMPTY_LIST);
        }
        return respPage;
    }

    @Override
    public void star(String bizId) {
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        LambdaQueryWrapper<BusinessUserStarDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BusinessUserStarDO::getBusinessId, bizId);
        wrapper.eq(BusinessUserStarDO::getUserId, userId);
        wrapper.eq(BusinessUserStarDO::getDeleted, false);
        BusinessUserStarDO star = businessUserStarService.getOne(wrapper);
        if (star == null) {
            star = new BusinessUserStarDO();
            star.setBusinessId(bizId);
            star.setUserId(userId);
            businessUserStarService.save(star);
        } else {
            businessUserStarService.removeById(star.getId());
        }
    }

    @Override
    public void relateContacts(CrmRelevanceBusinessBO relevanceBusinessBO) {
        relevanceBusinessBO.getContactsIds().forEach(id -> {
            businessContactsService.saveDO(
                    BusinessContactsDO.builder().businessId(relevanceBusinessBO.getBusinessId())
                            .contactsId(id).build());
        });
    }

    @Override
    public void unrelateContacts(CrmRelevanceBusinessBO relevanceBusinessBO) {
        BusinessDetailDO businessDetailDO = super.getByBizId(relevanceBusinessBO.getBusinessId());
        relevanceBusinessBO.getContactsIds().forEach(r -> {
            if (Objects.equals(r, businessDetailDO.getContactsId())) {
                lambdaUpdate().set(BusinessDetailDO::getContactsId, null)
                        .eq(BusinessDetailDO::getBizId, businessDetailDO.getBizId()).update();
            }
        });
        LambdaQueryWrapper<BusinessContactsDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BusinessContactsDO::getBusinessId, relevanceBusinessBO.getBusinessId());
        wrapper.in(BusinessContactsDO::getContactsId, relevanceBusinessBO.getContactsIds());
        businessContactsService.remove(wrapper);
    }

    @Override
    public KlmbPage<MemberContactsRespVO> pageContacts(MemberContactsPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        MemberContactsQueryDTO queryDTO = MemberContactsConvert.INSTANCE.convert(reqVO);
        KlmbPage<MemberContactsDO> klmbPage = KlmbPage.<MemberContactsDO>builder()
                .pageNo(reqVO.getPageNo()).pageSize(reqVO.getPageSize())
                .sortingFields(reqVO.getSortingFields()).build();
        //通过businessId来查询联系人
        List<BusinessContactsDO> list = businessContactsService.list(
                new LambdaQueryWrapper<BusinessContactsDO>().eq(BusinessContactsDO::getBusinessId,
                        reqVO.getBusinessId()).eq(BusinessContactsDO::getDeleted, false));
        if (CollUtil.isNotEmpty(list)) {
            List<String> collect = list.stream().map(BusinessContactsDO::getContactsId)
                    .collect(Collectors.toList());
            queryDTO.setBizIds(collect);

            klmbPage = memberContactsService.page(queryDTO, klmbPage);
            List<MemberContactsDO> content = klmbPage.getContent();
            if (CollUtil.isNotEmpty(content)) {
                content.forEach(e -> {
                    MemberUserDO memberUserDO = memberUserService.getByBizId(e.getCustomerId());
                    if (ObjectUtil.isNotNull(memberUserDO)) {
                        e.setCustomerName(memberUserDO.getName());
                        e.setIsFirstContacts(
                                StrUtil.equals(memberUserDO.getContactsId(), e.getBizId()));
                    }
                    if (StrUtil.isNotBlank(e.getParentContactsId())) {
                        MemberContactsDO memberContactsDO = memberContactsService.getByBizId(
                                e.getParentContactsId());
                        e.setParentContactsName(memberContactsDO.getName());
                    }
                    SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                    if (ObjectUtil.isNotNull(sysUserDO)) {
                        e.setOwnerUserName(sysUserDO.getNickname());
                    }
                    List<MemberContactsStarDO> starDOList = memberContactsStarService.list(
                            new LambdaQueryWrapper<MemberContactsStarDO>().eq(
                                            MemberContactsStarDO::getContactsId, e.getBizId())
                                    .eq(MemberContactsStarDO::getUserId, userId)
                                    .eq(MemberContactsStarDO::getDeleted, false));
                    e.setStar(CollUtil.isNotEmpty(starDOList));

                });
            }
        }

        return MemberContactsConvert.INSTANCE.convert(klmbPage);
    }


    //获取当前用户下的全部商机bizId
    private List<String> getALLBusiness(List<String> childUserIds, String userId) {
        List<String> bizIds = new ArrayList<>();
        //根据用户集合查询该用户们负责的商机
        List<BusinessDetailDO> businessDetailList = super.list(
                new LambdaQueryWrapper<BusinessDetailDO>().in(BusinessDetailDO::getOwnerUserId,
                        childUserIds).eq(BusinessDetailDO::getDeleted, false));

        if (CollUtil.isNotEmpty(businessDetailList)) {
            bizIds = CollUtil.unionAll(
                    businessDetailList.stream().map(BusinessDetailDO::getBizId)
                            .collect(Collectors.toList()), bizIds);
        }
        //根据当前用户查询团队成员表中商机id,根据商机id查询客户负责人id
        List<MemberTeamDO> memberTeamDOList = memberTeamService.list(
                new LambdaQueryWrapper<MemberTeamDO>().eq(MemberTeamDO::getUserId, userId)
                        .eq(MemberTeamDO::getType, CrmEnum.BUSINESS.getType())
                        .eq(MemberTeamDO::getDeleted, false));
        if (CollUtil.isNotEmpty(memberTeamDOList)) {
            bizIds = CollUtil.unionAll(memberTeamDOList.stream().map(MemberTeamDO::getTypeId)
                    .collect(Collectors.toList()), bizIds);
        }
        return bizIds;
    }


}
