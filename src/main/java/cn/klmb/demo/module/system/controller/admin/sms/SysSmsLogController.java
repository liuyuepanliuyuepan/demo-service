package cn.klmb.demo.module.system.controller.admin.sms;

import static cn.klmb.demo.framework.common.pojo.CommonResult.success;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.module.system.controller.admin.sms.vo.log.SysSmsLogPageReqVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.log.SysSmsLogRespVO;
import cn.klmb.demo.module.system.convert.sms.SysSmsLogConvert;
import cn.klmb.demo.module.system.dto.sms.SysSmsLogQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsLogDO;
import cn.klmb.demo.module.system.service.sms.SysSmsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统管理 - 短信日志
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@Api(tags = "0113.系统管理-短信日志")
@RestController
@RequestMapping("/sys/sms-log")
@Validated
public class SysSmsLogController {

    private final SysSmsLogService sysSmsLogService;

    public SysSmsLogController(SysSmsLogService sysSmsLogService) {
        this.sysSmsLogService = sysSmsLogService;
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:sms-log:query')")
    public CommonResult<KlmbPage<SysSmsLogRespVO>> page(@Valid SysSmsLogPageReqVO reqVO) {
        KlmbPage<SysSmsLogDO> klmbPage = KlmbPage.<SysSmsLogDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysSmsLogQueryDTO queryDTO = SysSmsLogConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysSmsLogDO> page = sysSmsLogService.page(queryDTO, klmbPage);
        return success(SysSmsLogConvert.INSTANCE.convert(page));
    }

}

