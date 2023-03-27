package cn.klmb.crm.module.system.controller.admin.file;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.file.vo.config.SysFileConfigPageReqVO;
import cn.klmb.crm.module.system.controller.admin.file.vo.config.SysFileConfigRespVO;
import cn.klmb.crm.module.system.controller.admin.file.vo.config.SysFileConfigSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.file.vo.config.SysFileConfigUpdateReqVO;
import cn.klmb.crm.module.system.convert.file.SysFileConfigConvert;
import cn.klmb.crm.module.system.dto.file.SysFileConfigQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileConfigDO;
import cn.klmb.crm.module.system.service.file.SysFileConfigService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统管理 - 文件配置
 *
 * @author shilinchuan
 * @date 2022/12/5
 */
@Api(tags = "0109.系统管理-文件配置")
@RestController
@RequestMapping("/sys/file-config")
@Validated
public class SysFileConfigController {

    private final SysFileConfigService sysFileConfigService;

    public SysFileConfigController(SysFileConfigService sysFileConfigService) {
        this.sysFileConfigService = sysFileConfigService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:file-config:save')")
    public CommonResult<String> save(@Valid @RequestBody SysFileConfigSaveReqVO saveReqVO) {
        SysFileConfigDO saveDO = SysFileConfigConvert.INSTANCE.convert(saveReqVO)
                .setConfig(sysFileConfigService.parseClientConfig(saveReqVO.getStorage(),
                        saveReqVO.getConfig()));
        String bizId = "";
        if (sysFileConfigService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:file-config:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysFileConfigService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:file-config:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysFileConfigUpdateReqVO updateReqVO) {
        SysFileConfigDO updateDO = SysFileConfigConvert.INSTANCE.convert(updateReqVO)
                .setConfig(sysFileConfigService.parseClientConfig(updateReqVO.getStorage(),
                        updateReqVO.getConfig()));
        sysFileConfigService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:file-config:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysFileConfigService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @PutMapping("/update-master")
    @ApiOperation("更新文件配置为 Master")
    @PreAuthorize("@ss.hasPermission('system:file-config:update')")
    public CommonResult<Boolean> updateFileConfigMaster(@RequestParam("bizId") String bizId) {
        sysFileConfigService.updateFileConfigMaster(bizId);
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:file-config:query')")
    public CommonResult<SysFileConfigRespVO> getByBizId(@PathVariable String bizId) {
        SysFileConfigDO saveDO = sysFileConfigService.getByBizId(bizId);
        return success(SysFileConfigConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:file-config:query')")
    public CommonResult<KlmbPage<SysFileConfigRespVO>> page(@Valid SysFileConfigPageReqVO reqVO) {
        KlmbPage<SysFileConfigDO> klmbPage = KlmbPage.<SysFileConfigDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysFileConfigQueryDTO queryDTO = SysFileConfigConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysFileConfigDO> page = sysFileConfigService.page(queryDTO, klmbPage);
        return success(SysFileConfigConvert.INSTANCE.convert(page));
    }

}

