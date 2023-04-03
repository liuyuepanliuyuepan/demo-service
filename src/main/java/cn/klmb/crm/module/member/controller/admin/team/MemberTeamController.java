package cn.klmb.crm.module.member.controller.admin.team;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamPageReqVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamRespVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamUpdateReqVO;
import cn.klmb.crm.module.member.convert.team.MemberTeamConvert;
import cn.klmb.crm.module.member.dto.team.MemberTeamQueryDTO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
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
 * crm团队成员 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0301. crm团队成员")
@RestController
@RequestMapping("/member/team")
@Validated
public class MemberTeamController {

    private final MemberTeamService memberTeamService;

    public MemberTeamController(MemberTeamService memberTeamService) {
        this.memberTeamService = memberTeamService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:team:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberTeamSaveReqVO saveReqVO) {
        MemberTeamDO saveDO = MemberTeamConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (memberTeamService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:team:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberTeamService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:team:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody MemberTeamUpdateReqVO updateReqVO) {
        MemberTeamDO updateDO = MemberTeamConvert.INSTANCE.convert(updateReqVO);
        memberTeamService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:team:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberTeamService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:team:query')")
    public CommonResult<MemberTeamRespVO> getByBizId(@PathVariable String bizId) {
        MemberTeamDO saveDO = memberTeamService.getByBizId(bizId);
        return success(MemberTeamConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:team:query')")
    public CommonResult<KlmbPage<MemberTeamRespVO>> page(@Valid MemberTeamPageReqVO reqVO) {
        KlmbPage<MemberTeamDO> klmbPage = KlmbPage.<MemberTeamDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        MemberTeamQueryDTO queryDTO = MemberTeamConvert.INSTANCE.convert(reqVO);
        KlmbPage<MemberTeamDO> page = memberTeamService.page(queryDTO, klmbPage);
        return success(MemberTeamConvert.INSTANCE.convert(page));
    }

    //添加团队成员

    //编辑权限

    //移除团队(除了移除客户模块外还可以移除联系人、商机、合同、回访)

    //批量转移 (其中包含的操作为1. 变更的负责人 2.将原负责人移除或转为团队成员(注意转为团队成员包括增加权限、有效期限) 3.同时将负责人变更联系人、商机、合同模块 )

}
