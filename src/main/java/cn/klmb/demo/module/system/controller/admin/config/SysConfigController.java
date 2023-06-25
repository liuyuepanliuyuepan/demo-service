package cn.klmb.demo.module.system.controller.admin.config;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.demo.framework.common.pojo.CommonResult.success;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.CONFIG_GET_VALUE_ERROR_IF_INVISIBLE;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.module.system.controller.admin.config.vo.SysConfigPageReqVO;
import cn.klmb.demo.module.system.controller.admin.config.vo.SysConfigRespVO;
import cn.klmb.demo.module.system.controller.admin.config.vo.SysConfigSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.config.vo.SysConfigUpdateReqVO;
import cn.klmb.demo.module.system.convert.config.SysConfigConvert;
import cn.klmb.demo.module.system.dto.config.SysConfigQueryDTO;
import cn.klmb.demo.module.system.entity.config.SysConfigDO;
import cn.klmb.demo.module.system.service.config.SysConfigService;
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
 * 系统管理 - 配置管理
 *
 * @author liuyuepan
 * @date 2022/12/5
 */
@Api(tags = "0108.系统管理-配置管理")
@RestController
@RequestMapping("/sys/config")
@Validated
public class SysConfigController {

    private final SysConfigService sysConfigService;

    public SysConfigController(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:config:save')")
    public CommonResult<String> save(@Valid @RequestBody SysConfigSaveReqVO saveReqVO) {
        SysConfigDO saveDO = SysConfigConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (sysConfigService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:config:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysConfigService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:config:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysConfigUpdateReqVO updateReqVO) {
        SysConfigDO updateDO = SysConfigConvert.INSTANCE.convert(updateReqVO);
        sysConfigService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:config:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysConfigService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:config:query')")
    public CommonResult<SysConfigRespVO> getByBizId(@PathVariable String bizId) {
        SysConfigDO saveDO = sysConfigService.getByBizId(bizId);
        return success(SysConfigConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:config:query')")
    public CommonResult<KlmbPage<SysConfigRespVO>> page(@Valid SysConfigPageReqVO reqVO) {
        KlmbPage<SysConfigDO> klmbPage = KlmbPage.<SysConfigDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysConfigQueryDTO queryDTO = SysConfigConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysConfigDO> page = sysConfigService.page(queryDTO, klmbPage);
        return success(SysConfigConvert.INSTANCE.convert(page));
    }

    @GetMapping(value = "/get-value-by-key")
    @ApiOperation(value = "根据参数键名查询参数值", notes = "不可见的配置，不允许返回给前端")
    @ApiImplicitParam(name = "configKey", value = "参数键", required = true, example = "sys.msg.no_message_tel_list", dataTypeClass = String.class)
    public CommonResult<String> getConfigKey(@RequestParam String configKey) {
        SysConfigDO config = sysConfigService.getByConfigKey(configKey);
        if (config == null) {
            return null;
        }
        if (!config.getVisible()) {
            throw exception(CONFIG_GET_VALUE_ERROR_IF_INVISIBLE);
        }
        return success(config.getValue());
    }

}

