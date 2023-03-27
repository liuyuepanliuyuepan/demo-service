package cn.klmb.crm.module.system.controller.admin.permission;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuListReqVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuRespVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.permission.vo.menu.SysMenuUpdateReqVO;
import cn.klmb.crm.module.system.convert.permission.SysMenuConvert;
import cn.klmb.crm.module.system.dto.permission.SysMenuQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysMenuDO;
import cn.klmb.crm.module.system.service.permission.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.Comparator;
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
 * 系统管理 - 菜单管理
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Api(tags = "0103.系统管理-菜单管理")
@RestController
@RequestMapping("/sys/menu")
@Validated
public class SysMenuController {

    private final SysMenuService sysMenuService;

    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:menu:save')")
    public CommonResult<String> save(@Valid @RequestBody SysMenuSaveReqVO saveReqVO) {
        SysMenuDO saveDO = SysMenuConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (sysMenuService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:menu:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysMenuService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysMenuUpdateReqVO updateReqVO) {
        SysMenuDO updateDO = SysMenuConvert.INSTANCE.convert01(updateReqVO);
        sysMenuService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysMenuService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<SysMenuRespVO> getByBizId(@PathVariable String bizId) {
        SysMenuDO saveDO = sysMenuService.getByBizId(bizId);
        return success(SysMenuConvert.INSTANCE.convert01(saveDO));
    }

    @GetMapping({"/list"})
    @ApiOperation(value = "获取菜单列表", notes = "用于【菜单管理】界面")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<List<SysMenuRespVO>> list(SysMenuListReqVO reqVO) {
        SysMenuQueryDTO queryDTO = SysMenuConvert.INSTANCE.convert(reqVO);
        List<SysMenuDO> menus = sysMenuService.list(queryDTO);
        // 排序后，返回给前端
        menus.sort(Comparator.comparing(SysMenuDO::getSort));
        return success(SysMenuConvert.INSTANCE.convert(menus));
    }

    @GetMapping({"/list-all-simple"})
    @ApiOperation(value = "获取菜单精简信息列表", notes = "只包含被开启的菜单，用于【角色分配菜单】功能的选项。")
    public CommonResult<List<SysMenuSimpleRespVO>> listAllSimple(SysMenuListReqVO reqVO) {
        SysMenuQueryDTO queryDTO = SysMenuConvert.INSTANCE.convert(reqVO);
        List<SysMenuDO> formList = sysMenuService.list(queryDTO);
        // 排序后，返回给前端
        formList.sort(Comparator.comparing(SysMenuDO::getSort));
        return success(SysMenuConvert.INSTANCE.convert01(formList));
    }

}

