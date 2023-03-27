//package cn.klmb.crm.framework.base.core.controller;
//
//
//import static cn.klmb.crm.framework.common.pojo.CommonResult.success;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.lang.tree.Tree;
//import cn.hutool.core.map.MapUtil;
//import cn.klmb.crm.framework.base.core.entity.KlmbBaseTreeDO;
//import cn.klmb.crm.framework.base.core.pojo.KlmbBaseTreeDTO;
//import cn.klmb.crm.framework.base.core.pojo.KlmbBaseTreeMoveDTO;
//import cn.klmb.crm.framework.base.core.service.KlmbBaseTreeService;
//import cn.klmb.crm.framework.common.pojo.CommonResult;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// * tree controller基类
// *
// * @author shilinchuan
// * @date 2022/04/13
// */
//@Slf4j
//public class KlmbBaseTreeController<TEntity extends KlmbBaseTreeDO<TEntity>,
//        TDTO extends KlmbBaseTreeDTO<TDTO>,
//        TService extends KlmbBaseTreeService<TEntity, TDTO>>
//        extends KlmbBaseController<TEntity, TDTO, TService> {
//
//    public CommonResult<List<Tree<String>>> findTree(@RequestParam TDTO condition) {
//        List<Tree<String>> entities = service.findSubTree(condition);
//        this.removeNullValue(entities);
//        return success(entities);
//    }
//
//    public CommonResult<Boolean> moveTree(@RequestBody KlmbBaseTreeMoveDTO treeMove) {
//        return success(service.moveTree(treeMove));
//    }
//
//    /**
//     * 去掉null键值
//     *
//     * @param treeList 树列表
//     */
//    private void removeNullValue(List<Tree<String>> treeList) {
//        if (CollUtil.isEmpty(treeList)) {
//            return;
//        }
//        treeList.forEach(tree -> {
//            MapUtil.removeNullValue(tree);
//            this.removeNullValue(tree.getChildren());
//        });
//    }
//
//}
