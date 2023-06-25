package cn.klmb.demo.module.system.controller.admin.dict;

import static cn.klmb.demo.framework.common.pojo.CommonResult.success;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.module.system.controller.admin.dict.vo.data.SysDictDataPageReqVO;
import cn.klmb.demo.module.system.controller.admin.dict.vo.data.SysDictDataRespVO;
import cn.klmb.demo.module.system.controller.admin.dict.vo.data.SysDictDataSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.dict.vo.data.SysDictDataSimpleRespVO;
import cn.klmb.demo.module.system.controller.admin.dict.vo.data.SysDictDataUpdateReqVO;
import cn.klmb.demo.module.system.convert.dict.SysDictDataConvert;
import cn.klmb.demo.module.system.dto.dict.SysDictDataQueryDTO;
import cn.klmb.demo.module.system.entity.dict.SysDictDataDO;
import cn.klmb.demo.module.system.service.dict.SysDictDataService;
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
 * 系统管理 - 字典数据
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
@Api(tags = "0107.系统管理-字典数据")
@RestController
@RequestMapping("/sys/dict-data")
@Validated
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    public SysDictDataController(SysDictDataService sysDictDataService) {
        this.sysDictDataService = sysDictDataService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:dict:save')")
    public CommonResult<String> save(@Valid @RequestBody SysDictDataSaveReqVO saveReqVO) {
        SysDictDataDO saveDO = SysDictDataConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (sysDictDataService.saveDO(saveDO)) {
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
        sysDictDataService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysDictDataUpdateReqVO updateReqVO) {
        SysDictDataDO updateDO = SysDictDataConvert.INSTANCE.convert(updateReqVO);
        sysDictDataService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysDictDataService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<SysDictDataRespVO> getByBizId(@PathVariable String bizId) {
        SysDictDataDO saveDO = sysDictDataService.getByBizId(bizId);
        return success(SysDictDataConvert.INSTANCE.convert(saveDO));
    }

    // 无需添加权限认证，因为前端全局都需要
    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典数据列表", notes = "一般用于管理后台缓存字典数据在本地")
    public CommonResult<List<SysDictDataSimpleRespVO>> listAllSimple() {
        return success(SysDictDataConvert.INSTANCE.convert(sysDictDataService.list()));
    }

    @GetMapping("/list-by-type/{dictType}")
    @ApiOperation(value = "根据类型获取字典数据列表")
    @ApiImplicitParam(name = "dictType", value = "字典类型", dataTypeClass = String.class, paramType = "path")
    public CommonResult<List<SysDictDataSimpleRespVO>> listByType(@PathVariable String dictType) {
        return success(SysDictDataConvert.INSTANCE.convert(
                sysDictDataService.list(SysDictDataQueryDTO.builder().dictType(dictType).status(
                        CommonStatusEnum.ENABLE.getStatus()).build())));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<KlmbPage<SysDictDataRespVO>> page(@Valid SysDictDataPageReqVO reqVO) {
        KlmbPage<SysDictDataDO> klmbPage = KlmbPage.<SysDictDataDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysDictDataQueryDTO queryDTO = SysDictDataConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysDictDataDO> page = sysDictDataService.page(queryDTO, klmbPage);
        return success(SysDictDataConvert.INSTANCE.convert(page));
    }

}

