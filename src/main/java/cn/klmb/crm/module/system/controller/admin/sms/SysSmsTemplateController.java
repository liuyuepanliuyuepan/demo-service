package cn.klmb.crm.module.system.controller.admin.sms;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplatePageReqVO;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplateRespVO;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplateSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplateSendReqVO;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplateUpdateReqVO;
import cn.klmb.crm.module.system.convert.sms.SysSmsTemplateConvert;
import cn.klmb.crm.module.system.dto.sms.SysSmsTemplateQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsTemplateDO;
import cn.klmb.crm.module.system.service.sms.SysSmsSendService;
import cn.klmb.crm.module.system.service.sms.SysSmsTemplateService;
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
 * 系统管理 - 短信模板
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Api(tags = "0112.系统管理-短信模板")
@RestController
@RequestMapping("/sys/sms-template")
@Validated
public class SysSmsTemplateController {

    private final SysSmsTemplateService sysSmsTemplateService;
    private final SysSmsSendService sysSmsSendService;

    public SysSmsTemplateController(SysSmsTemplateService sysSmsTemplateService,
            SysSmsSendService sysSmsSendService) {
        this.sysSmsTemplateService = sysSmsTemplateService;
        this.sysSmsSendService = sysSmsSendService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:sms-template:save')")
    public CommonResult<String> save(@Valid @RequestBody SysSmsTemplateSaveReqVO saveReqVO) {
        SysSmsTemplateDO saveDO = SysSmsTemplateConvert.INSTANCE.convert(saveReqVO);
        saveDO.setParams(sysSmsTemplateService.parseTemplateContentParams(saveReqVO.getContent()));
        saveDO.setChannelCode(saveReqVO.getCode());
        String bizId = "";
        if (sysSmsTemplateService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:sms-template:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysSmsTemplateService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:sms-template:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysSmsTemplateUpdateReqVO updateReqVO) {
        SysSmsTemplateDO updateDO = SysSmsTemplateConvert.INSTANCE.convert(updateReqVO);
        updateDO.setParams(
                sysSmsTemplateService.parseTemplateContentParams(updateReqVO.getContent()));
        updateDO.setChannelCode(updateReqVO.getCode());
        sysSmsTemplateService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:sms-template:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysSmsTemplateService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:sms-template:query')")
    public CommonResult<SysSmsTemplateRespVO> getByBizId(@PathVariable String bizId) {
        SysSmsTemplateDO saveDO = sysSmsTemplateService.getByBizId(bizId);
        return success(SysSmsTemplateConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:sms-template:query')")
    public CommonResult<KlmbPage<SysSmsTemplateRespVO>> page(@Valid SysSmsTemplatePageReqVO reqVO) {
        KlmbPage<SysSmsTemplateDO> klmbPage = KlmbPage.<SysSmsTemplateDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysSmsTemplateQueryDTO queryDTO = SysSmsTemplateConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysSmsTemplateDO> page = sysSmsTemplateService.page(queryDTO, klmbPage);
        return success(SysSmsTemplateConvert.INSTANCE.convert(page));
    }

    @PostMapping("/send-sms")
    @ApiOperation("发送测试短信")
    @PreAuthorize("@ss.hasPermission('system:sms-template:send-sms')")
    public CommonResult<String> sendSms(@Valid @RequestBody SysSmsTemplateSendReqVO sendReqVO) {
        return success(sysSmsSendService.sendSingleSmsToAdmin(sendReqVO.getMobile(), null,
                sendReqVO.getTemplateCode(), sendReqVO.getTemplateParams()));
    }

}

