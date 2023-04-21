package cn.klmb.crm.module.contract.controller.admin.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailRespVO;
import cn.klmb.crm.module.business.service.detail.BusinessDetailService;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.*;
import cn.klmb.crm.module.contract.convert.detail.ContractDetailConvert;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.enums.ContractErrorCodeConstants;
import cn.klmb.crm.module.contract.service.detail.ContractDetailService;
import cn.klmb.crm.module.contract.service.product.ContractProductService;
import cn.klmb.crm.module.contract.service.star.ContractStarService;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MembersTeamSelectVO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.dto.user.SysUserQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 合同详情 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "1201. 合同详情")
@RestController
@RequestMapping("/contract/detail")
@Validated
public class ContractDetailController {

    private final ContractDetailService contractDetailService;
    private final ContractProductService contractProductService;
    private final BusinessDetailService businessDetailService;
    private final MemberUserService memberUserService;
    private final MemberContactsService memberContactsService;
    private final SysUserService sysUserService;
    private final ContractStarService contractStarService;
    private final SysDeptService sysDeptService;
    private final MemberTeamService memberTeamService;


    public ContractDetailController(ContractDetailService contractDetailService,
            ContractProductService contractProductService,
            BusinessDetailService businessDetailService, MemberUserService memberUserService,
            MemberContactsService memberContactsService, SysUserService sysUserService,
            ContractStarService contractStarService, SysDeptService sysDeptService,
            MemberTeamService memberTeamService) {
        this.contractDetailService = contractDetailService;
        this.contractProductService = contractProductService;
        this.businessDetailService = businessDetailService;
        this.memberUserService = memberUserService;
        this.memberContactsService = memberContactsService;
        this.sysUserService = sysUserService;
        this.contractStarService = contractStarService;
        this.sysDeptService = sysDeptService;
        this.memberTeamService = memberTeamService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('contract:detail:save')")
    public CommonResult<String> save(@Valid @RequestBody ContractDetailSaveReqVO saveReqVO) {
        ContractDetailDO saveDO = ContractDetailConvert.INSTANCE.convert(saveReqVO);
        saveDO = contractDetailService.saveDefinition(saveDO);
        // 保存对应产品信息
        contractProductService.saveDefinition(saveReqVO.getContractProductSaveReqVOList(),saveDO.getBizId());

        MemberTeamDO memberTeamDO = MemberTeamDO.builder()
                .power(3).type(CrmEnum.CONTRACT.getType()).typeId(saveDO.getBizId())
                .userId(saveDO.getOwnerUserId())
                .build();
        memberTeamService.saveDO(memberTeamDO);
        return success(saveDO.getBizId());
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('contract:detail:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        contractDetailService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('contract:detail:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ContractDetailUpdateReqVO updateReqVO) {
        ContractDetailDO updateDO = ContractDetailConvert.INSTANCE.convert(updateReqVO);
        contractDetailService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('contract:detail:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        contractDetailService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @PostMapping(value = "/changeOwnerUser")
    @ApiOperation(value = "修改合同负责人")
    @PreAuthorize("@ss.hasPermission('contract:detail:update')")
    public CommonResult<Boolean> changeOwnerUser(@Valid @RequestBody ContractChangeOwnerUserVO crmChangeOwnerUserVO) {
        contractDetailService.changeOwnerUser(crmChangeOwnerUserVO);
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('contract:detail:query')")
    public CommonResult<ContractDetailRespVO> getByBizId(@PathVariable String bizId) {
        ContractDetailDO saveDO = contractDetailService.getByBizId(bizId);
        return success(ContractDetailConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('contract:detail:query')")
    public CommonResult<KlmbPage<ContractDetailRespVO>> page(@Valid ContractDetailPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        reqVO.setUserId(userId);
        KlmbPage<ContractDetailDO> page = contractDetailService.pageDefinition(reqVO);
        List<ContractDetailDO> content = page.getContent();
        content.forEach(e->{
            // 商机名称
            BusinessDetailRespVO business = businessDetailService.getBusinessByBizId(
                    e.getBusinessId());
            if (ObjectUtil.isNotNull(business)) {
                e.setBusinessName(business.getBusinessName());
            }
            // 客户名称
            MemberUserDO memberUserDO = memberUserService.getByBizId(e.getMemberUserId());
            if (ObjectUtil.isNotNull(memberUserDO)) {
                e.setMemberUserName(memberUserDO.getName());
            }
            // 客户签约人名称(联系人的意思)
            MemberContactsDO memberContactsDO = memberContactsService.getByBizId(e.getContactsId());
            if (ObjectUtil.isNotNull(memberContactsDO)) {
                e.setContactsName(memberContactsDO.getName());
            }
            // 公司签约人名称
            String companyUserId = e.getCompanyUserId();
            List<SysUserDO> sysUserDOList = sysUserService.list(
                    SysUserQueryDTO.builder().bizIds(Arrays.asList(companyUserId.split(",")))
                            .build());
            if (ObjectUtil.isNotNull(sysUserDOList)) {
                e.setCompanyUserName(sysUserDOList.stream().map(SysUserDO::getNickname).collect(
                        Collectors.toList()).stream().collect(Collectors.joining(",")));
            }
            //  创始人名称
            SysUserDO userDO = sysUserService.getByBizId(e.getCreator());
            e.setCreatorName(userDO.getNickname());
            // 负责人所在团队
            SysUserDO owner = sysUserService.getByBizId(e.getOwnerUserId());
            SysDeptDO deptDO = sysDeptService.getByBizId(owner.getDeptId());
            e.setOwnerDeptName(deptDO.getName());

            // teamMemberIds   该合同瞎的请他团队成员
            CrmEnum crmEnum = CrmEnum.BUSINESS;
            List<MembersTeamSelectVO> members = memberTeamService.getMembers(crmEnum, e.getBizId(),e.getOwnerUserId());
            if (ObjectUtil.isNotNull(members)) {
                e.setTeamMemberIds(members.stream().map(MembersTeamSelectVO::getNickName).collect(
                        Collectors.toList()).stream().collect(Collectors.joining(",")));
            }
        });
        return success(ContractDetailConvert.INSTANCE.convert(page));
    }
    
    /*****************************************合同内的团队成员******************************************************/
    @GetMapping("/getMembers/{contractBizId}")
    @ApiOperation("获取团队成员")
    @PreAuthorize("@ss.hasPermission('contract:detail:query')")
    public CommonResult<List<MembersTeamSelectVO>> getMembers(
            @PathVariable("contractBizId") @ApiParam("合同id") String contractBizId) {
        CrmEnum crmEnum = CrmEnum.CONTRACT;
        ContractDetailDO contractDetailDO = contractDetailService.getByBizId(contractBizId);
        if (contractDetailDO == null) {
            throw exception(ContractErrorCodeConstants.CONTRACT_NOT_EXIST);
        }
        List<MembersTeamSelectVO> members = memberTeamService.getMembers(crmEnum, contractBizId,
                contractDetailDO.getOwnerUserId());
        return CommonResult.success(members);
    }

    @PostMapping("/addMembers")
    @ApiOperation("新增团队成员")
    @PreAuthorize("@ss.hasPermission('contract:detail:save')")
    public CommonResult<Boolean> addMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.addMember(CrmEnum.CONTRACT, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/updateMembers")
    @ApiOperation("编辑团队成员")
    @PreAuthorize("@ss.hasPermission('contract:detail:update')")
    public CommonResult<Boolean> updateMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.addMember(CrmEnum.CONTRACT, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/deleteMembers")
    @ApiOperation("删除团队成员")
    @PreAuthorize("@ss.hasPermission('contract:detail:delete')")
    public CommonResult<Boolean> deleteMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.deleteMember(CrmEnum.CONTRACT, memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/exitTeam/{contractId}")
    @ApiOperation("退出团队")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contractId", value = "合同id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('contract:detail:update')")
    public CommonResult<Boolean> exitTeam(@PathVariable("contractId") String contractId) {
        memberTeamService.exitTeam(CrmEnum.CONTRACT, contractId);
        return CommonResult.success(true);
    }
    
}
