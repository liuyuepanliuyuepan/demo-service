package cn.klmb.crm.module.member.controller.admin.record;


import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.record.vo.MemberOwnerRecordPageReqVO;
import cn.klmb.crm.module.member.controller.admin.record.vo.MemberOwnerRecordRespVO;
import cn.klmb.crm.module.member.controller.admin.record.vo.MemberOwnerRecordSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.record.vo.MemberOwnerRecordUpdateReqVO;
import cn.klmb.crm.module.member.convert.record.MemberOwnerRecordConvert;
import cn.klmb.crm.module.member.dto.record.MemberOwnerRecordQueryDTO;
import cn.klmb.crm.module.member.entity.record.MemberOwnerRecordDO;
import cn.klmb.crm.module.member.service.record.MemberOwnerRecordService;
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
 * 负责人变更记录 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0401. 负责人变更记录")
@RestController
@RequestMapping("/member/owner-record")
@Validated
public class MemberOwnerRecordController {

    private final MemberOwnerRecordService memberOwnerRecordService;

    public MemberOwnerRecordController(MemberOwnerRecordService memberOwnerRecordService) {
        this.memberOwnerRecordService = memberOwnerRecordService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:owner-record:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberOwnerRecordSaveReqVO saveReqVO) {
        MemberOwnerRecordDO saveDO = MemberOwnerRecordConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (memberOwnerRecordService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:owner-record:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberOwnerRecordService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:owner-record:update')")
    public CommonResult<Boolean> update(
            @Valid @RequestBody MemberOwnerRecordUpdateReqVO updateReqVO) {
        MemberOwnerRecordDO updateDO = MemberOwnerRecordConvert.INSTANCE.convert(updateReqVO);
        memberOwnerRecordService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:owner-record:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberOwnerRecordService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:owner-record:query')")
    public CommonResult<MemberOwnerRecordRespVO> getByBizId(@PathVariable String bizId) {
        MemberOwnerRecordDO saveDO = memberOwnerRecordService.getByBizId(bizId);
        return success(MemberOwnerRecordConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:owner-record:query')")
    public CommonResult<KlmbPage<MemberOwnerRecordRespVO>> page(
            @Valid MemberOwnerRecordPageReqVO reqVO) {
        KlmbPage<MemberOwnerRecordDO> klmbPage = KlmbPage.<MemberOwnerRecordDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        MemberOwnerRecordQueryDTO queryDTO = MemberOwnerRecordConvert.INSTANCE.convert(reqVO);
        KlmbPage<MemberOwnerRecordDO> page = memberOwnerRecordService.page(queryDTO, klmbPage);
        return success(MemberOwnerRecordConvert.INSTANCE.convert(page));
    }

}
