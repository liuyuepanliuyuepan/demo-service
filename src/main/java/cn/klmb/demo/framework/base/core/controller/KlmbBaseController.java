//package cn.klmb.demo.framework.base.core.controller;
//
//import static cn.klmb.demo.framework.common.pojo.CommonResult.success;
//
//import cn.hutool.core.util.NumberUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
//import cn.klmb.demo.framework.base.core.pojo.KlmbBaseDTO;
//import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
//import cn.klmb.demo.framework.base.core.pojo.KlmbScrollPage;
//import cn.klmb.demo.framework.base.core.service.KlmbBaseService;
//import cn.klmb.demo.framework.common.pojo.CommonResult;
//import java.util.Collections;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// * controller基类
// *
// * @author liuyuepan
// * @date 2022/6/20
// */
//@Slf4j
//public class KlmbBaseController<TEntity extends KlmbBaseDO, TDTO extends KlmbBaseDTO, TService extends KlmbBaseService<TEntity, TDTO>> {
//
//    protected TService service;
//
//    public CommonResult<String> save(@RequestBody TDTO formDTO) {
//        TDTO tFormDTO = service.saveDTO(formDTO);
//        String bizId = null;
//        if (ObjectUtil.isNotNull(tFormDTO)) {
//            bizId = tFormDTO.getBizId();
//        }
//        return success(bizId);
//    }
//
//    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
//        service.deleteByBizIds(Collections.singletonList(bizId));
//        return success(true);
//    }
//
//    public CommonResult<Boolean> update(@RequestBody TDTO formDTO) {
//        service.updateDTO(formDTO);
//        return success(true);
//    }
//
//    public CommonResult<TDTO> getByBizId(@PathVariable String bizId) {
//        return success(service.findDtoByBizId(bizId));
//    }
//
//    public CommonResult<List<TDTO>> list(TDTO condition) {
//        List<TDTO> entities = service.listDTO(condition);
//        return success(entities);
//    }
//
//    public CommonResult<KlmbPage<TDTO>> page(TDTO condition,
//            @RequestParam(name = "pageNo", defaultValue = "1", required = false) Integer pageNo,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
//        if (NumberUtil.compare(pageNo, 1) == -1) {
//            // todo
////            return CommonResult.error(ErrorCodeEnum.CUSTOM.getValue(), "页码参数不能小于1！");
//        }
//        KlmbPage<TDTO> klmbPage = KlmbPage.<TDTO>builder()
//                .pageNo(pageNo)
//                .pageSize(pageSize)
//                .build();
//        return success(service.page(condition, klmbPage));
//    }
//
//    public CommonResult<KlmbScrollPage<TDTO>> pageScroll(TDTO condition,
//            @RequestParam(name = "lastBizId", required = false) String lastBizId,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
//            @RequestParam(name = "asc", defaultValue = "true", required = false) Boolean asc) {
//        KlmbScrollPage<TDTO> klmbPage = KlmbScrollPage.<TDTO>builder()
//                .lastBizId(lastBizId)
//                .pageSize(pageSize)
//                .asc(asc)
//                .build();
//        return success(service.pageScroll(condition, klmbPage));
//    }
//
//}
