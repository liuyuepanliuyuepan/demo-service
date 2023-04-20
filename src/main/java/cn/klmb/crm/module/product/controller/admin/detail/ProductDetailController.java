package cn.klmb.crm.module.product.controller.admin.detail;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailPageReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailSaveReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailUpdateReqVO;
import cn.klmb.crm.module.product.convert.detail.ProductDetailConvert;
import cn.klmb.crm.module.product.dto.detail.ProductDetailQueryDTO;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;
import cn.klmb.crm.module.product.service.detail.ProductDetailService;
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
 * 产品 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0701. 产品")
@RestController
@RequestMapping("/product/detail")
@Validated
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('product:detail:save')")
    public CommonResult<String> save(@Valid @RequestBody ProductDetailSaveReqVO saveReqVO) {
        ProductDetailDO saveDO = ProductDetailConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (productDetailService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('product:detail:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        productDetailService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('product:detail:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ProductDetailUpdateReqVO updateReqVO) {
        ProductDetailDO updateDO = ProductDetailConvert.INSTANCE.convert(updateReqVO);
        productDetailService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('product:detail:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        productDetailService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('product:detail:query')")
    public CommonResult<ProductDetailRespVO> getByBizId(@PathVariable String bizId) {
        ProductDetailDO saveDO = productDetailService.getByBizId(bizId);
        return success(ProductDetailConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('product:detail:query')")
    public CommonResult<KlmbPage<ProductDetailRespVO>> page(@Valid ProductDetailPageReqVO reqVO) {
        KlmbPage<ProductDetailDO> klmbPage = KlmbPage.<ProductDetailDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        ProductDetailQueryDTO queryDTO = ProductDetailConvert.INSTANCE.convert(reqVO);
        KlmbPage<ProductDetailDO> page = productDetailService.page(queryDTO, klmbPage);
        return success(ProductDetailConvert.INSTANCE.convert(page));
    }

}
