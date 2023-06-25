package cn.klmb.demo.framework.base.core.service;

import cn.hutool.core.util.ObjectUtil;
import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.demo.framework.common.util.collection.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * service基类
 *
 * @author liuyuepan
 * @date 2022/04/11
 */
public interface KlmbBaseService<TEntity extends KlmbBaseDO, TDTO extends KlmbBaseQueryDTO> extends
        IService<TEntity> {

    /**
     * 提供给子类实现自己的业务
     * <p>
     * 直接调用了 {@link IService#save}
     *
     * @param saveDO 实体
     * @return 实体
     */
    boolean saveDO(TEntity saveDO);

    /**
     * 提供给子类实现自己的业务
     * <p>
     * 直接调用了 {@link IService#saveBatch}
     *
     * @param saveDOList 实体列表
     * @return 实体
     */
    boolean saveBatchDO(Collection<TEntity> saveDOList);

    /**
     * 提供给子类实现自己的业务，根据需要实现
     * <p>
     * {@link KlmbBaseDO#id} 存在则调用 {@link IService#updateById}
     * <p>
     * {@link KlmbBaseDO#bizId} 存在则调用 {@link KlmbBaseService#updateByBizId}
     *
     * @param saveDO 实体
     * @return 实体
     */
    boolean updateDO(TEntity saveDO);

    default boolean updateByBizId(TEntity entity) {
        Long id = this.getIdByBizId(entity.getBizId());
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        entity.setId(id);
        return this.updateById(entity);
    }

    default boolean removeByBizId(String bizId) {
        return this.removeById(this.getIdByBizId(bizId));
    }

    default boolean removeByBizId(TEntity entity) {
        entity = this.getByBizId(entity.getBizId());
        return this.removeById(entity);
    }

    default boolean removeByBizIds(Collection<String> bizIds) {
        List<Long> ids = this.listIdByBizIds(bizIds);
        return this.removeByIds(ids);
    }

    default boolean removeBatchByBizIds(Collection<String> bizIds) {
        List<Long> ids = this.listIdByBizIds(bizIds);
        return this.removeBatchByIds(ids);
    }

    default boolean updateBatchByBizId(Collection<TEntity> entityList) {
        List<TEntity> entities = this.listByBizIds(
                entityList.stream().map(TEntity::getBizId).collect(Collectors.toSet()));
        Map<String, TEntity> entitiesMap = CollectionUtils.convertMap(entities, TEntity::getBizId);
        entityList.forEach(entity -> {
            TEntity eMap = entitiesMap.get(entity.getBizId());
            if (ObjectUtil.isNull(eMap)) {
                return;
            }
            entity.setId(eMap.getId());
        });
        entityList.removeIf(e -> ObjectUtil.isNull(e.getId()));
        return this.updateBatchById(entityList);
    }

    /**
     * 根据业务id列表删除(逻辑删除)
     *
     * @param bizIds 业务id列表
     */
    void removeByBizIds(List<String> bizIds);

    /**
     * 根据业务id查询
     *
     * @param bizId 业务id
     * @return 实体
     */
    TEntity getByBizId(String bizId);

    /**
     * 根据 BizID 查询 ID
     *
     * @param bizId 业务id
     * @return ID
     */
    Long getIdByBizId(String bizId);

    /**
     * 根据 BizID列表 查询 ID列表
     *
     * @param bizIds 业务id列表
     * @return ID列表
     */
    List<Long> listIdByBizIds(Collection<String> bizIds);

    /**
     * 根据业务id查询列表
     *
     * @param bizIds 业务id列表
     * @return 实体列表
     */
    List<TEntity> listByBizIds(Collection<String> bizIds);

    /**
     * 分页列表
     *
     * @param queryDTO 查询条件
     * @param klmbPage 分页条件
     * @return 表单分页列表
     */
    KlmbPage<TEntity> page(TDTO queryDTO, KlmbPage<TEntity> klmbPage);

    /**
     * 滚动分页列表
     *
     * @param queryDTO       查询条件
     * @param klmbScrollPage 滚动分页条件
     * @return 表单分页列表
     */
    KlmbScrollPage<TEntity> pageScroll(TDTO queryDTO,
            KlmbScrollPage<TEntity> klmbScrollPage);

    /**
     * 根据条件查询列表
     *
     * @param queryDTO 查询条件
     * @return 表单列表
     */
    List<TEntity> list(TDTO queryDTO);

    /**
     * 更新状态
     *
     * @param bizId  业务id
     * @param status 状态
     */
    void updateStatus(String bizId, Integer status);
}

