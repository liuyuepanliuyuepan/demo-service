package cn.klmb.crm.module.member.controller.admin.delivery;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.delivery.vo.MemberDeliveryPageReqVO;
import cn.klmb.crm.module.member.controller.admin.delivery.vo.MemberDeliveryRespVO;
import cn.klmb.crm.module.member.controller.admin.delivery.vo.MemberDeliverySaveReqVO;
import cn.klmb.crm.module.member.controller.admin.delivery.vo.MemberDeliveryUpdateReqVO;
import cn.klmb.crm.module.member.convert.delivery.MemberDeliveryConvert;
import cn.klmb.crm.module.member.dto.delivery.MemberDeliveryQueryDTO;
import cn.klmb.crm.module.member.entity.delivery.MemberDeliveryDO;
import cn.klmb.crm.module.member.service.delivery.MemberDeliveryService;
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
 * 客户-收货地址 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0202.客户管理-收货地址")
@RestController
@RequestMapping("/member/delivery")
@Validated
public class MemberDeliveryController {

    private final MemberDeliveryService memberDeliveryService;

    public MemberDeliveryController(MemberDeliveryService memberDeliveryService) {
        this.memberDeliveryService = memberDeliveryService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('member:delivery:save')")
    public CommonResult<String> save(@Valid @RequestBody MemberDeliverySaveReqVO saveReqVO) {
        MemberDeliveryDO saveDO = MemberDeliveryConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (memberDeliveryService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:delivery:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        memberDeliveryService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('member:delivery:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody MemberDeliveryUpdateReqVO updateReqVO) {
        MemberDeliveryDO updateDO = MemberDeliveryConvert.INSTANCE.convert(updateReqVO);
        memberDeliveryService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('member:delivery:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        memberDeliveryService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('member:delivery:query')")
    public CommonResult<MemberDeliveryRespVO> getByBizId(@PathVariable String bizId) {
        MemberDeliveryDO saveDO = memberDeliveryService.getByBizId(bizId);
        return success(MemberDeliveryConvert.INSTANCE.convert(saveDO));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('member:delivery:query')")
    public CommonResult<KlmbPage<MemberDeliveryRespVO>> page(@Valid MemberDeliveryPageReqVO reqVO) {
        KlmbPage<MemberDeliveryDO> klmbPage = KlmbPage.<MemberDeliveryDO>builder()
              .pageNo(reqVO.getPageNo())
              .pageSize(reqVO.getPageSize())
              .build();
        MemberDeliveryQueryDTO queryDTO = MemberDeliveryConvert.INSTANCE.convert(reqVO);
        KlmbPage<MemberDeliveryDO> page = memberDeliveryService.page(queryDTO, klmbPage);
        return success(MemberDeliveryConvert.INSTANCE.convert(page));
    }

}
