package cn.klmb.crm.module.member.controller.admin.userpool;


import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolPageReqVO;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolRespVO;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolSimpleRespVO;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolUpdateReqVO;
import cn.klmb.crm.module.member.convert.userpool.MemberUserPoolConvert;
import cn.klmb.crm.module.member.dto.userpool.MemberUserPoolQueryDTO;
import cn.klmb.crm.module.member.entity.userpool.MemberUserPoolDO;
import cn.klmb.crm.module.member.service.userpool.MemberUserPoolService;
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
 * 公海 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0206.客户管理-公海")
@RestController
@RequestMapping("/member/user-pool")
@Validated
public class MemberUserPoolController {

    private final MemberUserPoolService memberUserPoolService;

    public MemberUserPoolController(MemberUserPoolService memberUserPoolService) {
        this.memberUserPoolService = memberUserPoolService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:user-pool:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberUserPoolSaveReqVO saveReqVO) {
        MemberUserPoolDO saveDO = MemberUserPoolConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (memberUserPoolService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user-pool:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberUserPoolService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:user-pool:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody MemberUserPoolUpdateReqVO updateReqVO) {
        MemberUserPoolDO updateDO = MemberUserPoolConvert.INSTANCE.convert(updateReqVO);
        memberUserPoolService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:user-pool:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberUserPoolService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user-pool:query')")
    public CommonResult<MemberUserPoolRespVO> getByBizId(@PathVariable String bizId) {
        MemberUserPoolDO saveDO = memberUserPoolService.getByBizId(bizId);
        return success(MemberUserPoolConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:user-pool:query')")
    public CommonResult<KlmbPage<MemberUserPoolRespVO>> page(@Valid MemberUserPoolPageReqVO reqVO) {
        KlmbPage<MemberUserPoolDO> klmbPage = KlmbPage.<MemberUserPoolDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        MemberUserPoolQueryDTO queryDTO = MemberUserPoolConvert.INSTANCE.convert(reqVO);
        KlmbPage<MemberUserPoolDO> page = memberUserPoolService.page(queryDTO, klmbPage);
        return success(MemberUserPoolConvert.INSTANCE.convert(page));
    }

    @GetMapping({"/list-all-simple"})
    @ApiOperation(value = "列表精简信息")
    @PreAuthorize("@ss.hasPermission('member:user-pool:query')")
    public CommonResult<List<MemberUserPoolSimpleRespVO>> listAllSimple(
            @Valid MemberUserPoolPageReqVO reqVO) {
        MemberUserPoolQueryDTO queryDTO = MemberUserPoolConvert.INSTANCE.convert(reqVO);
        List<MemberUserPoolDO> entities = memberUserPoolService.list(queryDTO);
        return success(MemberUserPoolConvert.INSTANCE.convert01(entities));
    }


}
