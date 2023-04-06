package cn.klmb.crm.module.system.controller.admin.notify;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.notify.vo.template.SysNotifyTemplatePageReqVO;
import cn.klmb.crm.module.system.controller.admin.notify.vo.template.SysNotifyTemplateRespVO;
import cn.klmb.crm.module.system.controller.admin.notify.vo.template.SysNotifyTemplateSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.notify.vo.template.SysNotifyTemplateSendReqVO;
import cn.klmb.crm.module.system.controller.admin.notify.vo.template.SysNotifyTemplateUpdateReqVO;
import cn.klmb.crm.module.system.convert.notify.SysNotifyTemplateConvert;
import cn.klmb.crm.module.system.dto.notify.SysNotifyTemplateQueryDTO;
import cn.klmb.crm.module.system.entity.notify.SysNotifyTemplateDO;
import cn.klmb.crm.module.system.service.notify.SysNotifySendService;
import cn.klmb.crm.module.system.service.notify.SysNotifyTemplateService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 站内信模板 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0116.系统管理-站内信模板")
@RestController
@RequestMapping("/sys/notify-template")
@Validated
public class SysNotifyTemplateController {

    private final SysNotifyTemplateService sysNotifyTemplateService;

    private final SysNotifySendService sysNotifySendService;

    public SysNotifyTemplateController(SysNotifyTemplateService sysNotifyTemplateService,
            SysNotifySendService sysNotifySendService) {
        this.sysNotifyTemplateService = sysNotifyTemplateService;
        this.sysNotifySendService = sysNotifySendService;
    }

    @PostMapping("/create")
    @Operation(summary = "创建站内信模版")
    @PreAuthorize("@ss.hasPermission('system:notify-template:create')")
    public CommonResult<String> createNotifyTemplate(
            @Valid @RequestBody SysNotifyTemplateSaveReqVO createReqVO) {
        return success(sysNotifyTemplateService.createNotifyTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新站内信模版")
    @PreAuthorize("@ss.hasPermission('system:notify-template:update')")
    public CommonResult<Boolean> updateNotifyTemplate(
            @Valid @RequestBody SysNotifyTemplateUpdateReqVO updateReqVO) {
        sysNotifyTemplateService.updateNotifyTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除站内信模版")
    @Parameter(name = "bizId", description = "业务id", required = true)
    @PreAuthorize("@ss.hasPermission('system:notify-template:delete')")
    public CommonResult<Boolean> deleteNotifyTemplate(@RequestParam("bizId") String bizId) {
        sysNotifyTemplateService.deleteNotifyTemplate(bizId);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得站内信模版")
    @Parameter(name = "bizId", description = "业务id", required = true)
    @PreAuthorize("@ss.hasPermission('system:notify-template:query')")
    public CommonResult<SysNotifyTemplateRespVO> getNotifyTemplate(
            @RequestParam("bizId") String bizId) {
        SysNotifyTemplateDO notifyTemplate = sysNotifyTemplateService.getByBizId(bizId);
        return success(SysNotifyTemplateConvert.INSTANCE.convert(notifyTemplate));
    }

    @GetMapping("/page")
    @Operation(summary = "获得站内信模版分页")
    @PreAuthorize("@ss.hasPermission('system:notify-template:query')")
    public CommonResult<KlmbPage<SysNotifyTemplateRespVO>> getNotifyTemplatePage(
            @Valid SysNotifyTemplatePageReqVO reqVO) {
        KlmbPage<SysNotifyTemplateDO> klmbPage = KlmbPage.<SysNotifyTemplateDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysNotifyTemplateQueryDTO queryDTO = SysNotifyTemplateConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysNotifyTemplateDO> page = sysNotifyTemplateService.page(queryDTO, klmbPage);
        return success(SysNotifyTemplateConvert.INSTANCE.convert(page));
    }

    @PostMapping("/send-notify")
    @Operation(summary = "发送站内信")
    @PreAuthorize("@ss.hasPermission('system:notify-template:send-notify')")
    public CommonResult<String> sendNotify(
            @Valid @RequestBody SysNotifyTemplateSendReqVO sendReqVO) {
        return success(sysNotifySendService.sendSingleNotifyToAdmin(sendReqVO.getUserId(),
                sendReqVO.getTemplateCode(), sendReqVO.getTemplateParams()));
    }

}
