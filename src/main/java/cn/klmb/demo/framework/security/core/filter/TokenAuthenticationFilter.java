package cn.klmb.demo.framework.security.core.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.demo.framework.common.exception.ServiceException;
import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.framework.common.util.servlet.ServletUtils;
import cn.klmb.demo.framework.security.config.SecurityProperties;
import cn.klmb.demo.framework.security.core.LoginUser;
import cn.klmb.demo.framework.security.core.util.SecurityFrameworkUtils;
import cn.klmb.demo.framework.web.core.handler.GlobalExceptionHandler;
import cn.klmb.demo.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import cn.klmb.demo.module.system.service.oauth2.SysOAuth2TokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Token 过滤器，验证 token 的有效性 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 *
 * @author 快乐萌宝
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final GlobalExceptionHandler globalExceptionHandler;

    private final SysOAuth2TokenService sysOAuth2TokenService;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader());
        if (StrUtil.isNotEmpty(token)) {
            Integer userType = WebFrameworkUtils.getLoginUserType(request);
            try {
                // 1.1 基于 token 构建登录用户
                LoginUser loginUser = buildLoginUserByToken(token, userType);
                // 2. 设置当前用户
                if (loginUser != null) {
                    SecurityFrameworkUtils.setLoginUser(loginUser, request);
                }
            } catch (Throwable ex) {
                CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                ServletUtils.writeJSON(response, result);
                return;
            }
        }

        // 继续过滤链
        chain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByToken(String token, Integer userType) {
        try {
            SysOAuth2AccessTokenDO accessToken = sysOAuth2TokenService.checkAccessToken(token);
            if (ObjectUtil.isNull(accessToken)) {
                return null;
            }
            // 用户类型不匹配，无权限
            if (ObjectUtil.notEqual(accessToken.getUserType(), userType)) {
                throw new AccessDeniedException("错误的用户类型");
            }
            // 构建登录用户
            return new LoginUser().setId(accessToken.getUserId())
                    .setUserType(accessToken.getUserType()).setScopes(accessToken.getScopes());
        } catch (ServiceException serviceException) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }

}
