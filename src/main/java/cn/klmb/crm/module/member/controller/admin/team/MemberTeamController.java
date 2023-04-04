package cn.klmb.crm.module.member.controller.admin.team;

import cn.klmb.crm.module.member.service.team.MemberTeamService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
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

    //添加团队成员

    //编辑权限

    //移除团队(除了移除客户模块外还可以移除联系人、商机、合同、回访)

    //批量转移 (其中包含的操作为1. 变更的负责人 2.将原负责人移除或转为团队成员(注意转为团队成员包括增加权限、有效期限) 3.同时将负责人变更联系人、商机、合同模块 )

}
