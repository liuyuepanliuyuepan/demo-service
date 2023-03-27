package cn.klmb.crm.framework.base.core.service;

import cn.hutool.core.lang.tree.Tree;
import cn.klmb.crm.framework.base.core.entity.KlmbBaseTreeDO;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseTreeQueryDTO;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseTreeSaveDTO;
import java.util.Collection;
import java.util.List;

/**
 * tree service基类
 *
 * @author shilinchuan
 * @date 2022/04/13
 */
public interface KlmbBaseTreeService<TEntity extends KlmbBaseTreeDO<TEntity>, TDTO extends KlmbBaseTreeQueryDTO<TDTO>> extends
        KlmbBaseService<TEntity, TDTO> {

    /**
     * 根据treeId查询
     *
     * @param treeId 树id
     * @return 实体
     */
    TEntity findByTreeId(String treeId);

    /**
     * 根据treeId列表查询
     *
     * @param treeIds 树id列表
     * @return 实体列表
     */
    List<TEntity> findByTreeIdIn(Collection<String> treeIds);

    /**
     * 查询子树(包括当前节点)
     *
     * @param query 查询条件 treeId 当前树节点 treeDepth 查询深度 ... 其他查询条件
     * @return 树
     */
    List<Tree<String>> findSubTree(TDTO query);

    /**
     * 根据树节点id查询所有上级节点列表
     *
     * @param treeId         树节点id
     * @param includeCurrent 是否包含当前节点
     * @return 表单列表
     */
    List<TEntity> findUpList(String treeId, boolean includeCurrent);

    /**
     * 根据树节点id查询所有下级节点列表(包括当前节点)
     *
     * @param treeId         树节点id
     * @param includeCurrent 是否包含当前节点
     * @return 树列表
     */
    List<TEntity> findSubList(String treeId, boolean includeCurrent);

    /**
     * 保存多级树
     *
     * @param tree 多级树
     * @return 新增表单列表
     */
    List<TEntity> saveTree(KlmbBaseTreeSaveDTO<TEntity> tree);

}

