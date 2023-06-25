package cn.klmb.demo.module.system.controller.admin.sms;

import static cn.klmb.demo.framework.common.pojo.CommonResult.success;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelPageReqVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelRespVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelSimpleRespVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.channel.SysSmsChannelUpdateReqVO;
import cn.klmb.demo.module.system.convert.sms.SysSmsChannelConvert;
import cn.klmb.demo.module.system.dto.sms.SysSmsChannelQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsChannelDO;
import cn.klmb.demo.module.system.service.sms.SysSmsChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.Comparator;
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
 * 系统管理 - 短信渠道
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@Api(tags = "0111.系统管理-短信渠道")
@RestController
@RequestMapping("/sys/sms-channel")
@Validated
public class SysSmsChannelController {

    private final SysSmsChannelService sysSmsChannelService;

    public SysSmsChannelController(SysSmsChannelService sysSmsChannelService) {
        this.sysSmsChannelService = sysSmsChannelService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:sms-channel:save')")
    public CommonResult<String> save(@Valid @RequestBody SysSmsChannelSaveReqVO saveReqVO) {
        SysSmsChannelDO saveDO = SysSmsChannelConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (sysSmsChannelService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:sms-channel:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysSmsChannelService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:sms-channel:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysSmsChannelUpdateReqVO updateReqVO) {
        SysSmsChannelDO updateDO = SysSmsChannelConvert.INSTANCE.convert(updateReqVO);
        sysSmsChannelService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:sms-channel:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysSmsChannelService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:sms-channel:query')")
    public CommonResult<SysSmsChannelRespVO> getByBizId(@PathVariable String bizId) {
        SysSmsChannelDO saveDO = sysSmsChannelService.getByBizId(bizId);
        return success(SysSmsChannelConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:sms-channel:query')")
    public CommonResult<KlmbPage<SysSmsChannelRespVO>> page(@Valid SysSmsChannelPageReqVO reqVO) {
        KlmbPage<SysSmsChannelDO> klmbPage = KlmbPage.<SysSmsChannelDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysSmsChannelQueryDTO queryDTO = SysSmsChannelConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysSmsChannelDO> page = sysSmsChannelService.page(queryDTO, klmbPage);
        return success(SysSmsChannelConvert.INSTANCE.convert(page));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得短信渠道精简列表", notes = "包含被禁用的短信渠道")
    public CommonResult<List<SysSmsChannelSimpleRespVO>> getSimpleSmsChannels() {
        List<SysSmsChannelDO> list = sysSmsChannelService.list();
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysSmsChannelDO::getId));
        return success(SysSmsChannelConvert.INSTANCE.convert(list));
    }

}

