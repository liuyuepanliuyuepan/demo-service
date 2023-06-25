package cn.klmb.demo.framework.security.core.aop;

import static cn.klmb.demo.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.klmb.demo.framework.security.core.annotations.PreAuthenticated;
import cn.klmb.demo.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated)
            throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw exception(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
