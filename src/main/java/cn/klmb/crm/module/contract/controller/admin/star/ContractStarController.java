package cn.klmb.crm.module.contract.controller.admin.star;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.contract.controller.admin.star.vo.*;
import cn.klmb.crm.module.contract.convert.star.ContractStarConvert;
import cn.klmb.crm.module.contract.dto.star.ContractStarQueryDTO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import cn.klmb.crm.module.contract.service.star.ContractStarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
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
 * 合同标星关系 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "1203. 合同标星关系")
@RestController
@RequestMapping("/contract/star")
@Validated
public class ContractStarController {

    private final ContractStarService contractStarService;

    public ContractStarController(ContractStarService contractStarService) {
        this.contractStarService = contractStarService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('contract:star:save')")
    public CommonResult<String> save(@Valid @RequestBody ContractStarSaveReqVO saveReqVO) {
        ContractStarDO saveDO = ContractStarConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (contractStarService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{contractId}")
    @ApiOperation(value = "取消关注")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "contractId", value = "合同id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('contract:star:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String contractId) {
        contractStarService.cancelStar(contractId);
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('contract:star:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ContractStarUpdateReqVO updateReqVO) {
        ContractStarDO updateDO = ContractStarConvert.INSTANCE.convert(updateReqVO);
        contractStarService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('contract:star:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        contractStarService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('contract:star:query')")
    public CommonResult<ContractStarRespVO> getByBizId(@PathVariable String bizId) {
        ContractStarDO saveDO = contractStarService.getByBizId(bizId);
        return success(ContractStarConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('contract:star:query')")
    public CommonResult<KlmbPage<ContractStarRespVO>> page(@Valid ContractStarPageReqVO reqVO) {
        KlmbPage<ContractStarDO> klmbPage = KlmbPage.<ContractStarDO>builder()
              .pageNo(reqVO.getPageNo())
              .pageSize(reqVO.getPageSize())
              .build();
        ContractStarQueryDTO queryDTO = ContractStarConvert.INSTANCE.convert(reqVO);
        KlmbPage<ContractStarDO> page = contractStarService.page(queryDTO, klmbPage);
        return success(ContractStarConvert.INSTANCE.convert(page));
    }

}
