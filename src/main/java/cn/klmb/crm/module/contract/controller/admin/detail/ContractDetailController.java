package cn.klmb.crm.module.contract.controller.admin.detail;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDeleteReqVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailFullRespVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailPageReqVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailRespVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailSaveReqVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailScrollPageReqVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailUpdateReqVO;
import cn.klmb.crm.module.contract.convert.detail.ContractDetailConvert;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.service.detail.ContractDetailService;
import cn.klmb.crm.module.contract.service.product.ContractProductService;
import cn.klmb.crm.module.member.controller.admin.user.vo.CrmChangeOwnerUserBO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import cn.klmb.crm.module.system.enums.CrmEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
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
    private final MemberTeamService memberTeamService;


    public ContractDetailController(ContractDetailService contractDetailService,
            ContractProductService contractProductService, MemberTeamService memberTeamService) {
        this.contractDetailService = contractDetailService;
        this.contractProductService = contractProductService;
        this.memberTeamService = memberTeamService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('contract:detail:save')")
    public CommonResult<String> save(@Valid @RequestBody ContractDetailSaveReqVO saveReqVO) {
        ContractDetailDO saveDO = ContractDetailConvert.INSTANCE.convert(saveReqVO);
        saveDO = contractDetailService.saveDefinition(saveDO);
        // 保存对应产品信息
        contractProductService.saveDefinition(saveReqVO.getContractProductSaveReqVOList(),
                saveDO.getBizId());

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

    @PostMapping(value = "/batch-delete")
    @ApiOperation(value = "批量删除")
    @PreAuthorize("@ss.hasPermission('contract:detail:post')")
    public CommonResult<Boolean> deleteByBizId(@Valid @RequestBody ContractDeleteReqVO reqVO) {
        contractDetailService.removeByBizIds(reqVO.getBizIds());
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

    @PostMapping("/change-owner-user")
    @ApiOperation("修改合同负责人")
    @PreAuthorize("@ss.hasPermission('contract:detail:post')")
    public CommonResult<Boolean> changeOwnerUser(
            @RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
        contractDetailService.changeOwnerUser(crmChangeOwnerUserBO);
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('contract:detail:query')")
    public CommonResult<ContractDetailRespVO> getByBizId(@PathVariable String bizId) {
        return success(contractDetailService.findDetailByBizId(bizId));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('contract:detail:query')")
    public CommonResult<ContractDetailFullRespVO> page(@Valid ContractDetailPageReqVO reqVO) {
        ContractDetailFullRespVO page = contractDetailService.page(reqVO);
        return success(page);
    }


    @PostMapping("/star/{bizId}")
    @ApiOperation("合同标星")
    @PreAuthorize("@ss.hasPermission('contract:detail:post')")
    public CommonResult<Boolean> star(@PathVariable("bizId") String bizId) {
        contractDetailService.star(bizId);
        return success(true);
    }


    @GetMapping({"/page-scroll"})
    @ApiOperation(value = "合同滚动分页", notes = "只支持根据bizId顺序进行正、倒序查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastBizId", value = "业务id", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页数量，默认10", paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "asc", value = "是否为正序", paramType = "query", dataTypeClass = Boolean.class)})
    @PreAuthorize("@ss.hasPermission('contract:detail:query')")
    public CommonResult<KlmbScrollPage<ContractDetailRespVO>> pageScroll(
            @Valid ContractDetailScrollPageReqVO reqVO) {
        return success(contractDetailService.pageScroll(reqVO));
    }


}
