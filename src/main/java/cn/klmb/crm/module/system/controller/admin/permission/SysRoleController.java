package cn.klmb.crm.module.system.controller.admin.permission;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.permission.vo.role.SysRolePageReqVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.role.SysRoleRespVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.role.SysRoleSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.role.SysRoleSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.role.SysRoleUpdateReqVO;
import cn.klmb.crm.module.system.convert.permission.SysRoleConvert;
import cn.klmb.crm.module.system.dto.permission.SysRoleQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysRoleDO;
import cn.klmb.crm.module.system.enums.permission.SysRoleDeptIdEnum;
import cn.klmb.crm.module.system.service.permission.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
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
 * 系统管理 - 角色管理
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Api(tags = "0102.系统管理-角色管理")
@RestController
@RequestMapping("/sys/role")
@Validated
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:role:save')")
    public CommonResult<String> save(@Valid @RequestBody SysRoleSaveReqVO saveReqVO) {
        SysRoleDO saveDO = SysRoleConvert.INSTANCE.convert(saveReqVO);
        // 设置为超管部门角色
        saveDO.setDeptId(SysRoleDeptIdEnum.SUPER_DEPT_ID.getId());
        String bizId = "";
        if (sysRoleService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:role:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysRoleService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:role:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysRoleUpdateReqVO updateReqVO) {
        SysRoleDO updateDO = SysRoleConvert.INSTANCE.convert01(updateReqVO);
        sysRoleService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:role:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysRoleService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    public CommonResult<SysRoleRespVO> getByBizId(@PathVariable String bizId) {
        SysRoleDO saveDO = sysRoleService.getByBizId(bizId);
        return success(SysRoleConvert.INSTANCE.convert01(saveDO));
    }

    @ApiOperation(value = "列表精简信息")
    @GetMapping({"/list-all-simple"})
    public CommonResult<List<SysRoleSimpleRespVO>> listAllSimple(SysRolePageReqVO reqVO) {
        SysRoleQueryDTO queryDTO = SysRoleConvert.INSTANCE.convert(reqVO);
        // 只查询超管部门角色
        queryDTO.setDeptId(SysRoleDeptIdEnum.SUPER_DEPT_ID.getId());
        List<SysRoleDO> entities = sysRoleService.list(queryDTO);
        return success(SysRoleConvert.INSTANCE.convert01(entities));
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    public CommonResult<KlmbPage<SysRoleRespVO>> page(@Valid SysRolePageReqVO reqVO) {
        KlmbPage<SysRoleDO> klmbPage = KlmbPage.<SysRoleDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysRoleQueryDTO queryDTO = SysRoleConvert.INSTANCE.convert(reqVO);
        // 只查询超管部门角色
        queryDTO.setDeptId(SysRoleDeptIdEnum.SUPER_DEPT_ID.getId());
        KlmbPage<SysRoleDO> page = sysRoleService.page(queryDTO, klmbPage);
        return success(SysRoleConvert.INSTANCE.convert(page));
    }

}

