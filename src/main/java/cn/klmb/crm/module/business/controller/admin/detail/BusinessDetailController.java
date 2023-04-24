package cn.klmb.crm.module.business.controller.admin.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.util.ObjectUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDeleteReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailRespVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailUpdateReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.CrmRelevanceBusinessBO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.UpdateBusinessStatusReqVO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.enums.ErrorCodeConstants;
import cn.klmb.crm.module.business.service.detail.BusinessDetailService;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsRespVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MembersTeamSelectVO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.system.enums.CrmEnum;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 商机 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0601. 商机")
@RestController
@RequestMapping("/business/detail")
@Validated
public class BusinessDetailController {

    private final BusinessDetailService businessDetailService;

    private final MemberTeamService memberTeamService;

    private final MemberContactsService memberContactsService;

    public BusinessDetailController(BusinessDetailService businessDetailService,
            MemberTeamService memberTeamService, MemberContactsService memberContactsService) {
        this.businessDetailService = businessDetailService;
        this.memberTeamService = memberTeamService;
        this.memberContactsService = memberContactsService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增商机")
    @PreAuthorize("@ss.hasPermission('business:detail:save')")
    public CommonResult<String> save(@Valid @RequestBody BusinessDetailSaveReqVO saveReqVO) {
        return success(businessDetailService.saveBusiness(saveReqVO));
    }

    @DeleteMapping(value = "/batch-delete")
    @ApiOperation(value = "批量删除")
    @PreAuthorize("@ss.hasPermission('business:detail:delete')")
    public CommonResult<Boolean> deleteByBizId(@RequestBody BusinessDeleteReqVO reqVO) {
        businessDetailService.removeByBizIds(reqVO.getBizIds());
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新商机")
    @PreAuthorize("@ss.hasPermission('business:detail:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody BusinessDetailUpdateReqVO updateReqVO) {
        businessDetailService.updateBusiness(updateReqVO);
        return success(true);
    }


    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "商机详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('business:detail:query')")
    public CommonResult<BusinessDetailRespVO> getByBizId(@PathVariable String bizId) {
        BusinessDetailRespVO respVO = businessDetailService.getBusinessByBizId(bizId);
        return success(respVO);
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "商机分页查询")
    @PreAuthorize("@ss.hasPermission('business:detail:query')")
    public CommonResult<KlmbPage<BusinessDetailRespVO>> page(@Valid BusinessDetailPageReqVO reqVO) {
        return success(businessDetailService.page(reqVO));
    }

    @PutMapping("/update-business-status")
    @ApiOperation("修改商机阶段")
    @PreAuthorize("@ss.hasPermission('business:detail:update')")
    public CommonResult<Boolean> updateBusinessStatus(
            @Valid @RequestBody UpdateBusinessStatusReqVO reqVO) {
        businessDetailService.update(
                new UpdateWrapper<BusinessDetailDO>().in("biz_id", reqVO.getBizIds())
                        .set("status", reqVO.getBusinessStatus()));
        return success(true);
    }

    @GetMapping("/getMembers/{businessId}")
    @ApiOperation("获取团队成员")
    @PreAuthorize("@ss.hasPermission('business:detail:query')")
    public CommonResult<List<MembersTeamSelectVO>> getMembers(
            @PathVariable("businessId") @ApiParam("商机ID") String businessId) {
        BusinessDetailDO businessDetailDO = businessDetailService.getByBizId(businessId);
        if (ObjectUtil.isNull(businessDetailDO)) {
            throw exception(ErrorCodeConstants.BUSINESS_NOT_EXISTS);
        }
        CrmEnum crmEnum = CrmEnum.BUSINESS;
        List<MembersTeamSelectVO> members = memberTeamService.getMembers(crmEnum, businessId,
                businessDetailDO.getOwnerUserId());
        return CommonResult.success(members);
    }

    @PostMapping("/addMembers")
    @ApiOperation("新增团队成员")
    @PreAuthorize("@ss.hasPermission('business:detail:post')")
    public CommonResult<Boolean> addMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.addMember(CrmEnum.BUSINESS, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/updateMembers")
    @ApiOperation("编辑团队成员")
    @PreAuthorize("@ss.hasPermission('business:detail:post')")
    public CommonResult<Boolean> updateMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.addMember(CrmEnum.BUSINESS, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/deleteMembers")
    @ApiOperation("删除团队成员")
    @PreAuthorize("@ss.hasPermission('business:detail:post')")
    public CommonResult<Boolean> deleteMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.deleteMember(CrmEnum.BUSINESS, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/exitTeam/{businessId}")
    @ApiOperation("退出团队")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessId", value = "商机id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('business:detail:post')")
    public CommonResult<Boolean> exitTeam(@PathVariable("businessId") String businessId) {
        memberTeamService.exitTeam(CrmEnum.BUSINESS, businessId);
        return CommonResult.success(true);
    }

    @PostMapping("/star/{bizId}")
    @ApiOperation("商机标星")
    @PreAuthorize("@ss.hasPermission('business:detail:post')")
    public CommonResult<Boolean> star(@PathVariable("bizId") String bizId) {
        businessDetailService.star(bizId);
        return success(true);
    }

    @PostMapping("/relate-contacts")
    @ApiOperation("商机关联联系人")
    @PreAuthorize("@ss.hasPermission('business:detail:post')")
    public CommonResult<Boolean> relateContacts(
            @RequestBody CrmRelevanceBusinessBO relevanceBusinessBO) {
        businessDetailService.relateContacts(relevanceBusinessBO);
        return CommonResult.success(true);
    }

    @PostMapping("/unrelate-contacts")
    @ApiOperation("解除关联联系人")
    @PreAuthorize("@ss.hasPermission('business:detail:post')")
    public CommonResult<Boolean> unrelateContacts(
            @RequestBody CrmRelevanceBusinessBO relevanceBusinessBO) {
        businessDetailService.unrelateContacts(relevanceBusinessBO);
        return CommonResult.success(true);
    }


    @GetMapping({"/page_contacts"})
    @ApiOperation(value = "根据商机信息查询联系人分页")
    @PreAuthorize("@ss.hasPermission('business:detail:query')")
    public CommonResult<KlmbPage<MemberContactsRespVO>> pageContacts(
            @Valid MemberContactsPageReqVO reqVO) {
        return success(businessDetailService.pageContacts(reqVO));
    }

//商机滚动分页

}
