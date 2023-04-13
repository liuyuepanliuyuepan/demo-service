package cn.klmb.crm.module.system.controller.admin.test;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.manager.SysFeishuManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author liuyuepan
 * @date 2023/4/13
 */
@Api(tags = "9999. 测试")
@RestController
@RequestMapping("/sys/test")
@Validated
@Slf4j
public class TestController {

    private final SysFeishuManager sysFeishuManager;

    public TestController(SysFeishuManager sysFeishuManager) {
        this.sysFeishuManager = sysFeishuManager;
    }

    @GetMapping("/fs_user_info")
    @ApiOperation("获得飞书单个用户信息")
    @PermitAll
    public CommonResult<Boolean> getFsUserInfo() {
        sysFeishuManager.getUserInfo();
        return success(true);
    }


}
