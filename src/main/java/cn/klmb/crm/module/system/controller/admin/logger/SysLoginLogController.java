package cn.klmb.crm.module.system.controller.admin.logger;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.util.BooleanUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.logger.vo.loginlog.SysLoginLogPageReqVO;
import cn.klmb.crm.module.system.controller.admin.logger.vo.loginlog.SysLoginLogRespVO;
import cn.klmb.crm.module.system.convert.logger.SysLoginLogConvert;
import cn.klmb.crm.module.system.dto.logger.SysLoginLogQueryDTO;
import cn.klmb.crm.module.system.entity.logger.SysLoginLogDO;
import cn.klmb.crm.module.system.enums.logger.LoginResultEnum;
import cn.klmb.crm.module.system.service.logger.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统管理 - 登录日志
 *
 * @author shilinchuan
 * @date 2022/12/12
 */
@Api(tags = "0114.系统管理-登录日志")
@RestController
@RequestMapping("/sys/login-log")
@Validated
public class SysLoginLogController {

    private final SysLoginLogService sysLoginLogService;

    public SysLoginLogController(SysLoginLogService sysLoginLogService) {
        this.sysLoginLogService = sysLoginLogService;
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:login-log:query')")
    public CommonResult<SysLoginLogRespVO> getByBizId(@PathVariable String bizId) {
        SysLoginLogDO saveDO = sysLoginLogService.getByBizId(bizId);
        return success(SysLoginLogConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:login-log:query')")
    public CommonResult<KlmbPage<SysLoginLogRespVO>> page(@Valid SysLoginLogPageReqVO reqVO) {
        KlmbPage<SysLoginLogDO> klmbPage = KlmbPage.<SysLoginLogDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysLoginLogQueryDTO queryDTO = SysLoginLogConvert.INSTANCE.convert(reqVO);
        if (BooleanUtil.isTrue(reqVO.getOperateStatus())) {
            queryDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        } else if (BooleanUtil.isFalse(reqVO.getOperateStatus())) {
            queryDTO.setMinResult(LoginResultEnum.SUCCESS.getResult());
        }
        KlmbPage<SysLoginLogDO> page = sysLoginLogService.page(queryDTO, klmbPage);
        return success(SysLoginLogConvert.INSTANCE.convert(page));
    }

}

