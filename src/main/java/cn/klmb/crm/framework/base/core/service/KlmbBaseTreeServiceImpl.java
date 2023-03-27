package cn.klmb.crm.framework.base.core.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.config.TreeConfig;
import cn.klmb.crm.framework.base.core.constant.TreeConstant;
import cn.klmb.crm.framework.base.core.dao.KlmbBaseTreeMapper;
import cn.klmb.crm.framework.base.core.entity.KlmbBaseTreeDO;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseTreeQueryDTO;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseTreeSaveDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * tree serviceImpl基类
 *
 * @author shilinchuan
 * @date 2022/04/13
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KlmbBaseTreeServiceImpl<TEntity extends KlmbBaseTreeDO<TEntity>, TDTO extends KlmbBaseTreeQueryDTO<TDTO>, TMapper extends KlmbBaseTreeMapper<TEntity, TDTO>> extends
        KlmbBaseServiceImpl<TEntity, TDTO, TMapper> implements KlmbBaseTreeService<TEntity, TDTO> {

    @Override
    public boolean saveDO(TEntity entity) {
        TEntity parent = null;
        if (StrUtil.isNotBlank(entity.getTreeParentId())) {
            parent = mapper.selectByTreeId(entity.getTreeParentId());
        }

        String treeId = IdUtil.fastSimpleUUID();
        entity.setBizId(treeId);
        entity.setTreeId(treeId);
        if (parent != null) {
            // 存在父节点
            entity.setTreeParentId(parent.getTreeId());
            entity.setTreePath(parent.getTreePath() + treeId + TreeConstant.PATH_SEPARATOR);
            entity.setTreeDepth(
                    (ObjectUtil.isNull(parent.getTreeDepth()) ? 0 : parent.getTreeDepth()) + 1);
        } else {
            // 不存在父节点
            entity.setTreeParentId(TreeConstant.ROOT_NODE);
            entity.setTreePath(treeId + TreeConstant.PATH_SEPARATOR);
            entity.setTreeDepth(1);
        }
        if (Objects.isNull(entity.getSort())) {
            entity.setSort(TreeConstant.INCR_STEP);
        }

        return super.save(entity);
    }

    @Override
    public boolean updateDO(TEntity entity) {
        if (StrUtil.isBlank(entity.getTreeId())) {
            entity.setTreeId(entity.getBizId());
        }
        if (StrUtil.isBlank(entity.getTreeId())) {
            return false;
        }
        TEntity currentEntity = mapper.selectByTreeId(entity.getTreeId());
        if (ObjectUtil.isNull(currentEntity)) {
            return true;
        }
        List<TEntity> updateList = new ArrayList<>();
        // 父节点发生变化
        if (ObjectUtil.notEqual(entity.getTreeParentId(), currentEntity.getTreeParentId())) {
            // 旧path路径
            String oldParentPath = currentEntity.getTreePath();
            // 如果修改了父节点，修改treePath，treeDepth
            TEntity targetTreeNode = this.findByTreeId(entity.getTreeParentId());
            if (ObjectUtil.isNotNull(targetTreeNode)) {
                // 存在目标节点
                if (targetTreeNode.getTreePath().contains(currentEntity.getTreePath())) {
                    // 判断新父节点是否是当前节点的子节点，如果是，不能移动
                    return true;
                }
                currentEntity.setTreeParentId(entity.getTreeParentId());
                currentEntity.setTreePath(targetTreeNode.getTreePath() + entity.getTreeId()
                        + TreeConstant.PATH_SEPARATOR);
            } else {
                // 不存在目标节点
                currentEntity.setTreeParentId(TreeConstant.ROOT_NODE);
                currentEntity.setTreePath(currentEntity.getTreeId() + TreeConstant.PATH_SEPARATOR);
            }
            currentEntity.setTreeDepth(
                    StrUtil.count(currentEntity.getTreePath(), TreeConstant.PATH_SEPARATOR));
            // 获取所有子节点并修改信息
            List<TEntity> subList = this.findSubList(currentEntity.getTreeId(), true);
            if (CollUtil.isNotEmpty(subList) && subList.size() > 1) {
                String newParentPath = currentEntity.getTreePath();
                subList.forEach(sub -> {
                    if (ObjectUtil.equal(sub.getTreeId(), currentEntity.getTreeId())) {
                        return;
                    }
                    sub.setTreePath(
                            StrUtil.replace(sub.getTreePath(), oldParentPath, newParentPath));
                    sub.setTreeDepth(StrUtil.count(sub.getTreePath(), TreeConstant.PATH_SEPARATOR));
                    updateList.add(sub);
                });
            }
        }
        BeanUtil.copyProperties(entity, currentEntity, CopyOptions.create().ignoreNullValue()
                .setIgnoreProperties("id", "bizId", "createTime", "updateTime", "treeId"));
        updateList.add(currentEntity);
        return super.updateBatchById(updateList);
    }

    @Override
    public TEntity findByTreeId(String treeId) {
        return mapper.selectByTreeId(treeId);
    }

    @Override
    public List<TEntity> findByTreeIdIn(Collection<String> treeIds) {
        if (CollUtil.isEmpty(treeIds)) {
            return Collections.emptyList();
        }
        return mapper.selectByTreeIdIn(treeIds);
    }

    @Override
    public List<TEntity> list(TDTO query) {
        Integer rootTreeDepth = 0;
        TEntity entity = this.findByTreeId(query.getTreeId());
        if (ObjectUtil.isNotNull(entity)) {
            query.setTreePath(entity.getTreePath());
            rootTreeDepth = entity.getTreeDepth();
        }
        if (ObjectUtil.isNotNull(query.getTreeDepth())) {
            query.setTreeDepthMin(rootTreeDepth);
            query.setTreeDepthMax(rootTreeDepth + query.getTreeDepth());
        }
        query.setBizId(null);
        query.setTreeId(null);
        query.setTreeParentId(null);
        query.setTreeDepth(null);

        // 根据条件查询列表
        return super.list(query);
    }

    @Override
    public List<Tree<String>> findSubTree(TDTO query) {
        String rootNodeId = TreeConstant.ROOT_NODE;
        Integer rootTreeDepth = 0;
        TEntity entity = this.findByTreeId(query.getTreeId());
        if (ObjectUtil.isNotNull(entity)) {
            rootNodeId = entity.getTreeParentId();
            query.setTreePath(entity.getTreePath());
            rootTreeDepth = entity.getTreeDepth();
        }
        if (ObjectUtil.isNotNull(query.getTreeDepth())) {
            query.setTreeDepthMin(rootTreeDepth);
            query.setTreeDepthMax(rootTreeDepth + query.getTreeDepth());
        }
        query.setBizId(null);
        query.setTreeId(null);
        query.setTreeParentId(null);
        query.setTreeDepth(null);

        // 根据条件查询列表
        List<TEntity> entities = this.list(query);
        if (CollUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }

        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        entities.forEach(e -> {
            TreeNode<String> treeNode = new TreeNode<>(e.getTreeId(),
                    e.getTreeParentId(),
                    e.getTreeName(), e.getSort());
            // 是否返回表单数据
            treeNode.setExtra(MapUtil.of("data", e));
            nodeList.add(treeNode);
        });

        return TreeUtil.build(nodeList, rootNodeId,
                TreeConfig.getConfig(), this::nodeParser);
    }

    @Override
    public List<TEntity> findUpList(String treeId, boolean includeCurrent) {
        TEntity entity = this.findByTreeId(treeId);
        if (ObjectUtil.isNull(entity)) {
            return Collections.emptyList();
        }
        List<TEntity> upTreeList = new ArrayList<>();
        if (BooleanUtil.isTrue(includeCurrent)) {
            upTreeList.add(entity);
        }
        String treePath = StrUtil.removeAll(entity.getTreePath(), treeId);
        List<String> upTreeIdList = StrUtil.splitTrim(treePath, TreeConstant.PATH_SEPARATOR);
        List<TEntity> treeList = this.findByTreeIdIn(upTreeIdList);
        if (CollUtil.isNotEmpty(treeList)) {
            upTreeList.addAll(treeList);
        }
        return upTreeList;
    }

    @Override
    public List<TEntity> findSubList(String treeId, boolean includeCurrent) {
        TEntity entity = this.findByTreeId(treeId);
        if (ObjectUtil.isNull(entity)) {
            return Collections.emptyList();
        }
        // 根据条件查询列表
        TDTO query = BeanUtils.instantiateClass(targetDTOClazz);
        query.setTreePath(entity.getTreePath());
        List<TEntity> list = super.list(query);
        if (!includeCurrent) {
            list.removeIf(node -> ObjectUtil.equal(treeId, node.getTreeId()));
        }
        return list;
    }

    @Override
    public List<TEntity> saveTree(KlmbBaseTreeSaveDTO<TEntity> tree) {
        if (ObjectUtil.isEmpty(tree.getTreeId())) {
            tree.setTreeId(TreeConstant.ROOT_NODE);
        }
        List<TEntity> retList = new ArrayList<>();
        this.saveSubTree(tree.getTreeId(), tree.getSaveMultiTree(), retList);
        return retList;
    }

    /**
     * 根据业务id列表删除(逻辑删除)
     *
     * @param bizIds 业务id列表，删除当前及子节点
     */
    @Override
    public void removeByBizIds(List<String> bizIds) {
        super.removeByBizIds(this.findRemoveBizIds(bizIds));
    }

    /**
     * 自定义转换器，定义需要转换返回的字段信息 treeNode.getExtra().get("data") 中存放实体信息，默认不返回所有实体信息，需要返回请重写此方法
     *
     * @param treeNode 树节点
     * @param tree     树
     */
    protected void nodeParser(TreeNode<String> treeNode, Tree<String> tree) {
        tree.setId(treeNode.getId());
        tree.setParentId(treeNode.getParentId());
        tree.setWeight(treeNode.getWeight());
        tree.setName(treeNode.getName());
    }

    /**
     * 添加子树
     *
     * @param treeId  树id
     * @param tree    树结构数据
     * @param retList 添加实体结果列表
     */
    protected void saveSubTree(String treeId, List<TEntity> tree, List<TEntity> retList) {
        if (CollUtil.isEmpty(tree)) {
            return;
        }
        // 查询原有的节点并排序到最后
        TDTO query = BeanUtils.instantiateClass(targetDTOClazz);
        query.setTreeParentId(treeId);
        List<TEntity> existNodeList = this.list(query);

        int i = TreeConstant.INCR_STEP;
        for (TEntity node : tree) {
            node.setTreeParentId(treeId);
            node.setSort(i);
            i += TreeConstant.INCR_STEP;
            this.save(node);
            retList.add(node);
            this.saveSubTree(node.getTreeId(), node.getChildren(), retList);
        }

        if (CollUtil.isNotEmpty(existNodeList)) {
            for (TEntity node : existNodeList) {
                node.setSort(i);
                i += TreeConstant.INCR_STEP;
            }
            this.updateBatchById(existNodeList);
        }
    }

    /**
     * 获取删除需要的所有业务id列表
     *
     * @param bizIds 业务id列表
     * @return 需要删除的业务id列表
     */
    private List<String> findRemoveBizIds(List<String> bizIds) {
        List<TEntity> entities = super.listByBizIds(bizIds);
        if (CollUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        Set<TEntity> deleteSet = new HashSet<>();
        List<TEntity> subList;
        for (TEntity entity : entities) {
            subList = this.findSubList(entity.getTreeId(), true);
            if (CollUtil.isEmpty(subList)) {
                continue;
            }
            deleteSet.addAll(subList);
        }
        if (CollUtil.isEmpty(deleteSet)) {
            return Collections.emptyList();
        }
        return deleteSet.stream().map(TEntity::getBizId).collect(Collectors.toList());
    }

}