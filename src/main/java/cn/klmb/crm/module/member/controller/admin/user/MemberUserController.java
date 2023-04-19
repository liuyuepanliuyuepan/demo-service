package cn.klmb.crm.module.member.controller.admin.user;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MembersTeamSelectVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserBatchUpdateReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserDeleteReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPoolBO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserRespVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserScrollPageReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserSimpleRespVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import cn.klmb.crm.module.member.convert.user.MemberUserConvert;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.entity.userpoolrelation.MemberUserPoolRelationDO;
import cn.klmb.crm.module.member.entity.userstar.MemberUserStarDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.member.service.userpoolrelation.MemberUserPoolRelationService;
import cn.klmb.crm.module.member.service.userstar.MemberUserStarService;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 客户-用户 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0203. 客户管理-客户")
@RestController
@RequestMapping("/member/user")
@Validated
public class MemberUserController {

    private final MemberUserService memberUserService;

    private final MemberContactsService memberContactsService;

    private final SysUserService sysUserService;

    private final MemberUserStarService memberUserStarService;

    private final MemberTeamService memberTeamService;

    private final MemberUserPoolRelationService relationService;

    public MemberUserController(MemberUserService memberUserService,
            MemberContactsService memberContactsService, SysUserService sysUserService,
            MemberUserStarService memberUserStarService, MemberTeamService memberTeamService,
            MemberUserPoolRelationService relationService) {
        this.memberUserService = memberUserService;
        this.memberContactsService = memberContactsService;
        this.sysUserService = sysUserService;
        this.memberUserStarService = memberUserStarService;
        this.memberTeamService = memberTeamService;
        this.relationService = relationService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:user:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberUserSaveReqVO saveReqVO) {
        MemberUserDO saveDO = MemberUserConvert.INSTANCE.convert(saveReqVO);
        return success(memberUserService.saveCustomer(saveDO));
    }


    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberUserService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }


    @PostMapping(value = "/batch-delete")
    @ApiOperation(value = "批量删除客户")
    @PreAuthorize("@ss.hasPermission('member:user:delete')")
    public CommonResult<Boolean> deleteByBizIds(@RequestBody MemberUserDeleteReqVO reqVO) {
        memberUserService.removeByBizIds(reqVO.getBizIds());
        return success(true);
    }

    @PostMapping("/setDealStatus")
    @ApiOperation("修改客户成交状态")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> setDealStatus(
            @RequestBody MemberUserBatchUpdateReqVO updateReqVO) {
        memberUserService.setDealStatus(updateReqVO.getDealStatus(), updateReqVO.getBizIds());
        return success(true);
    }


    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:user:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody MemberUserUpdateReqVO updateReqVO) {
        MemberUserDO updateDO = MemberUserConvert.INSTANCE.convert(updateReqVO);
        memberUserService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:user:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberUserService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<MemberUserRespVO> getByBizId(@PathVariable String bizId) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        MemberUserDO saveDO = memberUserService.getByBizId(bizId);
        MemberContactsDO memberContactsDO = memberContactsService.getByBizId(
                saveDO.getContactsId());
        if (ObjectUtil.isNotNull(memberContactsDO)) {
            saveDO.setContactsName(memberContactsDO.getName());
            saveDO.setContactsMobile(memberContactsDO.getMobile());
        }
        SysUserDO sysUserDO = sysUserService.getByBizId(saveDO.getOwnerUserId());
        if (ObjectUtil.isNotNull(sysUserDO)) {
            saveDO.setOwnerUserName(sysUserDO.getNickname());
        }
        List<MemberUserStarDO> starDOList = memberUserStarService.list(
                new LambdaQueryWrapper<MemberUserStarDO>().eq(
                                MemberUserStarDO::getCustomerId, bizId)
                        .eq(MemberUserStarDO::getUserId, userId)
                        .eq(MemberUserStarDO::getDeleted, false));
        saveDO.setStar(CollUtil.isNotEmpty(starDOList));
        MemberUserRespVO convert = MemberUserConvert.INSTANCE.convert(saveDO);
        if (ObjectUtil.isNotNull(convert)) {
            List<MemberUserPoolRelationDO> list = relationService.list(
                    new LambdaQueryWrapper<MemberUserPoolRelationDO>().eq(
                                    MemberUserPoolRelationDO::getCustomerId, bizId)
                            .eq(MemberUserPoolRelationDO::getDeleted, false));
            convert.setExistPool(CollUtil.isNotEmpty(list));
        }
        return success(convert);
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<KlmbPage<MemberUserRespVO>> page(@Valid MemberUserPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        reqVO.setUserId(userId);
        KlmbPage<MemberUserDO> page = memberUserService.page(reqVO);
        List<MemberUserDO> content = page.getContent();
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                MemberContactsDO memberContactsDO = memberContactsService.getByBizId(
                        e.getContactsId());
                if (ObjectUtil.isNotNull(memberContactsDO)) {
                    e.setContactsName(memberContactsDO.getName());
                    e.setContactsMobile(memberContactsDO.getMobile());
                }
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                if (ObjectUtil.isNotNull(sysUserDO)) {
                    e.setOwnerUserName(sysUserDO.getNickname());
                }
                List<MemberUserStarDO> starDOList = memberUserStarService.list(
                        new LambdaQueryWrapper<MemberUserStarDO>().eq(
                                        MemberUserStarDO::getCustomerId, e.getBizId())
                                .eq(MemberUserStarDO::getUserId, userId)
                                .eq(MemberUserStarDO::getDeleted, false));
                e.setStar(CollUtil.isNotEmpty(starDOList));
            });
        }
        return success(MemberUserConvert.INSTANCE.convert(page));
    }

    @GetMapping({"/page-scroll"})
    @ApiOperation(value = "滚动分页查询(客户)", notes = "只支持根据bizId顺序进行正、倒序查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastBizId", value = "业务id", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页数量，默认10", paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "asc", value = "是否为正序", paramType = "query", dataTypeClass = Boolean.class)})
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<KlmbScrollPage<MemberUserRespVO>> pageScroll(
            @Valid MemberUserScrollPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        reqVO.setUserId(userId);
        KlmbScrollPage<MemberUserDO> page = memberUserService.pageScroll(reqVO);
        KlmbScrollPage<MemberUserRespVO> respPage = new KlmbScrollPage<>();
        respPage = MemberUserConvert.INSTANCE.convert(page);
        List<MemberUserRespVO> content = respPage.getContent();
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                MemberContactsDO memberContactsDO = memberContactsService.getByBizId(
                        e.getContactsId());
                if (ObjectUtil.isNotNull(memberContactsDO)) {
                    e.setContactsName(memberContactsDO.getName());
                    e.setContactsMobile(memberContactsDO.getMobile());
                }
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                if (ObjectUtil.isNotNull(sysUserDO)) {
                    e.setOwnerUserName(sysUserDO.getNickname());
                }
                List<MemberUserStarDO> starDOList = memberUserStarService.list(
                        new LambdaQueryWrapper<MemberUserStarDO>().eq(
                                        MemberUserStarDO::getCustomerId, e.getBizId())
                                .eq(MemberUserStarDO::getUserId, userId)
                                .eq(MemberUserStarDO::getDeleted, false));
                e.setStar(CollUtil.isNotEmpty(starDOList));
            });
        }
        return success(respPage);
    }

    @PostMapping("/star/{bizId}")
    @ApiOperation("客户标星")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> star(@PathVariable("bizId") String bizId) {
        memberUserService.star(bizId);
        return success(true);
    }

    @GetMapping({"/list-all-simple"})
    @ApiOperation(value = "列表精简信息")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<List<MemberUserSimpleRespVO>> listAllSimple(
            @Valid MemberUserPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        MemberUserQueryDTO queryDTO = MemberUserConvert.INSTANCE.convert(reqVO);
        queryDTO.setOwnerUserId(userId);
        List<MemberUserDO> entities = memberUserService.list(queryDTO);
        return success(MemberUserConvert.INSTANCE.convert01(entities));
    }


    @GetMapping("/getMembers/{customerId}")
    @ApiOperation("获取团队成员")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<List<MembersTeamSelectVO>> getMembers(
            @PathVariable("customerId") @ApiParam("客户ID") String customerId) {
        CrmEnum crmEnum = CrmEnum.CUSTOMER;
        List<MembersTeamSelectVO> members = memberTeamService.getMembers(crmEnum, customerId);
        return CommonResult.success(members);
    }

    @PostMapping("/addMembers")
    @ApiOperation("新增团队成员")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> addMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.addMember(CrmEnum.CUSTOMER, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/updateMembers")
    @ApiOperation("编辑团队成员")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> updateMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.addMember(CrmEnum.CUSTOMER, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/deleteMembers")
    @ApiOperation("删除团队成员")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> deleteMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.deleteMember(CrmEnum.CUSTOMER, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/exitTeam/{customerId}")
    @ApiOperation("退出团队")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> exitTeam(@PathVariable("customerId") String customerId) {
        memberTeamService.exitTeam(CrmEnum.CUSTOMER, customerId);
        return CommonResult.success(true);
    }

	@GetMapping("/nearbyMember")
	@ApiOperation("附近的客户")
	@PreAuthorize("@ss.hasPermission('member:user:query')")
	public CommonResult<List<MemberUserDO>> nearbyCustomer(@RequestParam("lng") String lng,
		@RequestParam("lat") String lat,
        @RequestParam(value = "type", required = false) Integer type,
        @RequestParam("radius") Integer radius,
		@RequestParam(value = "ownerUserId", required = false) String ownerUserId) {
		return CommonResult
			.success(memberUserService.nearbyMember(lng, lat, type, radius, ownerUserId));
	}

    @PostMapping("/add_pool")
    @ApiOperation("客户放入公海")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> updateCustomerByIds(@RequestBody MemberUserPoolBO poolBO) {
        memberUserService.addPool(poolBO);
        return CommonResult.success(true);
    }


    @GetMapping({"/page_pool"})
    @ApiOperation(value = "公海客户分页查询")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<KlmbPage<MemberUserRespVO>> pagePool(
            @Valid MemberUserPageReqVO reqVO) {
        reqVO.setPoolId("0");
        KlmbPage<MemberUserDO> klmbPage = KlmbPage.<MemberUserDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        MemberUserQueryDTO convert = MemberUserConvert.INSTANCE.convert(
                reqVO);
        List<MemberUserPoolRelationDO> relationDOS = relationService.list(
                new LambdaQueryWrapper<MemberUserPoolRelationDO>().eq(
                                MemberUserPoolRelationDO::getPoolId, reqVO.getPoolId())
                        .eq(MemberUserPoolRelationDO::getDeleted, false));
        if (CollUtil.isNotEmpty(relationDOS)) {
            convert.setBizIds(
                    relationDOS.stream().map(MemberUserPoolRelationDO::getCustomerId).collect(
                            Collectors.toList()));
            klmbPage = memberUserService.page(convert,
                    klmbPage);
        } else {
            klmbPage.setContent(Collections.EMPTY_LIST);
        }
        return success(MemberUserConvert.INSTANCE.convert(klmbPage));
    }

    @GetMapping({"/page-scroll-pool"})
    @ApiOperation(value = "滚动分页查询(公海客户)", notes = "只支持根据bizId顺序进行正、倒序查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastBizId", value = "业务id", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页数量，默认10", paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "asc", value = "是否为正序", paramType = "query", dataTypeClass = Boolean.class)})
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<KlmbScrollPage<MemberUserRespVO>> pageScrollPool(
            @Valid MemberUserScrollPageReqVO reqVO) {
        reqVO.setPoolId("0");
        KlmbScrollPage<MemberUserDO> klmbPage = KlmbScrollPage.<MemberUserDO>builder()
                .lastBizId(reqVO.getLastBizId())
                .pageSize(reqVO.getPageSize())
                .asc(reqVO.getAsc())
                .build();
        KlmbScrollPage<MemberUserRespVO> respPage = new KlmbScrollPage<>();
        MemberUserQueryDTO convert = MemberUserConvert.INSTANCE.convert(reqVO);
        List<MemberUserPoolRelationDO> relationDOS = relationService.list(
                new LambdaQueryWrapper<MemberUserPoolRelationDO>().eq(
                                MemberUserPoolRelationDO::getPoolId, reqVO.getPoolId())
                        .eq(MemberUserPoolRelationDO::getDeleted, false));
        if (CollUtil.isNotEmpty(relationDOS)) {
            convert.setBizIds(
                    relationDOS.stream().map(MemberUserPoolRelationDO::getCustomerId).collect(
                            Collectors.toList()));
            KlmbScrollPage<MemberUserDO> page = memberUserService.pageScroll(
                    convert, klmbPage);
            respPage = new KlmbScrollPage<>();
            respPage = MemberUserConvert.INSTANCE.convert(page);
            return success(respPage);
        } else {
            respPage.setContent(Collections.EMPTY_LIST);
        }
        return success(respPage);
    }


    @PostMapping("/distribute_or_receive")
    @ApiOperation("公海分配/领取客户")
    @PreAuthorize("@ss.hasPermission('member:user:post')")
    public CommonResult<Boolean> distributeOrReceive(@RequestBody MemberUserPoolBO poolBO) {
        memberUserService.getCustomersByIds(poolBO);
        return CommonResult.success(true);
    }


}
