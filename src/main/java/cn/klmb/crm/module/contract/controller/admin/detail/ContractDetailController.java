package cn.klmb.crm.module.contract.controller.admin.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.*;
import cn.klmb.crm.module.contract.convert.detail.ContractDetailConvert;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.service.detail.ContractDetailService;
import cn.klmb.crm.module.contract.service.product.ContractProductService;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
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

    public ContractDetailController(ContractDetailService contractDetailService,
            ContractProductService contractProductService) {
        this.contractDetailService = contractDetailService;
        this.contractProductService = contractProductService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('contract:detail:save')")
    public CommonResult<String> save(@Valid @RequestBody ContractDetailSaveReqVO saveReqVO) {
        ContractDetailDO saveDO = ContractDetailConvert.INSTANCE.convert(saveReqVO);
        String bizId = contractDetailService.saveDefinition(saveDO);
        // 保存对应产品信息
        contractProductService.saveDefinition(saveReqVO.getContractProductSaveReqVOList(),bizId);
        return success(bizId);
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
            // todo 在这里补充一些名称等
            // 客户名称  商机名称  客户签约人名称  公司签约人名称  创始人名称
            // 字典  合同类型
            // 负责人所在团队  该合同瞎的请他团队成员
        });
        return success(ContractDetailConvert.INSTANCE.convert(page));
    }

    // 团队列表
}
