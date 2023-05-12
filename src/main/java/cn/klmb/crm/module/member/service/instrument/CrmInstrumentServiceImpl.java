package cn.klmb.crm.module.member.service.instrument;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.common.pojo.BiAuthority;
import cn.klmb.crm.framework.common.pojo.BiParams;
import cn.klmb.crm.framework.common.util.data.BiTimeUtil;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.business.convert.detail.BusinessDetailConvert;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.service.detail.BusinessDetailService;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmCountRankVO;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmDataSummaryVO;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmInstrumentVO;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivityRespVO;
import cn.klmb.crm.module.member.convert.contacts.MemberContactsConvert;
import cn.klmb.crm.module.member.convert.teamactivity.MemberTeamActivityConvert;
import cn.klmb.crm.module.member.convert.user.MemberUserConvert;
import cn.klmb.crm.module.member.dao.instrument.CrmInstrumentMapper;
import cn.klmb.crm.module.member.dto.contacts.MemberContactsQueryDTO;
import cn.klmb.crm.module.member.dto.teamactivity.MemberTeamActivityQueryDTO;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.teamactivity.MemberTeamActivityDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.enums.DataTypeEnum;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.teamactivity.MemberTeamActivityService;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.entity.file.SysFileDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import cn.klmb.crm.module.system.service.file.SysFileService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CrmInstrumentServiceImpl implements CrmInstrumentService {


    private final SysUserService sysUserService;

    private final SysDeptService sysDeptService;

    private final CrmInstrumentMapper crmInstrumentMapper;

    private final MemberUserService memberUserService;

    private final MemberContactsService memberContactsService;

    private final BusinessDetailService businessDetailService;

    private final MemberTeamActivityService memberTeamActivityService;

    private final SysFileService sysFileService;

    public CrmInstrumentServiceImpl(SysUserService sysUserService,
            SysDeptService sysDeptService, CrmInstrumentMapper crmInstrumentMapper,
            MemberUserService memberUserService, MemberContactsService memberContactsService,
            BusinessDetailService businessDetailService,
            MemberTeamActivityService memberTeamActivityService, SysFileService sysFileService) {
        this.sysUserService = sysUserService;
        this.sysDeptService = sysDeptService;
        this.crmInstrumentMapper = crmInstrumentMapper;
        this.memberUserService = memberUserService;
        this.memberContactsService = memberContactsService;
        this.businessDetailService = businessDetailService;
        this.memberTeamActivityService = memberTeamActivityService;
        this.sysFileService = sysFileService;
    }


    @Override
    public CrmInstrumentVO queryBulletin(BiParams biParams) {
        BiTimeUtil.BiTimeEntity biTimeEntity = BiTimeUtil.analyzeTime(biParams);
        List<String> userIds = getUserIds(biParams);
        if (Objects.equals(biParams.getDataType(), DataTypeEnum.CUSTOMIZE.getType())
                && CollUtil.isEmpty(userIds)) {
            return CrmInstrumentVO.builder().activityCount(0L).businessCount(0L)
                    .businessMoney(new BigDecimal(
                            "0.00")).contactsCount(0L).customerCount(0L).build();
        }
        CrmInstrumentVO crmInstrumentVO = crmInstrumentMapper.queryBulletin(biTimeEntity, userIds);
        return crmInstrumentVO;
    }

    @Override
    public List<CrmCountRankVO> countRank(BiParams biParams) {
        BiTimeUtil.BiTimeEntity biTimeEntity = BiTimeUtil.analyzeTime(biParams);
        List<String> userIds = getUserIds(biParams);
        Integer rankType = biParams.getRankType();
        if (ObjectUtil.isNull(rankType)) {
            return Collections.emptyList();
        }
        if (Objects.equals(biParams.getDataType(), DataTypeEnum.CUSTOMIZE.getType())
                && CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        switch (rankType) {
            case 1: {
                //新增客户数
                return crmInstrumentMapper.customerCountRank(biTimeEntity,
                        userIds);
            }
            case 2: {
                //新增联系人排行
                return crmInstrumentMapper.contactsCountRank(biTimeEntity,
                        userIds);
            }
            case 3: {
                //新增跟进记录排行
                return crmInstrumentMapper.recordCountRank(biTimeEntity,
                        userIds);
            }
            default: {
                return Collections.emptyList();
            }
        }
    }

    @Override
    public KlmbPage<?> queryBulletinInfo(BiParams biParams) {
        KlmbPage<Object> klmbPage = new KlmbPage<>();
        klmbPage.setContent(Collections.emptyList());
        BiTimeUtil.BiTimeEntity biTimeEntity = BiTimeUtil.analyzeTime(biParams);
        List<String> userIds = getUserIds(biParams);
        if (Objects.equals(biParams.getDataType(), DataTypeEnum.CUSTOMIZE.getType())
                && CollUtil.isEmpty(userIds)) {
            return klmbPage;
        }
        Integer label = biParams.getLabel();
        switch (label) {
            case 2: {
                //新增客户详情
                List<String> newCustomer = crmInstrumentMapper.newCustomer(biTimeEntity, userIds);
                if (CollUtil.isEmpty(newCustomer)) {
                    return klmbPage;
                }
                KlmbPage<MemberUserDO> klmbMemberUserPage = KlmbPage.<MemberUserDO>builder()
                        .pageNo(biParams.getPageNo())
                        .pageSize(biParams.getPageSize())
                        .sortingFields(biParams.getSortingFields())
                        .build();
                MemberUserQueryDTO memberUserQueryDTO = new MemberUserQueryDTO();
                memberUserQueryDTO.setBizIds(newCustomer);
                memberUserQueryDTO.setKeyword(biParams.getSearch());
                KlmbPage<MemberUserDO> page = memberUserService.page(memberUserQueryDTO,
                        klmbMemberUserPage);
                List<MemberUserDO> content = page.getContent();
                if (CollUtil.isNotEmpty(content)) {
                    content.forEach(e -> {
                        SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                        if (ObjectUtil.isNotNull(sysUserDO)) {
                            e.setOwnerUserName(sysUserDO.getNickname());
                        }
                        if (StrUtil.isNotBlank(e.getPreOwnerUserId())) {
                            SysUserDO userDO = sysUserService.getByBizId(e.getPreOwnerUserId());
                            e.setPreOwnerUserName(
                                    ObjectUtil.isNotNull(userDO) ? userDO.getNickname() : null);
                        }
                    });
                }
                return MemberUserConvert.INSTANCE.convert(page);
            }

            case 3: {
                List<String> newContacts = crmInstrumentMapper.newContacts(biTimeEntity, userIds);
                if (CollUtil.isEmpty(newContacts)) {
                    return klmbPage;
                }
                KlmbPage<MemberContactsDO> klmbMemberContactsPage = KlmbPage.<MemberContactsDO>builder()
                        .pageNo(biParams.getPageNo())
                        .pageSize(biParams.getPageSize())
                        .sortingFields(biParams.getSortingFields())
                        .build();
                MemberContactsQueryDTO memberContactsQueryDTO = new MemberContactsQueryDTO();
                memberContactsQueryDTO.setBizIds(newContacts);
                memberContactsQueryDTO.setKeyword(biParams.getSearch());
                KlmbPage<MemberContactsDO> page = memberContactsService.page(memberContactsQueryDTO,
                        klmbMemberContactsPage);

                List<MemberContactsDO> content = page.getContent();
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
                    });
                }
                return MemberContactsConvert.INSTANCE.convert(page);
            }

            case 5: {
                List<String> newBusiness = crmInstrumentMapper.newBusiness(biTimeEntity, userIds);
                if (CollUtil.isEmpty(newBusiness)) {
                    return klmbPage;
                }
                KlmbPage<BusinessDetailDO> klmbBusinessDetailPage = KlmbPage.<BusinessDetailDO>builder()
                        .pageNo(biParams.getPageNo())
                        .pageSize(biParams.getPageSize())
                        .sortingFields(biParams.getSortingFields())
                        .build();
                BusinessDetailQueryDTO businessDetailQueryDTO = new BusinessDetailQueryDTO();
                businessDetailQueryDTO.setBizIds(newBusiness);
                businessDetailQueryDTO.setBusinessName(biParams.getSearch());

                KlmbPage<BusinessDetailDO> page = businessDetailService.page(businessDetailQueryDTO,
                        klmbBusinessDetailPage);
                List<BusinessDetailDO> content = page.getContent();
                if (CollUtil.isNotEmpty(content)) {
                    content.forEach(e -> {
                        SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                        if (ObjectUtil.isNotNull(sysUserDO)) {
                            e.setOwnerUserName(sysUserDO.getNickname());
                        }
                        if (StrUtil.isNotBlank(e.getCustomerId())) {
                            MemberUserDO memberUserDO = memberUserService.getByBizId(
                                    e.getCustomerId());
                            e.setCustomerName(
                                    ObjectUtil.isNotNull(memberUserDO) ? memberUserDO.getName()
                                            : null);
                        }
                    });
                }
                return BusinessDetailConvert.INSTANCE.convert(page);
            }

            case 19: {
                List<String> newActivity = crmInstrumentMapper.newActivity(biTimeEntity, userIds);
                if (CollUtil.isEmpty(newActivity)) {
                    return klmbPage;
                }
                KlmbPage<MemberTeamActivityDO> klmbActivityPage = KlmbPage.<MemberTeamActivityDO>builder()
                        .pageNo(biParams.getPageNo())
                        .pageSize(biParams.getPageSize())
                        .sortingFields(biParams.getSortingFields())
                        .build();
                MemberTeamActivityQueryDTO memberTeamActivityQueryDTO = new MemberTeamActivityQueryDTO();
                memberTeamActivityQueryDTO.setBizIds(newActivity);
                memberTeamActivityQueryDTO.setActivityType(biParams.getQueryType());
                KlmbPage<MemberTeamActivityDO> page = memberTeamActivityService.page(
                        memberTeamActivityQueryDTO, klmbActivityPage);

                KlmbPage<MemberTeamActivityRespVO> convert = MemberTeamActivityConvert.INSTANCE.convert(
                        page);
                if (ObjectUtil.isNotNull(convert) && CollUtil.isNotEmpty(convert.getContent())) {
                    List<MemberTeamActivityRespVO> content = convert.getContent();
                    content.forEach(e -> {
                                List<String> imgIds = e.getImgIds();
                                List<String> fileIds = e.getFileIds();
                                if (CollUtil.isNotEmpty(imgIds)) {
                                    List<SysFileDO> sysFileDOS = sysFileService.listByBizIds(imgIds);
                                    e.setImgInfo(sysFileDOS);
                                }
                                if (CollUtil.isNotEmpty(fileIds)) {
                                    List<SysFileDO> sysFileDOS = sysFileService.listByBizIds(fileIds);
                                    e.setFileInfo(sysFileDOS);
                                }
                                SysUserDO sysUserDO = sysUserService.getByBizId(e.getCreator());
                                if (ObjectUtil.isNotNull(sysUserDO)) {
                                    e.setCreatorName(sysUserDO.getNickname());
                                    e.setAvatar(sysUserDO.getAvatar());
                                }
                            }
                    );
                }
                return convert;
            }
            default: {
                return klmbPage;
            }
        }
    }

    @Override
    public CrmDataSummaryVO queryDataInfo(BiParams biParams) {
        BiTimeUtil.BiTimeEntity biTimeEntity = BiTimeUtil.analyzeTime(biParams);
        List<String> userIds = getUserIds(biParams);
        if (Objects.equals(biParams.getDataType(), DataTypeEnum.CUSTOMIZE.getType())
                && CollUtil.isEmpty(userIds)) {
            return CrmDataSummaryVO.builder().activityNum(0L).activityRealNum(0L).allBusiness(0L)
                    .allCustomer(0L).businessMoney(new BigDecimal("0.00")).dealCustomer(0L)
                    .putInPoolNum(0L).receiveNum(0L).endBusiness(0L).loseBusiness(0L).build();
        }
        CrmDataSummaryVO crmDataSummaryVO = crmInstrumentMapper.queryDataInfo(biTimeEntity,
                userIds);
        return crmDataSummaryVO;
    }


    private BiAuthority handleDataType(BiParams biParams) {
        //数据类型
        SysUserDO sysUserDO = sysUserService.getByBizId(WebFrameworkUtils.getLoginUserId());
        DataTypeEnum typeEnum = DataTypeEnum.parse(biParams.getDataType());
        List<String> userIdList = new ArrayList<>();
        List<String> deptIdList = new ArrayList<>();
        if (typeEnum != null) {
            if (typeEnum == DataTypeEnum.SELF) {
                userIdList.add(WebFrameworkUtils.getLoginUserId());
            } else if (typeEnum == DataTypeEnum.SELF_AND_CHILD) {
                userIdList.addAll(
                        sysUserService.queryChildUserId(WebFrameworkUtils.getLoginUserId()));
                userIdList.add(WebFrameworkUtils.getLoginUserId());
            } else if (typeEnum == DataTypeEnum.DEPT) {
                deptIdList.add(sysUserDO.getDeptId());
                userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
            } else if (typeEnum == DataTypeEnum.DEPT_AND_CHILD) {
                deptIdList.addAll(sysDeptService.queryChildDept(sysUserDO.getDeptId()));
                deptIdList.add(sysUserDO.getDeptId());
                userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
            } else if (typeEnum == DataTypeEnum.CUSTOMIZE) {
                userIdList = Collections.emptyList();
                deptIdList = Collections.emptyList();
            } else {
                deptIdList.addAll(sysDeptService.queryChildDept("0"));
                userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
            }
        } else {
            if (1 == biParams.getIsUser()) {
                List<String> userIds = Collections.emptyList();
                if (ObjectUtil.isNotEmpty(biParams.getUserIds())) {
                    userIds = Arrays.asList(biParams.getUserIds());
                }
                if (CollUtil.isEmpty(userIds)) {
                    userIds = new ArrayList<>();
                }
                if (StrUtil.isNotBlank(biParams.getUserId())) {
                    userIds.add(biParams.getUserId());
                }
                if (userIds.size() == 0) {
                    if (StrUtil.equals(sysUserDO.getDeptId(), "0")) {
                        userIdList.addAll(sysUserService.queryUserByDeptIds(
                                sysDeptService.queryChildDept("0")));
                    } else {
                        userIdList.addAll(sysUserService.queryChildUserId(
                                WebFrameworkUtils.getLoginUserId()));
                        userIdList.add(WebFrameworkUtils.getLoginUserId());
                    }
                } else {
                    if (StrUtil.equals(sysUserDO.getDeptId(), "0")) {
                        userIdList.addAll(userIds);
                    }
                }
            } else if (0 == biParams.getIsUser() && biParams.getDeptId() != null) {
                List<String> data = sysDeptService.queryChildDept(biParams.getDeptId());
                data.add(biParams.getDeptId());
                deptIdList.addAll(data);
                userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
            }
        }
        BiAuthority authority = new BiAuthority();
        authority.setUserIds(userIdList);
        authority.setDeptIds(deptIdList);
        return authority;
    }


    private List<String> getUserIds(BiParams biParams) {
        List<String> userIds = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(biParams.getUserIds()) || ObjectUtil.isNotEmpty(
                biParams.getDeptIds())) {
            if (ObjectUtil.isNotEmpty(biParams.getUserIds()) && CollUtil.isNotEmpty(
                    Arrays.asList(biParams.getUserIds()))) {
                userIds = CollUtil.unionAll(userIds, Arrays.asList(biParams.getUserIds()));
            }
            if (ObjectUtil.isNotEmpty(
                    biParams.getDeptIds()) && CollUtil.isNotEmpty(
                    Arrays.asList(biParams.getDeptIds()))) {
                List<String> list = sysUserService.queryUserByDeptIds(
                        Arrays.asList(biParams.getDeptIds()));
                if (CollUtil.isNotEmpty(list)) {
                    userIds = CollUtil.unionAll(userIds, list);
                }
            }
            if (CollUtil.isNotEmpty(userIds)) {
                userIds = userIds.stream().filter(Objects::nonNull).distinct()
                        .collect(Collectors.toList());
            }
        } else {
            BiAuthority biAuthority = handleDataType(biParams.setDataType(biParams.getDataType()));
            userIds = biAuthority.getUserIds();
        }

        return userIds;
    }


}
