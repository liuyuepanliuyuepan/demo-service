package cn.klmb.crm.module.member.controller.admin.userpoolrelation;


import cn.klmb.crm.module.member.service.userpoolrelation.MemberUserPoolRelationService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
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

}
