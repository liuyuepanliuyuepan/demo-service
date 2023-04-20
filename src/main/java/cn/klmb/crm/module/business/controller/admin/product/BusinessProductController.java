package cn.klmb.crm.module.business.controller.admin.product;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductPageReqVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductRespVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductUpdateReqVO;
import cn.klmb.crm.module.business.convert.product.BusinessProductConvert;
import cn.klmb.crm.module.business.dto.product.BusinessProductQueryDTO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import cn.klmb.crm.module.business.service.product.BusinessProductService;
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
 * 商机产品关系 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0602. 商机产品关系")
@RestController
@RequestMapping("/business/product")
@Validated
public class BusinessProductController {

    private final BusinessProductService businessProductService;

    public BusinessProductController(BusinessProductService businessProductService) {
        this.businessProductService = businessProductService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('business:product:save')")
    public CommonResult<String> save(@Valid @RequestBody BusinessProductSaveReqVO saveReqVO) {
        BusinessProductDO saveDO = BusinessProductConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (businessProductService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('business:product:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        businessProductService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('business:product:update')")
    public CommonResult<Boolean> update(
            @Valid @RequestBody BusinessProductUpdateReqVO updateReqVO) {
        BusinessProductDO updateDO = BusinessProductConvert.INSTANCE.convert(updateReqVO);
        businessProductService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('business:product:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        businessProductService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('business:product:query')")
    public CommonResult<BusinessProductRespVO> getByBizId(@PathVariable String bizId) {
        BusinessProductDO saveDO = businessProductService.getByBizId(bizId);
        return success(BusinessProductConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('business:product:query')")
    public CommonResult<KlmbPage<BusinessProductRespVO>> page(
            @Valid BusinessProductPageReqVO reqVO) {
        KlmbPage<BusinessProductDO> klmbPage = KlmbPage.<BusinessProductDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        BusinessProductQueryDTO queryDTO = BusinessProductConvert.INSTANCE.convert(reqVO);
        KlmbPage<BusinessProductDO> page = businessProductService.page(queryDTO, klmbPage);
        return success(BusinessProductConvert.INSTANCE.convert(page));
    }

}
