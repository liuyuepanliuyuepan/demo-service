package cn.klmb.crm.module.product.controller.admin.category;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.product.controller.admin.category.vo.CrmProductCategoryBO;
import cn.klmb.crm.module.product.controller.admin.category.vo.ProductCategoryPageReqVO;
import cn.klmb.crm.module.product.controller.admin.category.vo.ProductCategoryRespVO;
import cn.klmb.crm.module.product.controller.admin.category.vo.ProductCategorySaveReqVO;
import cn.klmb.crm.module.product.controller.admin.category.vo.ProductCategoryUpdateReqVO;
import cn.klmb.crm.module.product.convert.category.ProductCategoryConvert;
import cn.klmb.crm.module.product.dto.category.ProductCategoryQueryDTO;
import cn.klmb.crm.module.product.entity.category.ProductCategoryDO;
import cn.klmb.crm.module.product.service.category.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 产品分类 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0702. 产品分类")
@RestController
@RequestMapping("/product/category")
@Validated
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('product:category:save')")
    public CommonResult<String> save(@Valid @RequestBody ProductCategorySaveReqVO saveReqVO) {
        ProductCategoryDO saveDO = ProductCategoryConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (productCategoryService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('product:category:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        productCategoryService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('product:category:update')")
    public CommonResult<Boolean> update(
            @Valid @RequestBody ProductCategoryUpdateReqVO updateReqVO) {
        ProductCategoryDO updateDO = ProductCategoryConvert.INSTANCE.convert(updateReqVO);
        productCategoryService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('product:category:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        productCategoryService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('product:category:query')")
    public CommonResult<ProductCategoryRespVO> getByBizId(@PathVariable String bizId) {
        ProductCategoryDO saveDO = productCategoryService.getByBizId(bizId);
        return success(ProductCategoryConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('product:category:query')")
    public CommonResult<KlmbPage<ProductCategoryRespVO>> page(
            @Valid ProductCategoryPageReqVO reqVO) {
        KlmbPage<ProductCategoryDO> klmbPage = KlmbPage.<ProductCategoryDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        ProductCategoryQueryDTO queryDTO = ProductCategoryConvert.INSTANCE.convert(reqVO);
        KlmbPage<ProductCategoryDO> page = productCategoryService.page(queryDTO, klmbPage);
        return success(ProductCategoryConvert.INSTANCE.convert(page));
    }


    @GetMapping("/queryList")
    @ApiOperation("查询产品分类列表")
    @PreAuthorize("@ss.hasPermission('product:category:query')")
    public CommonResult<List<CrmProductCategoryBO>> queryList(@ApiParam("type") String type) {
        List<CrmProductCategoryBO> list = productCategoryService.queryList(type);
        return CommonResult.success(list);
    }


}
