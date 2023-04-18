package cn.klmb.crm.module.business.controller.admin.detail;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailRespVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailUpdateReqVO;
import cn.klmb.crm.module.business.convert.detail.BusinessDetailConvert;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.service.detail.BusinessDetailService;
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
 * 商机 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0601. 商机")
@RestController
@RequestMapping("/business/detail")
@Validated
public class BusinessDetailController {

    private final BusinessDetailService businessDetailService;

    public BusinessDetailController(BusinessDetailService businessDetailService) {
        this.businessDetailService = businessDetailService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('business:detail:save')")
    public CommonResult<String> save(@Valid @RequestBody BusinessDetailSaveReqVO saveReqVO) {
        BusinessDetailDO saveDO = BusinessDetailConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (businessDetailService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('business:detail:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        businessDetailService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('business:detail:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody BusinessDetailUpdateReqVO updateReqVO) {
        BusinessDetailDO updateDO = BusinessDetailConvert.INSTANCE.convert(updateReqVO);
        businessDetailService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('business:detail:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        businessDetailService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('business:detail:query')")
    public CommonResult<BusinessDetailRespVO> getByBizId(@PathVariable String bizId) {
        BusinessDetailDO saveDO = businessDetailService.getByBizId(bizId);
        return success(BusinessDetailConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('business:detail:query')")
    public CommonResult<KlmbPage<BusinessDetailRespVO>> page(@Valid BusinessDetailPageReqVO reqVO) {
        KlmbPage<BusinessDetailDO> klmbPage = KlmbPage.<BusinessDetailDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        BusinessDetailQueryDTO queryDTO = BusinessDetailConvert.INSTANCE.convert(reqVO);
        KlmbPage<BusinessDetailDO> page = businessDetailService.page(queryDTO, klmbPage);
        return success(BusinessDetailConvert.INSTANCE.convert(page));
    }

}
