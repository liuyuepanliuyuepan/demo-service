package cn.klmb.crm.module.system.controller.admin.dict;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypePageReqVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypeRespVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypeSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypeSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.dict.vo.type.SysDictTypeUpdateReqVO;
import cn.klmb.crm.module.system.convert.dict.SysDictTypeConvert;
import cn.klmb.crm.module.system.dto.dict.SysDictTypeQueryDTO;
import cn.klmb.crm.module.system.entity.dict.SysDictTypeDO;
import cn.klmb.crm.module.system.service.dict.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
 * 系统管理 - 字典类型
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@Api(tags = "0106.系统管理-字典类型")
@RestController
@RequestMapping("/sys/dict-type")
@Validated
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    public SysDictTypeController(SysDictTypeService sysDictTypeService) {
        this.sysDictTypeService = sysDictTypeService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:dict:save')")
    public CommonResult<String> save(@Valid @RequestBody SysDictTypeSaveReqVO saveReqVO) {
        SysDictTypeDO saveDO = SysDictTypeConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (sysDictTypeService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysDictTypeService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysDictTypeUpdateReqVO updateReqVO) {
        SysDictTypeDO updateDO = SysDictTypeConvert.INSTANCE.convert(updateReqVO);
        sysDictTypeService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysDictTypeService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<SysDictTypeRespVO> getByBizId(@PathVariable String bizId) {
        SysDictTypeDO saveDO = sysDictTypeService.getByBizId(bizId);
        return success(SysDictTypeConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典类型列表", notes = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
    public CommonResult<List<SysDictTypeSimpleRespVO>> listAllSimple() {
        return success(SysDictTypeConvert.INSTANCE.convert(sysDictTypeService.list()));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<KlmbPage<SysDictTypeRespVO>> page(@Valid SysDictTypePageReqVO reqVO) {
        KlmbPage<SysDictTypeDO> klmbPage = KlmbPage.<SysDictTypeDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysDictTypeQueryDTO queryDTO = SysDictTypeConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysDictTypeDO> page = sysDictTypeService.page(queryDTO, klmbPage);
        return success(SysDictTypeConvert.INSTANCE.convert(page));
    }

}

