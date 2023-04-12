package cn.klmb.crm.module.member.controller.admin.instrument;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.common.pojo.BiParams;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmInstrumentVO;
import cn.klmb.crm.module.member.service.instrument.CrmInstrumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CRM仪表盘
 *
 * @author liuyuepan
 * @date 2023/4/11
 */
@Api(tags = "0501.CRM仪表盘")
@RestController
@RequestMapping("/crmInstrument")
@Validated
public class CrmInstrumentController {

    private final CrmInstrumentService crmInstrumentService;

    public CrmInstrumentController(CrmInstrumentService crmInstrumentService) {
        this.crmInstrumentService = crmInstrumentService;
    }

    //销售简报(新增客户、新增联系人、新增跟进记录)

    @GetMapping(value = "/query-bulletin")
    @ApiOperation(value = "销售简报")
    @PreAuthorize("@ss.hasPermission('member:/crm-instrument:query')")
    public CommonResult<CrmInstrumentVO> queryBulletin(BiParams biParams) {
        return success(crmInstrumentService.queryBulletin(biParams));
    }

    //客户汇总(新增客户、转成交客户、放入公海的客户、公海池领取的)

    //跟进汇总(跟进客户数、)


}
