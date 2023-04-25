package cn.klmb.crm.module.product.controller.admin.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.user.vo.CrmChangeOwnerUserBO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDeleteReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailPageReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailSaveReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailScrollPageReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailUpdateReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductStatusVO;
import cn.klmb.crm.module.product.convert.detail.ProductDetailConvert;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;
import cn.klmb.crm.module.product.enums.ShelfStatusEnum;
import cn.klmb.crm.module.product.service.detail.ProductDetailService;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
    @ApiOperation(value = "新增产品")
    @PreAuthorize("@ss.hasPermission('product:detail:save')")
    public CommonResult<String> save(@Valid @RequestBody ProductDetailSaveReqVO saveReqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        ProductDetailDO saveDO = ProductDetailConvert.INSTANCE.convert(saveReqVO);
        saveDO.setStatus(ShelfStatusEnum.ON_SHELF.getValue());
        String bizId = "";
        if (StrUtil.isBlank(saveDO.getOwnerUserId())) {
            saveDO.setOwnerUserId(userId);
        }
        if (productDetailService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @PostMapping(value = "/batch-delete")
    @ApiOperation(value = "批量删除产品")
    @PreAuthorize("@ss.hasPermission('product:detail:post')")
    public CommonResult<Boolean> batchDelete(@Valid @RequestBody ProductDeleteReqVO reqVO) {
        productDetailService.removeByBizIds(reqVO.getBizIds());
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新产品")
    @PreAuthorize("@ss.hasPermission('product:detail:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ProductDetailUpdateReqVO updateReqVO) {
        ProductDetailDO updateDO = ProductDetailConvert.INSTANCE.convert(updateReqVO);
        productDetailService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改上下架状态")
    @PreAuthorize("@ss.hasPermission('product:detail:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody ProductStatusVO reqVO) {
        reqVO.getBizIds().forEach(e -> {
            productDetailService.updateStatus(e, reqVO.getStatus());
        });
        return success(true);
    }

    @GetMapping(value = "/detail_info/{bizId}")
    @ApiOperation(value = "产品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('product:detail:query')")
    public CommonResult<ProductDetailRespVO> getByBizId(@PathVariable String bizId) {
        return success(productDetailService.getProductDetailByBizId(bizId));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "产品分页查询")
    @PreAuthorize("@ss.hasPermission('product:detail:query')")
    public CommonResult<KlmbPage<ProductDetailRespVO>> page(@Valid ProductDetailPageReqVO reqVO) {
        return success(productDetailService.page(reqVO));
    }

    @PostMapping("/star/{bizId}")
    @ApiOperation("产品标星")
    @PreAuthorize("@ss.hasPermission('product:detail:update')")
    public CommonResult<Boolean> star(@PathVariable("bizId") String bizId) {
        productDetailService.star(bizId);
        return success(true);
    }

    @GetMapping({"/page-scroll"})
    @ApiOperation(value = "产品滚动分页", notes = "只支持根据bizId顺序进行正、倒序查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastBizId", value = "业务id", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页数量，默认10", paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "asc", value = "是否为正序", paramType = "query", dataTypeClass = Boolean.class)})
    @PreAuthorize("@ss.hasPermission('product:detail:query')")
    public CommonResult<KlmbScrollPage<ProductDetailRespVO>> pageScroll(
            @Valid ProductDetailScrollPageReqVO reqVO) {
        return success(productDetailService.pageScroll(reqVO));
    }


    @PostMapping("/change-owner-user")
    @ApiOperation("修改产品负责人")
    @PreAuthorize("@ss.hasPermission('product:detail:post')")
    public CommonResult<Boolean> changeOwnerUser(
            @RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
        productDetailService.changeOwnerUser(crmChangeOwnerUserBO);
        return success(true);
    }

}
