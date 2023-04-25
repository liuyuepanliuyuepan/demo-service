package cn.klmb.crm.module.member.controller.admin.team;

import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamReqVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveBO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MembersTeamSelectVO;
import cn.klmb.crm.module.member.service.team.MemberTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("/getMembers")
    @ApiOperation("获取团队成员")
    @PreAuthorize("@ss.hasPermission('member:team:query')")
    public CommonResult<List<MembersTeamSelectVO>> getMembers(@Valid MemberTeamReqVO reqVO) {
        List<MembersTeamSelectVO> members = memberTeamService.getMembers(reqVO);
        return CommonResult.success(members);
    }

    @PostMapping("/add")
    @ApiOperation("新增团队成员")
    @PreAuthorize("@ss.hasPermission('member:team:save')")
    public CommonResult<Boolean> addMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.addMember(memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("编辑团队成员")
    @PreAuthorize("@ss.hasPermission('member:team:post')")
    public CommonResult<Boolean> updateMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.addMember(memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/delete")
    @ApiOperation("删除团队成员")
    @PreAuthorize("@ss.hasPermission('member:team:delete')")
    public CommonResult<Boolean> deleteMembers(@RequestBody MemberTeamSaveBO memberTeamSaveBO) {
        memberTeamService.deleteMember(memberTeamSaveBO);
        return CommonResult.success(true);
    }

    @PostMapping("/exitTeam")
    @ApiOperation("退出团队")
    @PreAuthorize("@ss.hasPermission('member:team:post')")
    public CommonResult<Boolean> exitTeam(@Valid MemberTeamReqVO reqVO) {
        memberTeamService.exitTeam(reqVO);
        return CommonResult.success(true);
    }

    //编辑权限

    //移除团队(除了移除客户模块外还可以移除联系人、商机、合同、回访)

    //批量转移 (其中包含的操作为1. 变更的负责人 2.将原负责人移除或转为团队成员(注意转为团队成员包括增加权限、有效期限) 3.同时将负责人变更联系人、商机、合同模块 )

}
