package cn.klmb.crm.module.business.controller.admin.userstar;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.business.controller.admin.userstar.vo.BusinessUserStarPageReqVO;
import cn.klmb.crm.module.business.controller.admin.userstar.vo.BusinessUserStarRespVO;
import cn.klmb.crm.module.business.controller.admin.userstar.vo.BusinessUserStarSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.userstar.vo.BusinessUserStarUpdateReqVO;
import cn.klmb.crm.module.business.convert.userstar.BusinessUserStarConvert;
import cn.klmb.crm.module.business.dto.userstar.BusinessUserStarQueryDTO;
import cn.klmb.crm.module.business.entity.userstar.BusinessUserStarDO;
import cn.klmb.crm.module.business.service.userstar.BusinessUserStarService;
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
 * 用户商机标星关系 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0603. 用户商机标星关系")
@RestController
@RequestMapping("/business/user-star")
@Validated
public class BusinessUserStarController {

    private final BusinessUserStarService businessUserStarService;

    public BusinessUserStarController(BusinessUserStarService businessUserStarService) {
        this.businessUserStarService = businessUserStarService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('business:user-star:save')")
    public CommonResult<String> save(@Valid @RequestBody BusinessUserStarSaveReqVO saveReqVO) {
        BusinessUserStarDO saveDO = BusinessUserStarConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (businessUserStarService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('business:user-star:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        businessUserStarService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('business:user-star:update')")
    public CommonResult<Boolean> update(
            @Valid @RequestBody BusinessUserStarUpdateReqVO updateReqVO) {
        BusinessUserStarDO updateDO = BusinessUserStarConvert.INSTANCE.convert(updateReqVO);
        businessUserStarService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('business:user-star:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        businessUserStarService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('business:user-star:query')")
    public CommonResult<BusinessUserStarRespVO> getByBizId(@PathVariable String bizId) {
        BusinessUserStarDO saveDO = businessUserStarService.getByBizId(bizId);
        return success(BusinessUserStarConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('business:user-star:query')")
    public CommonResult<KlmbPage<BusinessUserStarRespVO>> page(
            @Valid BusinessUserStarPageReqVO reqVO) {
        KlmbPage<BusinessUserStarDO> klmbPage = KlmbPage.<BusinessUserStarDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        BusinessUserStarQueryDTO queryDTO = BusinessUserStarConvert.INSTANCE.convert(reqVO);
        KlmbPage<BusinessUserStarDO> page = businessUserStarService.page(queryDTO, klmbPage);
        return success(BusinessUserStarConvert.INSTANCE.convert(page));
    }

}
