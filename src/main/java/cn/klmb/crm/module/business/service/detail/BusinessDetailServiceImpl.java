package cn.klmb.crm.module.business.service.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailRespVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailUpdateReqVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductRespVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductSaveReqVO;
import cn.klmb.crm.module.business.convert.detail.BusinessDetailConvert;
import cn.klmb.crm.module.business.convert.product.BusinessProductConvert;
import cn.klmb.crm.module.business.dao.detail.BusinessDetailMapper;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.dto.product.BusinessProductQueryDTO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import cn.klmb.crm.module.business.entity.userstar.BusinessUserStarDO;
import cn.klmb.crm.module.business.enums.BusinessStatusEnum;
import cn.klmb.crm.module.business.service.product.BusinessProductService;
import cn.klmb.crm.module.business.service.userstar.BusinessUserStarService;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

    public BusinessDetailServiceImpl(BusinessProductService businessProductService,
            BusinessDetailMapper mapper, SysUserService sysUserService,
            MemberTeamService memberTeamService, BusinessUserStarService businessUserStarService) {
        this.businessProductService = businessProductService;
        this.sysUserService = sysUserService;
        this.memberTeamService = memberTeamService;
        this.businessUserStarService = businessUserStarService;
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
            businessProductSaveList.forEach(e -> {
                BusinessProductDO businessProductDO = BusinessProductConvert.INSTANCE.convert(e);
                businessProductService.saveDO(businessProductDO);
            });
        }
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
    }

    @Override
    public boolean updateBusiness(BusinessDetailUpdateReqVO updateReqVO) {
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
        }
        return success;
    }

    @Override
    public BusinessDetailRespVO getBusinessByBizId(String bizId) {
        BusinessDetailDO businessDetailDO = super.getByBizId(bizId);
        BusinessDetailRespVO respVO = BusinessDetailConvert.INSTANCE.convert(businessDetailDO);
        List<BusinessProductRespVO> productRespList = Collections.emptyList();
        if (ObjectUtil.isNotNull(respVO)) {
            List<BusinessProductDO> businessProductList = businessProductService.list(
                    BusinessProductQueryDTO.builder().businessId(bizId).build());
            if (CollUtil.isNotEmpty(businessProductList)) {
                productRespList = BusinessProductConvert.INSTANCE.convert(
                        businessProductList);
            }
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
            });
        }
        return BusinessDetailConvert.INSTANCE.convert(klmbPage);
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
