package cn.klmb.crm.module.member.controller.admin.user;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserRespVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import cn.klmb.crm.module.member.convert.user.MemberUserConvert;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.user.MemberUserService;
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
 * 客户-用户 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0201.客户管理-用户信息")
@RestController
@RequestMapping("/member/user")
@Validated
public class MemberUserController {

    private final MemberUserService memberUserService;

    public MemberUserController(MemberUserService memberUserService) {
        this.memberUserService = memberUserService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:user:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberUserSaveReqVO saveReqVO) {
        MemberUserDO saveDO = MemberUserConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (memberUserService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberUserService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:user:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody MemberUserUpdateReqVO updateReqVO) {
        MemberUserDO updateDO = MemberUserConvert.INSTANCE.convert(updateReqVO);
        memberUserService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:user:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberUserService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<MemberUserRespVO> getByBizId(@PathVariable String bizId) {
        MemberUserDO saveDO = memberUserService.getByBizId(bizId);
        return success(MemberUserConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<KlmbPage<MemberUserRespVO>> page(@Valid MemberUserPageReqVO reqVO) {
        KlmbPage<MemberUserDO> klmbPage = KlmbPage.<MemberUserDO>builder()
              .pageNo(reqVO.getPageNo())
              .pageSize(reqVO.getPageSize())
              .build();
        MemberUserQueryDTO queryDTO = MemberUserConvert.INSTANCE.convert(reqVO);
        KlmbPage<MemberUserDO> page = memberUserService.page(queryDTO, klmbPage);
        return success(MemberUserConvert.INSTANCE.convert(page));
    }

}
