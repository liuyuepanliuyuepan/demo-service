package cn.klmb.crm.module.member.controller.admin.contacts;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsDeleteReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsRespVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsSimpleRespVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsUpdateReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberFirstContactsReqVO;
import cn.klmb.crm.module.member.convert.contacts.MemberContactsConvert;
import cn.klmb.crm.module.member.dto.contacts.MemberContactsQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.contactsstar.MemberContactsStarDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.contactsstar.MemberContactsStarService;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 联系人 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0204. 客户管理-联系人")
@RestController
@RequestMapping("/member/contacts")
@Validated
public class MemberContactsController {

    private final MemberContactsService memberContactsService;

    private final MemberUserService memberUserService;

    private final SysUserService sysUserService;

    private final MemberContactsStarService memberContactsStarService;


    public MemberContactsController(MemberContactsService memberContactsService,
            MemberUserService memberUserService, SysUserService sysUserService,
            MemberContactsStarService memberContactsStarService) {
        this.memberContactsService = memberContactsService;
        this.memberUserService = memberUserService;
        this.sysUserService = sysUserService;
        this.memberContactsStarService = memberContactsStarService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:contacts:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberContactsSaveReqVO saveReqVO) {
        MemberContactsDO saveDO = MemberContactsConvert.INSTANCE.convert(saveReqVO);
        return success(memberContactsService.saveContacts(saveReqVO.getBusinessId(), saveDO));
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:contacts:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberContactsService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:contacts:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody MemberContactsUpdateReqVO updateReqVO) {
        MemberContactsDO updateDO = MemberContactsConvert.INSTANCE.convert(updateReqVO);
        memberContactsService.updateDO(updateDO);
        return success(true);
    }


    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:contacts:query')")
    public CommonResult<MemberContactsRespVO> getByBizId(@PathVariable String bizId) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        MemberContactsDO saveDO = memberContactsService.getByBizId(bizId);
        MemberUserDO memberUserDO = memberUserService.getByBizId(saveDO.getCustomerId());
        if (ObjectUtil.isNotNull(memberUserDO)) {
            saveDO.setCustomerName(memberUserDO.getName());
        }
        if (StrUtil.isNotBlank(saveDO.getParentContactsId())) {
            MemberContactsDO memberContactsDO = memberContactsService.getByBizId(
                    saveDO.getParentContactsId());
            saveDO.setParentContactsName(memberContactsDO.getName());
        }

        SysUserDO sysUserDO = sysUserService.getByBizId(saveDO.getOwnerUserId());
        if (ObjectUtil.isNotNull(sysUserDO)) {
            saveDO.setOwnerUserName(sysUserDO.getNickname());
        }
        List<MemberContactsStarDO> starDOList = memberContactsStarService.list(
                new LambdaQueryWrapper<MemberContactsStarDO>().eq(
                                MemberContactsStarDO::getContactsId, bizId)
                        .eq(MemberContactsStarDO::getUserId, userId)
                        .eq(MemberContactsStarDO::getDeleted, false));
        saveDO.setStar(CollUtil.isNotEmpty(starDOList));
        return success(MemberContactsConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:contacts:query')")
    public CommonResult<KlmbPage<MemberContactsRespVO>> page(@Valid MemberContactsPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        reqVO.setUserId(userId);
        KlmbPage<MemberContactsDO> page = memberContactsService.page(reqVO);
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
                List<MemberContactsStarDO> starDOList = memberContactsStarService.list(
                        new LambdaQueryWrapper<MemberContactsStarDO>().eq(
                                        MemberContactsStarDO::getContactsId, e.getBizId())
                                .eq(MemberContactsStarDO::getUserId, userId)
                                .eq(MemberContactsStarDO::getDeleted, false));
                e.setStar(CollUtil.isNotEmpty(starDOList));

            });
        }
        return success(MemberContactsConvert.INSTANCE.convert(page));
    }

    @PostMapping(value = "/batch-delete")
    @ApiOperation(value = "批量删除联系人")
    @PreAuthorize("@ss.hasPermission('member:user:delete')")
    public CommonResult<Boolean> deleteByBizIds(@RequestBody MemberContactsDeleteReqVO reqVO) {
        memberContactsService.removeByBizIds(reqVO.getBizIds());
        return success(true);
    }

    @PostMapping("/setContacts")
    @ApiOperation(value = "设置首要联系人")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> setContacts(@RequestBody MemberFirstContactsReqVO reqVO) {
        memberContactsService.setContacts(reqVO);
        return CommonResult.success(true);
    }


    @PostMapping("/star/{bizId}")
    @ApiOperation("联系人标星")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> star(@PathVariable("bizId") String bizId) {
        memberContactsService.star(bizId);
        return success(true);
    }

    @GetMapping({"/list-all-simple"})
    @ApiOperation(value = "列表精简信息")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<List<MemberContactsSimpleRespVO>> listAllSimple(
            @Valid MemberContactsPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        MemberContactsQueryDTO queryDTO = MemberContactsConvert.INSTANCE.convert(reqVO);
        queryDTO.setOwnerUserId(userId);
        List<MemberContactsDO> entities = memberContactsService.list(queryDTO);
        return success(MemberContactsConvert.INSTANCE.convert01(entities));
    }


    @GetMapping({"/list"})
    @ApiOperation(value = "列表", notes = "此接口仅适用根据customerId查询联系人信息")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<List<MemberContactsRespVO>> list(
            @Valid MemberContactsPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        MemberContactsQueryDTO queryDTO = MemberContactsConvert.INSTANCE.convert(reqVO);
        List<MemberContactsDO> entities = memberContactsService.list(
                new LambdaQueryWrapper<MemberContactsDO>().eq(MemberContactsDO::getCustomerId,
                                queryDTO.getCustomerId()).eq(MemberContactsDO::getDeleted, false)
                        .orderByDesc(MemberContactsDO::getCreateTime));
        if (CollUtil.isNotEmpty(entities)) {
            entities.forEach(e -> {
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
        return success(MemberContactsConvert.INSTANCE.convert(entities));
    }


}
