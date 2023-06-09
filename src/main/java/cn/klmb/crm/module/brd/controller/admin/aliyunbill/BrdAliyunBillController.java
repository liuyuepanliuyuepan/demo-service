package cn.klmb.crm.module.brd.controller.admin.aliyunbill;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.UpdateStatusReqVO;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.*;
import cn.klmb.crm.module.brd.convert.aliyunbill.BrdAliyunBillConvert;
import cn.klmb.crm.module.brd.dto.aliyunbill.BrdAliyunBillQueryDTO;
import cn.klmb.crm.module.brd.entity.aliyunbill.BrdAliyunBillDO;
import cn.klmb.crm.module.brd.service.aliyunbill.BrdAliyunBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
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
 * 博瑞迪阿里云账单 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "8001.博瑞迪阿里云账单")
@RestController
@RequestMapping("/brd/aliyun-bill")
@Validated
public class BrdAliyunBillController {

	private static final String brdKey = "f4ce6381fedd2a1ae17c52c9d9030372";

	private final BrdAliyunBillService brdAliyunBillService;

	public BrdAliyunBillController(BrdAliyunBillService brdAliyunBillService) {
		this.brdAliyunBillService = brdAliyunBillService;
	}

	@PostMapping(value = "/save")
	@ApiOperation(value = "新增")
	@PreAuthorize("@ss.hasPermission('brd:aliyun-bill:save')")
	public CommonResult<String> save(@Valid @RequestBody BrdAliyunBillSaveReqVO saveReqVO) {
		BrdAliyunBillDO saveDO = BrdAliyunBillConvert.INSTANCE.convert(saveReqVO);
		String bizId = "";
		if (brdAliyunBillService.saveDO(saveDO)) {
			bizId = saveDO.getBizId();
		}
		return success(bizId);
	}

	@DeleteMapping(value = "/delete/{bizId}")
	@ApiOperation(value = "删除")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
	@PreAuthorize("@ss.hasPermission('brd:aliyun-bill:delete')")
	public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
		brdAliyunBillService.removeByBizIds(Collections.singletonList(bizId));
		return success(true);
	}

	@PutMapping(value = "/update")
	@ApiOperation(value = "更新")
	@PreAuthorize("@ss.hasPermission('brd:aliyun-bill:update')")
	public CommonResult<Boolean> update(@Valid @RequestBody BrdAliyunBillUpdateReqVO updateReqVO) {
		BrdAliyunBillDO updateDO = BrdAliyunBillConvert.INSTANCE.convert(updateReqVO);
		brdAliyunBillService.updateDO(updateDO);
		return success(true);
	}

	@PutMapping("/update-status")
	@ApiOperation("修改状态")
	@PreAuthorize("@ss.hasPermission('brd:aliyun-bill:update')")
	public CommonResult<Boolean> updateStatus(@Valid @RequestBody UpdateStatusReqVO reqVO) {
		brdAliyunBillService.updateStatus(reqVO.getBizId(), reqVO.getStatus());
		return success(true);
	}

	@GetMapping(value = "/detail/{bizId}")
	@ApiOperation(value = "详情")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "bizId", value = "业务id", dataTypeClass = String.class, paramType = "path")})
	@PreAuthorize("@ss.hasPermission('brd:aliyun-bill:query')")
	public CommonResult<BrdAliyunBillRespVO> getByBizId(@PathVariable String bizId) {
		BrdAliyunBillDO saveDO = brdAliyunBillService.getByBizId(bizId);
		return success(BrdAliyunBillConvert.INSTANCE.convert(saveDO));
	}

	@GetMapping({"/page"})
	@ApiOperation(value = "分页查询")
	@PreAuthorize("@ss.hasPermission('brd:aliyun-bill:query')")
	public CommonResult<KlmbPage<BrdAliyunBillRespVO>> page(@Valid BrdAliyunBillPageReqVO reqVO) {
		KlmbPage<BrdAliyunBillDO> klmbPage = KlmbPage.<BrdAliyunBillDO>builder()
			.pageNo(reqVO.getPageNo())
			.pageSize(reqVO.getPageSize())
			.build();
		BrdAliyunBillQueryDTO queryDTO = BrdAliyunBillConvert.INSTANCE.convert(reqVO);
		KlmbPage<BrdAliyunBillDO> page = brdAliyunBillService.page(queryDTO, klmbPage);
		return success(BrdAliyunBillConvert.INSTANCE.convert(page));
	}

	@GetMapping(value = "/queryInstanceBill")
	@ApiOperation(value = "账单明细")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "key", value = "鉴权key", dataTypeClass = String.class, paramType = "query", required = true),
		@ApiImplicitParam(name = "billingCycle", value = "账期YYYY-MM", dataTypeClass = String.class, paramType = "query", required = true),
		@ApiImplicitParam(name = "billingDate", value = "账单日期YYYY-MM-DD", dataTypeClass = String.class, paramType = "query", required = true)
	})
	@PermitAll
	public CommonResult<List<BrdBillDetailRespVO>> queryInstanceBill(
		@NotNull(message = "鉴权key不能为空") String key,
		@NotNull(message = "账期字段为必填项") String billingCycle,
		@NotNull(message = "账单日期字段为必填项") String billingDate) {
		if (!Objects.equals(brdKey, key)) {
			return CommonResult.error(500, "鉴权key不正确请检查");
		}
		List<BrdBillDetailRespVO> list = brdAliyunBillService
			.getBrdBillDetail(billingCycle, billingDate);
		return success(list);
	}

	@GetMapping(value = "/listCost")
	@ApiOperation(value = "成本统计")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "key", value = "鉴权key", dataTypeClass = String.class, paramType = "query", required = true),
		@ApiImplicitParam(name = "periodType", value = "时间粒度,月：MONTH 天：DAY", dataTypeClass = String.class, paramType = "query", required = true),
		@ApiImplicitParam(name = "startPeriod", value = "开始日期：YYYY-MM-DD", dataTypeClass = String.class, paramType = "query", required = true),
		@ApiImplicitParam(name = "endPeriod", value = "截止日期：YYYY-MM-DD", dataTypeClass = String.class, paramType = "query", required = true)
	})
	@PermitAll
	public CommonResult<BrdBillCostVO> listCost(@NotNull(message = "鉴权key不能为空") String key,
		@NotNull(message = "时间粒度为必填项") String periodType,
		@NotNull(message = "开始日期字段为必填项") String startPeriod,
		@NotNull(message = "结束日期字段为必填项") String endPeriod
	) {
		if (!Objects.equals(brdKey, key)) {
			return CommonResult.error(500, "鉴权key不正确请检查");
		}
		BrdBillCostVO brdBillCostVO = brdAliyunBillService
			.getCostList(periodType, startPeriod, endPeriod);
		return success(brdBillCostVO);
	}

}
