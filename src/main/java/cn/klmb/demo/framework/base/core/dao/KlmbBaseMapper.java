package cn.klmb.demo.framework.base.core.dao;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.demo.framework.mybatis.core.mapper.BaseMapperX;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * mapper基类
 *
 * @author liuyuepan
 * @date 2022/04/11
 */
public interface KlmbBaseMapper<TEntity extends KlmbBaseDO, TDTO extends KlmbBaseQueryDTO> extends
        BaseMapperX<TEntity> {

    default Long selectIdByBizId(String bizId) {
        TEntity entity = selectOne(new QueryWrapper<TEntity>().select("id").eq("biz_id", bizId));
        if (ObjectUtil.isNull(entity)) {
            return null;
        }
        return entity.getId();
    }

    default List<Long> selectIdsByBizIds(Collection<String> bizIds) {
        List<TEntity> entities = selectList(
                new QueryWrapper<TEntity>().select("id").in("biz_id", bizIds));
        if (ObjectUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(TEntity::getId).collect(Collectors.toList());
    }

    default TEntity selectByBizId(String bizId) {
        return selectOne("biz_id", bizId);
    }

    default List<TEntity> selectByBizIdIn(Collection<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return Collections.emptyList();
        }
        return selectList("biz_id", bizIds);
    }

}
