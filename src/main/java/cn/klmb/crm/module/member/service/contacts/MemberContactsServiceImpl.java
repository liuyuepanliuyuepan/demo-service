package cn.klmb.crm.module.member.service.contacts;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.job.dto.XxlJobChangeTaskDTO;
import cn.klmb.crm.framework.job.util.XxlJobApiUtils;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberFirstContactsReqVO;
import cn.klmb.crm.module.member.convert.contacts.MemberContactsConvert;
import cn.klmb.crm.module.member.dao.contacts.MemberContactsMapper;
import cn.klmb.crm.module.member.dto.contacts.MemberContactsQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.contactsstar.MemberContactsStarDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.contactsstar.MemberContactsStarService;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

    private final MemberContactsStarService memberContactsStarService;

    private final XxlJobApiUtils xxlJobApiUtils;

    public MemberContactsServiceImpl(MemberContactsMapper mapper, SysUserService sysUserService,
            MemberUserService memberUserService,
            MemberContactsStarService memberContactsStarService, XxlJobApiUtils xxlJobApiUtils) {
        this.sysUserService = sysUserService;
        this.memberUserService = memberUserService;
        this.memberContactsStarService = memberContactsStarService;
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.mapper = mapper;
    }


    @Override
    public String saveContacts(MemberContactsDO entity) {
        String bizId = "";
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        entity.setOwnerUserId(userId);
        if (super.saveDO(entity)) {
            bizId = entity.getBizId();
        }
        //查询该联系人是否在客户的首位联系人
        MemberUserDO memberUserDO = memberUserService.getOne(
                new LambdaQueryWrapper<MemberUserDO>().eq(MemberUserDO::getBizId,
                        entity.getCustomerId()).eq(MemberUserDO::getDeleted, false));
        if (ObjectUtil.isNotNull(memberUserDO) && StrUtil.isBlank(memberUserDO.getContactsId())) {
            MemberFirstContactsReqVO reqVO = new MemberFirstContactsReqVO();
            reqVO.setContactsId(bizId);
            reqVO.setCustomerId(entity.getCustomerId());
            this.setContacts(reqVO);
        }
        xxlJobApiUtils.changeTask(
                XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                        .executorHandler("customerContactReminderHandler").author("liuyuepan")
                        .ownerUserId(entity.getOwnerUserId())
                        .bizId(bizId).nextTime(entity.getNextTime()).name(entity.getName())
                        .operateType(1)
                        .messageType(CrmEnum.CONTACTS.getRemarks())
                        .contactsType(CrmEnum.CONTACTS.getType()).build());
        return bizId;
    }


    @Override
    public KlmbPage<MemberContactsDO> page(MemberContactsPageReqVO reqVO) {
        String userId = reqVO.getUserId();
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
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<MemberContactsStarDO> memberContactsStarDOS = memberContactsStarService.list(
                    new LambdaQueryWrapper<MemberContactsStarDO>().eq(
                            MemberContactsStarDO::getUserId,
                            userId).eq(MemberContactsStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(memberContactsStarDOS)) {
                List<String> contactsIds = memberContactsStarDOS.stream()
                        .map(MemberContactsStarDO::getContactsId)
                        .collect(Collectors.toList());
                queryDTO.setBizIds(contactsIds);
                KlmbPage<MemberContactsDO> page = super.page(queryDTO, klmbPage);
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
    public void setContacts(MemberFirstContactsReqVO reqVO) {
        memberUserService.update(
                new LambdaUpdateWrapper<MemberUserDO>().set(MemberUserDO::getContactsId,
                                reqVO.getContactsId())
                        .eq(MemberUserDO::getBizId, reqVO.getCustomerId()));
    }

    @Override
    public void star(String bizId) {
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        LambdaQueryWrapper<MemberContactsStarDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberContactsStarDO::getContactsId, bizId);
        wrapper.eq(MemberContactsStarDO::getUserId, userId);
        wrapper.eq(MemberContactsStarDO::getDeleted, false);
        MemberContactsStarDO star = memberContactsStarService.getOne(wrapper);
        if (star == null) {
            star = new MemberContactsStarDO();
            star.setContactsId(bizId);
            star.setUserId(userId);
            memberContactsStarService.save(star);
        } else {
            memberContactsStarService.removeById(star.getId());
        }
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(bizIds);
        //判断客户中是否存在删除的联系人
        List<MemberUserDO> memberUserDOS = memberUserService.list(
                new LambdaQueryWrapper<MemberUserDO>().eq(MemberUserDO::getDeleted, false)
                        .in(MemberUserDO::getContactsId, bizIds));
        if (CollUtil.isNotEmpty(memberUserDOS)) {
            memberUserDOS.forEach(e -> {
                e.setContactsId(null);
            });
            memberUserService.updateBatchById(memberUserDOS);
        }

        bizIds.forEach(e -> {
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .bizId(e).operateType(3)
                            .messageType(CrmEnum.CONTACTS.getRemarks())
                            .contactsType(CrmEnum.CONTACTS.getType()).build());
        });

    }


    @Override
    public boolean updateDO(MemberContactsDO entity) {
        MemberContactsDO memberContactsDO = super.getByBizId(entity.getBizId());
        LocalDateTime nextTime = memberContactsDO.getNextTime();
        boolean success = super.updateDO(entity);
        if (!nextTime.isEqual(entity.getNextTime())) {
            xxlJobApiUtils.changeTask(
                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm").title("crm执行器")
                            .executorHandler("customerContactReminderHandler").author("liuyuepan")
                            .ownerUserId(entity.getOwnerUserId())
                            .bizId(entity.getBizId()).nextTime(entity.getNextTime())
                            .name(entity.getName()).operateType(2)
                            .messageType(CrmEnum.CONTACTS.getRemarks())
                            .contactsType(CrmEnum.CONTACTS.getType()).build());
        }
        return success;
    }


}
