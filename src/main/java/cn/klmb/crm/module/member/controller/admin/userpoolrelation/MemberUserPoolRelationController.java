package cn.klmb.crm.module.member.controller.admin.userpoolrelation;


import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo.MemberUserPoolRelationPageReqVO;
import cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo.MemberUserPoolRelationRespVO;
import cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo.MemberUserPoolRelationSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo.MemberUserPoolRelationUpdateReqVO;
import cn.klmb.crm.module.member.convert.userpoolrelation.MemberUserPoolRelationConvert;
import cn.klmb.crm.module.member.dto.userpoolrelation.MemberUserPoolRelationQueryDTO;
import cn.klmb.crm.module.member.entity.userpoolrelation.MemberUserPoolRelationDO;
import cn.klmb.crm.module.member.service.userpoolrelation.MemberUserPoolRelationService;
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
 * 客户公海关联 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0207.客户管理-客户公海关联")
@RestController
@RequestMapping("/member/user-pool-relation")
@Validated
public class MemberUserPoolRelationController {

    private final MemberUserPoolRelationService memberUserPoolRelationService;

    public MemberUserPoolRelationController(
            MemberUserPoolRelationService memberUserPoolRelationService) {
        this.memberUserPoolRelationService = memberUserPoolRelationService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:user-pool-relation:save')")
    public CommonResult<String> save(
            @Valid @RequestBody MemberUserPoolRelationSaveReqVO saveReqVO) {
        MemberUserPoolRelationDO saveDO = MemberUserPoolRelationConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (memberUserPoolRelationService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user-pool-relation:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberUserPoolRelationService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:user-pool-relation:update')")
    public CommonResult<Boolean> update(
            @Valid @RequestBody MemberUserPoolRelationUpdateReqVO updateReqVO) {
        MemberUserPoolRelationDO updateDO = MemberUserPoolRelationConvert.INSTANCE.convert(
                updateReqVO);
        memberUserPoolRelationService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:user-pool-relation:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberUserPoolRelationService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user-pool-relation:query')")
    public CommonResult<MemberUserPoolRelationRespVO> getByBizId(@PathVariable String bizId) {
        MemberUserPoolRelationDO saveDO = memberUserPoolRelationService.getByBizId(bizId);
        return success(MemberUserPoolRelationConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:user-pool-relation:query')")
    public CommonResult<KlmbPage<MemberUserPoolRelationRespVO>> page(
            @Valid MemberUserPoolRelationPageReqVO reqVO) {
        KlmbPage<MemberUserPoolRelationDO> klmbPage = KlmbPage.<MemberUserPoolRelationDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        MemberUserPoolRelationQueryDTO queryDTO = MemberUserPoolRelationConvert.INSTANCE.convert(
                reqVO);
        KlmbPage<MemberUserPoolRelationDO> page = memberUserPoolRelationService.page(queryDTO,
                klmbPage);
        return success(MemberUserPoolRelationConvert.INSTANCE.convert(page));
    }

}
