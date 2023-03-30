package cn.klmb.crm.module.member.service.contacts;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberFirstContactsReqVO;
import cn.klmb.crm.module.member.convert.contacts.MemberContactsConvert;
import cn.klmb.crm.module.member.dao.contacts.MemberContactsMapper;
import cn.klmb.crm.module.member.dto.contacts.MemberContactsQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 * 联系人 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberContactsServiceImpl extends
        KlmbBaseServiceImpl<MemberContactsDO, MemberContactsQueryDTO, MemberContactsMapper> implements
        MemberContactsService {

    private final SysUserService sysUserService;

    private final MemberUserService memberUserService;

    public MemberContactsServiceImpl(MemberContactsMapper mapper, SysUserService sysUserService,
            MemberUserService memberUserService) {
        this.sysUserService = sysUserService;
        this.memberUserService = memberUserService;
        this.mapper = mapper;
    }

    @Override
    public KlmbPage<MemberContactsDO> page(MemberContactsPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        List<String> childUserIds = sysUserService.queryChildUserId(
                userId);
        KlmbPage<MemberContactsDO> klmbPage = KlmbPage.<MemberContactsDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .sortingFields(reqVO.getSortingFields())
                .build();
        MemberContactsQueryDTO queryDTO = MemberContactsConvert.INSTANCE.convert(reqVO);
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.CHILD.getType())) {
            if (CollUtil.isEmpty(childUserIds)) {
                klmbPage.setContent(Collections.EMPTY_LIST);
                return klmbPage;
            } else {
                queryDTO.setOwnerUserIds(childUserIds);
                KlmbPage<MemberContactsDO> page = super.page(queryDTO, klmbPage);
                return page;
            }
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.SELF.getType())) {
            queryDTO.setOwnerUserId(userId);
            KlmbPage<MemberContactsDO> page = super.page(queryDTO, klmbPage);
            return page;
        }
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            childUserIds.add(userId);
            queryDTO.setOwnerUserIds(childUserIds);
            KlmbPage<MemberContactsDO> page = super.page(queryDTO, klmbPage);
            return page;
        }
//        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
//            List<MemberUserStarDO> memberUserStarDOS = memberUserStarService.list(
//                    new LambdaQueryWrapper<MemberUserStarDO>().eq(MemberUserStarDO::getUserId,
//                            userId));
//            if (CollUtil.isNotEmpty(memberUserStarDOS)) {
//                List<String> customerIds = memberUserStarDOS.stream()
//                        .map(MemberUserStarDO::getCustomerId)
//                        .collect(Collectors.toList());
//                queryDTO.setBizIds(customerIds);
//                KlmbPage<MemberUserDO> page = super.page(queryDTO, klmbPage);
//                return page;
//            } else {
//                klmbPage.setContent(Collections.EMPTY_LIST);
//                return klmbPage;
//            }
//        }
        klmbPage.setContent(Collections.EMPTY_LIST);
        return klmbPage;
    }

    @Override
    public void setContacts(MemberFirstContactsReqVO reqVO) {
        memberUserService.update(
                new LambdaUpdateWrapper<MemberUserDO>().set(MemberUserDO::getContactsId,
                                reqVO.getContactsId())
                        .eq(MemberUserDO::getBizId, reqVO.getCustomerId()));
    }

}
