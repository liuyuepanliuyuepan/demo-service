package cn.klmb.demo.framework.base.core.dao;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseTreeDO;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Collection;
import java.util.List;

/**
 * tree mapper基类
 *
 * @author liuyuepan
 * @date 2022/04/11
 */
public interface KlmbBaseTreeMapper<TEntity extends KlmbBaseTreeDO<TEntity>, TDTO extends KlmbBaseQueryDTO> extends
        KlmbBaseMapper<TEntity, TDTO> {

    default TEntity selectByTreeId(String treeId) {
        return selectOne("tree_id", treeId);
    }

    default List<TEntity> selectByTreeIdIn(Collection<String> treeIds) {
        return selectList("tree_id", treeIds);
    }

    default TEntity selectByTreeParentIdAndTreeName(String treeParentId, String treeName) {
        return selectOne(new QueryWrapper<TEntity>()
                .eq("tree_parent_id", treeParentId)
                .eq("tree_name", treeName));
    }

    default Long selectCountByTreeParentId(String treeParentId) {
        return selectCount("tree_parent_id", treeParentId);
    }

}
