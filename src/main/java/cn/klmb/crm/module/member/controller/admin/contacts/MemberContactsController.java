package cn.klmb.crm.module.member.controller.admin.contacts;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsDeleteReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsRespVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsUpdateReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberFirstContactsReqVO;
import cn.klmb.crm.module.member.convert.contacts.MemberContactsConvert;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
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
 * 联系人 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0204. 客户管理-联系人")
@RestController
@RequestMapping("/member/contacts")
@Validated
public class MemberContactsController {

    private final MemberContactsService memberContactsService;

    public MemberContactsController(MemberContactsService memberContactsService) {
        this.memberContactsService = memberContactsService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:contacts:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberContactsSaveReqVO saveReqVO) {
        MemberContactsDO saveDO = MemberContactsConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        saveDO.setOwnerUserId(userId);
        if (memberContactsService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:contacts:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberContactsService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:contacts:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody MemberContactsUpdateReqVO updateReqVO) {
        MemberContactsDO updateDO = MemberContactsConvert.INSTANCE.convert(updateReqVO);
        memberContactsService.updateDO(updateDO);
        return success(true);
    }


    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:contacts:query')")
    public CommonResult<MemberContactsRespVO> getByBizId(@PathVariable String bizId) {
        MemberContactsDO saveDO = memberContactsService.getByBizId(bizId);
        return success(MemberContactsConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:contacts:query')")
    public CommonResult<KlmbPage<MemberContactsRespVO>> page(@Valid MemberContactsPageReqVO reqVO) {
        KlmbPage<MemberContactsDO> page = memberContactsService.page(reqVO);
        return success(MemberContactsConvert.INSTANCE.convert(page));
    }

    @PostMapping(value = "/batch-delete")
    @ApiOperation(value = "批量删除联系人")
    @PreAuthorize("@ss.hasPermission('member:user:delete')")
    public CommonResult<Boolean> deleteByBizIds(@RequestBody MemberContactsDeleteReqVO reqVO) {
        memberContactsService.removeByBizIds(reqVO.getBizIds());
        return success(true);
    }

    @PostMapping("/setContacts")
    @ApiOperation(value = "设置首要联系人")
    public CommonResult<Boolean> setContacts(@RequestBody MemberFirstContactsReqVO reqVO) {
        memberContactsService.setContacts(reqVO);
        return CommonResult.success(true);
    }


}
