package cn.klmb.crm.module.system.controller.admin.user;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.common.pojo.CommonResult.success;
import static cn.klmb.crm.framework.common.util.collection.CollectionUtils.convertList;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.common.util.collection.CollectionUtils;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserPageReqVO;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserRespVO;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserSimpleRespVO;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserUpdatePwdReqVO;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserUpdateReqVO;
import cn.klmb.crm.module.system.convert.permission.SysRoleConvert;
import cn.klmb.crm.module.system.convert.user.SysUserConvert;
import cn.klmb.crm.module.system.dto.feishu.FeishuMinAppResultDTO;
import cn.klmb.crm.module.system.dto.user.SysUserQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import cn.klmb.crm.module.system.entity.permission.SysRoleDO;
import cn.klmb.crm.module.system.entity.permission.SysUserRoleDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.manager.SysFeishuManager;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import cn.klmb.crm.module.system.service.permission.SysPermissionService;
import cn.klmb.crm.module.system.service.permission.SysRoleService;
import cn.klmb.crm.module.system.service.permission.SysUserRoleService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
 * 系统管理 - 用户管理(系统)
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@Api(tags = "0101.系统管理-用户管理")
@RestController
@RequestMapping("/sys/user")
@Validated
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysDeptService sysDeptService;
    private final SysPermissionService sysPermissionService;

    private final SysFeishuManager sysFeishuManager;

    private final SysUserRoleService sysUserRoleService;

    public SysUserController(SysUserService sysUserService, SysRoleService sysRoleService,
            SysDeptService sysDeptService,
            SysPermissionService sysPermissionService, SysFeishuManager sysFeishuManager,
            SysUserRoleService sysUserRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.sysDeptService = sysDeptService;
        this.sysPermissionService = sysPermissionService;
        this.sysFeishuManager = sysFeishuManager;
        this.sysUserRoleService = sysUserRoleService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增")
    @PreAuthorize("@ss.hasPermission('system:user:save')")
    public CommonResult<String> save(@Valid @RequestBody SysUserSaveReqVO saveReqVO) {
        SysUserDO saveDO = SysUserConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (sysUserService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @PostMapping(value = "/save-batch")
    @ApiOperation(value = "批量新增")
    @PreAuthorize("@ss.hasPermission('system:user:save')")
    public CommonResult<List<String>> saveBatch(
            @Valid @RequestBody List<SysUserSaveReqVO> saveReqVO) {
        if (CollUtil.isEmpty(saveReqVO)) {
            return success(Collections.emptyList());
        }
        List<SysUserDO> saveDOList = SysUserConvert.INSTANCE.convertList(saveReqVO);
        List<String> bizIds = new ArrayList<>();
        if (sysUserService.saveBatchDO(saveDOList)) {
            bizIds = saveDOList.stream().map(SysUserDO::getBizId).collect(Collectors.toList());
        }
        return success(bizIds);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:user:delete')")
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        sysUserService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "更新")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody SysUserUpdateReqVO updateReqVO) {
        SysUserDO updateDO = SysUserConvert.INSTANCE.convert01(updateReqVO);
        sysUserService.updateDO(updateDO);
        return success(true);
    }

    @PutMapping(value = "/update-pwd")
    @ApiOperation(value = "修改密码")
    @PreAuthorize("@ss.hasPermission('system:user:update-password')")
    public CommonResult<Boolean> updatePassword(
            @Valid @RequestBody SysUserUpdatePwdReqVO updatePwdReqVO) {
        sysUserService.updatePassword(updatePwdReqVO.getBizId(), updatePwdReqVO.getPassword());
        return success(true);
    }

    @PutMapping(value = "/reset-pwd/{bizId}")
    @ApiOperation(value = "重置密码")
    @PreAuthorize("@ss.hasPermission('system:user:update-password')")
    public CommonResult<String> resetPassword(@PathVariable String bizId) {
        return success(sysUserService.resetPassword(bizId));
    }

    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
        sysUserService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping(value = "/detail/{bizId}")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<SysUserRespVO> getByBizId(@PathVariable String bizId) {
        SysUserDO sysUserDO = sysUserService.getByBizId(bizId);
        // todo 角色信息

        return success(SysUserConvert.INSTANCE.convert01(sysUserDO));
    }

//    @GetMapping({"/list-all-simple"})
//    @ApiOperation(value = "列表精简信息")
//    @PermitAll
//    public CommonResult<List<SysUserSimpleRespVO>> listAllSimple(SysUserPageReqVO query) {
//        SysUserQueryDTO queryDTO = SysUserConvert.INSTANCE.convert(query);
//        List<SysUserDO> entities = sysUserService.list(queryDTO);
//        return success(SysUserConvert.INSTANCE.convert01(entities));
//    }

    @GetMapping({"/list-all-simple"})
    @ApiOperation(value = "列表精简信息")
    @PermitAll
    public CommonResult<List<SysUserSimpleRespVO>> listAllSimple(SysUserPageReqVO query) {
        List<String> allUserIds = new ArrayList<>();
        List<SysUserDO> allUserList = new ArrayList<>();
        SysUserQueryDTO queryDTO = SysUserConvert.INSTANCE.convert(query);
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        SysUserDO sysUserDO = sysUserService.getByBizId(userId);
        if (ObjectUtil.isNotNull(sysUserDO)) {
            String deptId = sysUserDO.getDeptId();
            List<String> queryChildDept = sysDeptService.queryChildDept(deptId);
            queryChildDept.add(deptId);
            queryDTO.setDeptIds(queryChildDept);
            allUserList = sysUserService.list(queryDTO);
            if (CollUtil.isNotEmpty(allUserList)) {
                allUserIds = allUserList.stream().map(SysUserDO::getBizId)
                        .collect(Collectors.toList());
            }
            //查询系统内置角色
            List<SysRoleDO> sysRoleDOS = sysRoleService.list(
                    new LambdaQueryWrapper<SysRoleDO>().eq(SysRoleDO::getType, 1)
                            .eq(SysRoleDO::getDeleted, false));
            List<String> collect = null;
            if (CollUtil.isNotEmpty(sysRoleDOS)) {
                collect = sysRoleDOS.stream().map(SysRoleDO::getBizId)
                        .collect(Collectors.toList());
            }
            List<SysUserRoleDO> list = sysUserRoleService.list(
                    new LambdaQueryWrapper<SysUserRoleDO>().in(SysUserRoleDO::getRoleId, collect)
                            .eq(SysUserRoleDO::getDeleted, false));
            if (CollUtil.isNotEmpty(list)) {
                // 获取到拥有内置角色的用户
                List<String> userList = list.stream().map(SysUserRoleDO::getUserId)
                        .collect(Collectors.toList());
                allUserIds = CollUtil.subtractToList(allUserIds, userList);
            }
        }
        if (CollUtil.isNotEmpty(allUserIds)) {
            List<SysUserDO> sysUserDOS = sysUserService.listByBizIds(allUserIds);
            return success(SysUserConvert.INSTANCE.convert01(sysUserDOS));
        }
        return null;
    }


    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<KlmbPage<SysUserRespVO>> page(@Valid SysUserPageReqVO reqVO) {
        KlmbPage<SysUserDO> klmbPage = KlmbPage.<SysUserDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysUserQueryDTO queryDTO = SysUserConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysUserDO> page = sysUserService.page(queryDTO, klmbPage);
        KlmbPage<SysUserRespVO> pageResult = SysUserConvert.INSTANCE.convert(page);
        if (CollUtil.isNotEmpty(pageResult.getContent())) {
            // 获得拼接需要的数据
            Collection<String> deptIds = convertList(pageResult.getContent(),
                    SysUserRespVO::getDeptId);
            Map<String, SysDeptDO> deptMap = sysDeptService.getDeptMap(deptIds);
            List<SysRoleDO> roles = sysRoleService.list();
            Map<String, SysRoleDO> roleMap = CollectionUtils.convertMap(roles, SysRoleDO::getBizId);
            pageResult.getContent().forEach(sysUserRespVO -> {
                // 部门
                SysDeptDO sysDeptDO = deptMap.get(sysUserRespVO.getDeptId());
                if (ObjectUtil.isNotNull(sysDeptDO)) {
                    sysUserRespVO.setDeptName(sysDeptDO.getName());
                }
                // 角色
                Set<String> roleIds = sysPermissionService.getUserRoleIdListByUserId(
                        sysUserRespVO.getBizId());
                if (CollUtil.isNotEmpty(roleIds)) {
                    List<SysRoleDO> userRoles = new ArrayList<>();
                    roleIds.forEach(roleId -> {
                        SysRoleDO sysRoleDO = roleMap.get(roleId);
                        if (ObjectUtil.isNotNull(sysRoleDO)) {
                            userRoles.add(sysRoleDO);
                        }
                    });
                    sysUserRespVO.setRoles(SysRoleConvert.INSTANCE.convert01(userRoles));
                }
            });
        }
        return success(pageResult);
    }


    @GetMapping(value = "/query-child-userId/{userId}")
    @ApiOperation(value = "查询该用户下级的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<List<String>> queryChildUserId(@PathVariable String userId) {
        return success(sysUserService.queryChildUserId(userId));
    }

//    @GetMapping({"/page-scroll"})
//    @ApiOperation(value = "滚动分页查询", notes = "只支持根据bizId顺序进行正、倒序查询", hidden = true)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "lastBizId", value = "业务id", paramType = "query", dataTypeClass = String.class),
//            @ApiImplicitParam(name = "pageSize", value = "每页数量，默认10", paramType = "query", dataTypeClass = Integer.class),
//            @ApiImplicitParam(name = "asc", value = "是否为正序", paramType = "query", dataTypeClass = Boolean.class)})
//    @PreAuthorize("@ss.hasPermission('system:user:query')")
//    public CommonResult<KlmbScrollPage<SysUserRespVO>> pageScroll(
//            @Valid SysUserScrollPageReqVO reqVO) {
//        KlmbScrollPage<SysUserDO> klmbPage = KlmbScrollPage.<SysUserDO>builder()
//                .lastBizId(reqVO.getLastBizId())
//                .pageSize(reqVO.getPageSize())
//                .asc(reqVO.getAsc())
//                .build();
//        SysUserDTO queryDTO = SysUserConvert.INSTANCE.convert(reqVO);
//        KlmbScrollPage<SysUserDO> page = sysUserService.pageScroll(
//                queryDTO, klmbPage);
//        KlmbScrollPage<SysUserRespVO> respPage = new KlmbScrollPage<>();
//        respPage = SysUserConvert.INSTANCE.convert(page);
//        return success(respPage);
//    }


    @GetMapping(value = "/getAccessToken")
    @ApiOperation(value = "获取飞书访问凭证")
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<String> getAccessToken() {
        String accessToken = sysFeishuManager.getAccessToken();
        return CommonResult.success(accessToken);
    }

    @GetMapping(value = "/code2session/{code}")
    @ApiOperation(value = "飞书小程序自建应用的登录获取用户身份")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "登录时获取的 code", dataTypeClass = String.class, paramType = "path")})
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<FeishuMinAppResultDTO> code2session(@PathVariable String code) {
        return CommonResult.success(sysFeishuManager.code2session(code));
    }


}

