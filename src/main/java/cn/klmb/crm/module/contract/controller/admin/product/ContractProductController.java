package cn.klmb.crm.module.contract.controller.admin.product;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.contract.controller.admin.product.vo.*;
import cn.klmb.crm.module.contract.convert.product.ContractProductConvert;
import cn.klmb.crm.module.contract.dto.product.ContractProductQueryDTO;
import cn.klmb.crm.module.contract.entity.product.ContractProductDO;
import cn.klmb.crm.module.contract.service.product.ContractProductService;
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
 * 合同产品关系 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "1202. 合同产品关系")
@RestController
@RequestMapping("/contract/product")
@Validated
public class ContractProductController {

    private final ContractProductService contractProductService;

    public ContractProductController(ContractProductService contractProductService) {
        this.contractProductService = contractProductService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('contract:product:save')")
    public CommonResult<String> save(@Valid @RequestBody ContractProductSaveReqVO saveReqVO) {
        ContractProductDO saveDO = ContractProductConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (contractProductService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('contract:product:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        contractProductService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('contract:product:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ContractProductUpdateReqVO updateReqVO) {
        ContractProductDO updateDO = ContractProductConvert.INSTANCE.convert(updateReqVO);
        contractProductService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('contract:product:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        contractProductService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('contract:product:query')")
    public CommonResult<ContractProductRespVO> getByBizId(@PathVariable String bizId) {
        ContractProductDO saveDO = contractProductService.getByBizId(bizId);
        return success(ContractProductConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('contract:product:query')")
    public CommonResult<KlmbPage<ContractProductRespVO>> page(@Valid ContractProductPageReqVO reqVO) {
        KlmbPage<ContractProductDO> klmbPage = KlmbPage.<ContractProductDO>builder()
              .pageNo(reqVO.getPageNo())
              .pageSize(reqVO.getPageSize())
              .build();
        ContractProductQueryDTO queryDTO = ContractProductConvert.INSTANCE.convert(reqVO);
        KlmbPage<ContractProductDO> page = contractProductService.page(queryDTO, klmbPage);
        return success(ContractProductConvert.INSTANCE.convert(page));
    }

}
