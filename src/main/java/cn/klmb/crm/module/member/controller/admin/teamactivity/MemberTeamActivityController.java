package cn.klmb.crm.module.member.controller.admin.teamactivity;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivityPageReqVO;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivityRespVO;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivitySaveReqVO;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivityUpdateReqVO;
import cn.klmb.crm.module.member.convert.teamactivity.MemberTeamActivityConvert;
import cn.klmb.crm.module.member.dto.teamactivity.MemberTeamActivityQueryDTO;
import cn.klmb.crm.module.member.entity.teamactivity.MemberTeamActivityDO;
import cn.klmb.crm.module.member.service.teamactivity.MemberTeamActivityService;
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
 * crm团队成员活动 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0302. crm团队成员活动")
@RestController
@RequestMapping("/member/team-activity")
@Validated
public class MemberTeamActivityController {

    private final MemberTeamActivityService memberTeamActivityService;

    public MemberTeamActivityController(MemberTeamActivityService memberTeamActivityService) {
        this.memberTeamActivityService = memberTeamActivityService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:team-activity:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberTeamActivitySaveReqVO saveReqVO) {
        MemberTeamActivityDO saveDO = MemberTeamActivityConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (memberTeamActivityService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:team-activity:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberTeamActivityService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:team-activity:update')")
    public CommonResult<Boolean> update(
            @Valid @RequestBody MemberTeamActivityUpdateReqVO updateReqVO) {
        MemberTeamActivityDO updateDO = MemberTeamActivityConvert.INSTANCE.convert(updateReqVO);
        memberTeamActivityService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:team-activity:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberTeamActivityService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:team-activity:query')")
    public CommonResult<MemberTeamActivityRespVO> getByBizId(@PathVariable String bizId) {
        MemberTeamActivityDO saveDO = memberTeamActivityService.getByBizId(bizId);
        return success(MemberTeamActivityConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:team-activity:query')")
    public CommonResult<KlmbPage<MemberTeamActivityRespVO>> page(
            @Valid MemberTeamActivityPageReqVO reqVO) {
        KlmbPage<MemberTeamActivityDO> klmbPage = KlmbPage.<MemberTeamActivityDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        MemberTeamActivityQueryDTO queryDTO = MemberTeamActivityConvert.INSTANCE.convert(reqVO);
        KlmbPage<MemberTeamActivityDO> page = memberTeamActivityService.page(queryDTO, klmbPage);
        return success(MemberTeamActivityConvert.INSTANCE.convert(page));
    }



}
