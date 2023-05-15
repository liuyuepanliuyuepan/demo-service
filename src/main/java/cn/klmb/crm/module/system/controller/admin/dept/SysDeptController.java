package cn.klmb.crm.module.system.controller.admin.dept;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptListReqVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptRespVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.dept.vo.SysDeptUpdateReqVO;
import cn.klmb.crm.module.system.convert.dept.SysDeptConvert;
import cn.klmb.crm.module.system.dto.dept.SysDeptQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.security.PermitAll;
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
 * 系统管理 - 部门管理
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Api(tags = "0105.系统管理-部门管理")
@RestController
@RequestMapping("/sys/dept")
@Validated
public class SysDeptController {

    private final SysDeptService sysDeptService;

    public SysDeptController(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:dept:save')")
    public CommonResult<String> save(@Valid @RequestBody SysDeptSaveReqVO saveReqVO) {
        SysDeptDO saveDO = SysDeptConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (sysDeptService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysDeptService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysDeptUpdateReqVO updateReqVO) {
        SysDeptDO updateDO = SysDeptConvert.INSTANCE.convert01(updateReqVO);
        sysDeptService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysDeptService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<SysDeptRespVO> getByBizId(@PathVariable String bizId) {
        SysDeptDO saveDO = sysDeptService.getByBizId(bizId);
        return success(SysDeptConvert.INSTANCE.convert01(saveDO));
    }

    @GetMapping({"/list"})
    @ApiOperation(value = "获取部门列表", notes = "用于【部门管理】界面")
    @PermitAll
    public CommonResult<List<SysDeptRespVO>> list(SysDeptListReqVO reqVO) {
        SysDeptQueryDTO queryDTO = SysDeptConvert.INSTANCE.convert(reqVO);
        List<SysDeptDO> list = sysDeptService.list(queryDTO);
        list.sort(Comparator.comparing(SysDeptDO::getSort));
        return success(SysDeptConvert.INSTANCE.convert(list));
    }

    @GetMapping({"/list_v2"})
    @ApiOperation(value = "获取本部门与下级部门列表", notes = "用于【首页自定义查询部门】界面")
    @PermitAll
    public CommonResult<List<SysDeptRespVO>> listV2(SysDeptListReqVO reqVO) {
        SysDeptQueryDTO queryDTO = SysDeptConvert.INSTANCE.convert(reqVO);
        List<SysDeptDO> list = sysDeptService.listV2(queryDTO);
        list.sort(Comparator.comparing(SysDeptDO::getSort));
        return success(SysDeptConvert.INSTANCE.convert(list));
    }


    @GetMapping({"/list-all-simple"})
    @ApiOperation(value = "获取部门精简信息列表", notes = "只包含被开启的部门，主要用于前端的下拉选项")
    public CommonResult<List<SysDeptSimpleRespVO>> listAllSimple() {
        List<SysDeptDO> list = sysDeptService.list(SysDeptQueryDTO.builder()
                .status(CommonStatusEnum.ENABLE.getStatus())
                .build());
        list.sort(Comparator.comparing(SysDeptDO::getSort));
        return success(SysDeptConvert.INSTANCE.convert01(list));
    }


    @GetMapping(value = "/query-child-dept/{deptId}")
    @ApiOperation(value = "查询部门下属部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<List<String>> queryChildDept(@PathVariable String deptId) {
        return success(sysDeptService.queryChildDept(deptId));
    }

//    @GetMapping({"/tree"})
//    @ApiOperation(value = "部门树")
//    @PreAuthorize("@ss.hasPermission('system:dept:query')")
//    public CommonResult<List<Tree<String>>> tree(SysDeptTreeReqVO reqVO) {
//        SysDeptDTO query = SysDeptConvert.INSTANCE.convert(reqVO);
//        return success(sysDeptService.findSubTree(query));
//    }

}

