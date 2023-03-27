package cn.klmb.crm.module.system.controller.admin.auth;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;
import static cn.klmb.crm.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.klmb.crm.framework.security.core.util.SecurityFrameworkUtils.obtainAuthorization;
import static java.util.Collections.singleton;

import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.common.util.collection.SetUtils;
import cn.klmb.crm.framework.security.config.SecurityProperties;
import cn.klmb.crm.module.system.controller.admin.auth.vo.SysAuthLoginReqVO;
import cn.klmb.crm.module.system.controller.admin.auth.vo.SysAuthLoginRespVO;
import cn.klmb.crm.module.system.controller.admin.auth.vo.SysAuthMenuRespVO;
import cn.klmb.crm.module.system.controller.admin.auth.vo.SysAuthPermissionInfoRespVO;
import cn.klmb.crm.module.system.convert.auth.SysAuthConvert;
import cn.klmb.crm.module.system.entity.permission.SysMenuDO;
import cn.klmb.crm.module.system.entity.permission.SysRoleDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.permission.SysMenuTypeEnum;
import cn.klmb.crm.module.system.service.auth.SysAuthService;
import cn.klmb.crm.module.system.service.permission.SysPermissionService;
import cn.klmb.crm.module.system.service.permission.SysRoleService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Set;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录认证
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
@Api(tags = "0000. 登录认证")
@RestController
@RequestMapping("/sys/auth")
@Validated
@Slf4j
public class SysAuthController {

    private final SysAuthService sysAuthService;
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysPermissionService sysPermissionService;
    private final SecurityProperties securityProperties;

    public SysAuthController(SysAuthService sysAuthService, SysUserService sysUserService,
            SysRoleService sysRoleService, SysPermissionService sysPermissionService,
            SecurityProperties securityProperties) {
        this.sysAuthService = sysAuthService;
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.sysPermissionService = sysPermissionService;
        this.securityProperties = securityProperties;
    }

    @PostMapping("/login")
    @ApiOperation(value = "使用账号密码登录")
    @PermitAll
    public CommonResult<SysAuthLoginRespVO> login(@RequestBody @Valid SysAuthLoginReqVO reqVO) {
        return success(sysAuthService.login(reqVO));
    }

    @PostMapping("/logout")
    @ApiOperation("登出系统")
    @PermitAll
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            sysAuthService.logout(token);
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @ApiOperation("刷新令牌")
    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", required = true, dataTypeClass = String.class)
    @PermitAll
    public CommonResult<SysAuthLoginRespVO> refreshToken(
            @RequestParam("refreshToken") String refreshToken) {
        return success(sysAuthService.refreshToken(refreshToken));
    }

    @GetMapping("/get-permission-info")
    @ApiOperation("获取登录用户的权限信息")
    public CommonResult<SysAuthPermissionInfoRespVO> getPermissionInfo() {
        // 获得用户信息
        SysUserDO user = sysUserService.getByBizId(getLoginUserId());
        if (user == null) {
            return null;
        }
        // 获得角色列表
        Set<String> roleIds = sysPermissionService.getUserRoleIds(getLoginUserId(),
                singleton(CommonStatusEnum.ENABLE.getStatus()));
        List<SysRoleDO> roleList = sysRoleService.listByBizIds(roleIds);
        // 获得菜单列表
        List<SysMenuDO> menuList = sysPermissionService.getRoleMenuList(roleIds,
                SetUtils.asSet(SysMenuTypeEnum.DIR.getType(), SysMenuTypeEnum.MENU.getType(),
                        SysMenuTypeEnum.BUTTON.getType()),
                singleton(CommonStatusEnum.ENABLE.getStatus())); // 只要开启的
        // 拼接结果返回
        return success(SysAuthConvert.INSTANCE.convert(user, roleList, menuList));
    }

    @GetMapping("/list-menus")
    @ApiOperation("获得登录用户的菜单列表")
    public CommonResult<List<SysAuthMenuRespVO>> getMenus() {
        // 获得角色列表
        Set<String> roleIds = sysPermissionService.getUserRoleIds(getLoginUserId(),
                singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 获得用户拥有的菜单列表
        List<SysMenuDO> menuList = sysPermissionService.getRoleMenuList(roleIds,
                SetUtils.asSet(SysMenuTypeEnum.DIR.getType(), SysMenuTypeEnum.MENU.getType()),
                // 只要目录和菜单类型
                singleton(CommonStatusEnum.ENABLE.getStatus())); // 只要开启的
        // 转换成 Tree 结构返回
        return success(SysAuthConvert.INSTANCE.buildMenuTree(menuList));
    }

}
