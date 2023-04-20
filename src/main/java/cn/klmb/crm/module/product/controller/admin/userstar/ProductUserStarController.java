package cn.klmb.crm.module.product.controller.admin.userstar;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.product.controller.admin.userstar.vo.ProductUserStarPageReqVO;
import cn.klmb.crm.module.product.controller.admin.userstar.vo.ProductUserStarRespVO;
import cn.klmb.crm.module.product.controller.admin.userstar.vo.ProductUserStarSaveReqVO;
import cn.klmb.crm.module.product.controller.admin.userstar.vo.ProductUserStarUpdateReqVO;
import cn.klmb.crm.module.product.convert.userstar.ProductUserStarConvert;
import cn.klmb.crm.module.product.dto.userstar.ProductUserStarQueryDTO;
import cn.klmb.crm.module.product.entity.userstar.ProductUserStarDO;
import cn.klmb.crm.module.product.service.userstar.ProductUserStarService;
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
 * 用户产品标星关系 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0703. 用户产品标星关系")
@RestController
@RequestMapping("/product/user-star")
@Validated
public class ProductUserStarController {

    private final ProductUserStarService productUserStarService;

    public ProductUserStarController(ProductUserStarService productUserStarService) {
        this.productUserStarService = productUserStarService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('product:user-star:save')")
    public CommonResult<String> save(@Valid @RequestBody ProductUserStarSaveReqVO saveReqVO) {
        ProductUserStarDO saveDO = ProductUserStarConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (productUserStarService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('product:user-star:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        productUserStarService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('product:user-star:update')")
    public CommonResult<Boolean> update(
            @Valid @RequestBody ProductUserStarUpdateReqVO updateReqVO) {
        ProductUserStarDO updateDO = ProductUserStarConvert.INSTANCE.convert(updateReqVO);
        productUserStarService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('product:user-star:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        productUserStarService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('product:user-star:query')")
    public CommonResult<ProductUserStarRespVO> getByBizId(@PathVariable String bizId) {
        ProductUserStarDO saveDO = productUserStarService.getByBizId(bizId);
        return success(ProductUserStarConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('product:user-star:query')")
    public CommonResult<KlmbPage<ProductUserStarRespVO>> page(
            @Valid ProductUserStarPageReqVO reqVO) {
        KlmbPage<ProductUserStarDO> klmbPage = KlmbPage.<ProductUserStarDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        ProductUserStarQueryDTO queryDTO = ProductUserStarConvert.INSTANCE.convert(reqVO);
        KlmbPage<ProductUserStarDO> page = productUserStarService.page(queryDTO, klmbPage);
        return success(ProductUserStarConvert.INSTANCE.convert(page));
    }

}
