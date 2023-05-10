package cn.klmb.crm.module.member.controller.admin.instrument;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.common.pojo.BiParams;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmCountRankVO;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmInstrumentVO;
import cn.klmb.crm.module.member.service.instrument.CrmInstrumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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

    @GetMapping(value = "/query-bulletin")
    @ApiOperation(value = "销售简报")
    @PreAuthorize("@ss.hasPermission('member:crm-instrument:query')")
    public CommonResult<CrmInstrumentVO> queryBulletin(BiParams biParams) {
        return success(crmInstrumentService.queryBulletin(biParams));
    }

    //客户汇总(新增客户、转成交客户、放入公海的客户、公海池领取的)

    //跟进汇总(跟进客户数、)

    @ApiOperation(value = "新增排行榜(客户,联系人，跟进记录)", notes = "必传字段dataType、endTime、rankType、startTime")
    @GetMapping("/count-rank")
    @PreAuthorize("@ss.hasPermission('member:crm-instrument:query')")
    public CommonResult<List<CrmCountRankVO>> countRank(BiParams biParams) {
        List<CrmCountRankVO> list = crmInstrumentService.countRank(biParams);
        return success(list);
    }


    @GetMapping("/query-bulletin-info")
    @ApiOperation(value = "查询销售简报详情", notes = "必传字段dataType、endTime、label、queryType、startTime、pageNo、pageSize、search")
    @PreAuthorize("@ss.hasPermission('member:crm-instrument:query')")
    public CommonResult<KlmbPage<?>> queryBulletinInfo(BiParams biParams) {
        KlmbPage<?> page = crmInstrumentService.queryBulletinInfo(biParams);
        return success(page);
    }

//    @GetMapping("/query-data-info")
//    @ApiOperation("数据汇总")
//    @PreAuthorize("@ss.hasPermission('member:crm-instrument:query')")
//    public  CommonResult<CrmDataSummaryVO> queryDataInfo(BiParams biParams) {
//        JSONObject jsonObject = instrumentService.queryDataInfo(biParams);
//        return success();
//    }


}
