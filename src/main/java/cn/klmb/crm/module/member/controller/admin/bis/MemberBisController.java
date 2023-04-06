package cn.klmb.crm.module.member.controller.admin.bis;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.util.ObjectUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.bis.vo.MemberBisPageReqVO;
import cn.klmb.crm.module.member.controller.admin.bis.vo.MemberBisRespVO;
import cn.klmb.crm.module.member.controller.admin.bis.vo.MemberBisSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.bis.vo.MemberBisUpdateReqVO;
import cn.klmb.crm.module.member.convert.bis.MemberBisConvert;
import cn.klmb.crm.module.member.dto.bis.MemberBisQueryDTO;
import cn.klmb.crm.module.member.entity.bis.MemberBisDO;
import cn.klmb.crm.module.member.service.bis.MemberBisService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * 客户工商信息 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0205.客户管理-客户工商信息")
@RestController
@RequestMapping("/member/bis")
@Validated
public class MemberBisController {

    private final MemberBisService memberBisService;

    public MemberBisController(MemberBisService memberBisService) {
        this.memberBisService = memberBisService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:bis:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberBisSaveReqVO saveReqVO) {
        MemberBisDO saveDO = MemberBisConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (memberBisService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:bis:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberBisService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:bis:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody MemberBisUpdateReqVO updateReqVO) {
        MemberBisDO updateDO = MemberBisConvert.INSTANCE.convert(updateReqVO);
        memberBisService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:bis:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberBisService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:bis:query')")
    public CommonResult<MemberBisRespVO> getByBizId(@PathVariable String bizId) {
        MemberBisDO saveDO = memberBisService.getByBizId(bizId);
        return success(MemberBisConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:bis:query')")
    public CommonResult<KlmbPage<MemberBisRespVO>> page(@Valid MemberBisPageReqVO reqVO) {
        KlmbPage<MemberBisDO> klmbPage = KlmbPage.<MemberBisDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        MemberBisQueryDTO queryDTO = MemberBisConvert.INSTANCE.convert(reqVO);
        KlmbPage<MemberBisDO> page = memberBisService.page(queryDTO, klmbPage);
        return success(MemberBisConvert.INSTANCE.convert(page));
    }

    @GetMapping(value = "/detail_v1/{customerId}")
    @ApiOperation(value = "根据客户id查询工商信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:bis:query')")
    public CommonResult<MemberBisRespVO> getByCustomerId(@PathVariable String customerId) {
        MemberBisDO saveDO = null;
        MemberBisDO memberBisDO = memberBisService.getOne(
                new LambdaQueryWrapper<MemberBisDO>().eq(MemberBisDO::getCustomerId, customerId)
                        .eq(MemberBisDO::getDeleted, false));
        if (ObjectUtil.isNotNull(memberBisDO)) {
            saveDO = memberBisService.getByBizId(memberBisDO.getBizId());
        }
        return success(MemberBisConvert.INSTANCE.convert(saveDO));
    }

}
