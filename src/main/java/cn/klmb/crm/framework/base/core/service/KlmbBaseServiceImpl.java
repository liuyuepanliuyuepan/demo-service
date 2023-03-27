package cn.klmb.crm.framework.base.core.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.common.pojo.SortingField;
import cn.klmb.crm.framework.common.util.reflect.ReflectUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * serviceImpl基类
 *
 * @author shilinchuan
 * @date 2022/04/11
 */
@Slf4j
public class KlmbBaseServiceImpl<TEntity extends KlmbBaseDO, TDTO extends KlmbBaseQueryDTO, TMapper extends KlmbBaseMapper<TEntity, TDTO>> extends
        ServiceImpl<TMapper, TEntity> implements KlmbBaseService<TEntity, TDTO> {

    protected TMapper mapper;

    protected Class<TEntity> targetEntityClazz;

    protected Class<TDTO> targetDTOClazz;

    public KlmbBaseServiceImpl() {
        targetEntityClazz = ReflectUtils.findTemplateClass(getClass(), 0);
        targetDTOClazz = ReflectUtils.findTemplateClass(getClass(), 1);
    }

    @Override
    public boolean saveDO(TEntity entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatchDO(Collection<TEntity> saveDOList) {
        return super.saveBatch(saveDOList);
    }

    @Override
    public boolean updateDO(TEntity entity) {
        boolean success = false;
        if (ObjectUtil.isNotNull(entity.getId())) {
            success = super.updateById(entity);
        } else if (StrUtil.isNotBlank(entity.getBizId())) {
            success = this.updateByBizId(entity);
        }
        return success;
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        List<TEntity> entities = this.listByBizIds(bizIds);
        if (CollUtil.isEmpty(entities)) {
            return;
        }
        super.removeBatchByIds(entities);
    }

    @Override
    public TEntity getByBizId(String bizId) {
        return mapper.selectByBizId(bizId);
    }

    @Override
    public Long getIdByBizId(String bizId) {
        return mapper.selectIdByBizId(bizId);
    }

    @Override
    public List<Long> listIdByBizIds(Collection<String> bizIds) {
        return mapper.selectIdsByBizIds(bizIds);
    }

    @Override
    public List<TEntity> listByBizIds(Collection<String> bizIds) {
        return mapper.selectByBizIdIn(bizIds);
    }

    @Override
    public KlmbPage<TEntity> page(TDTO queryDTO, KlmbPage<TEntity> klmbPage) {
        // 创建分页
        PageDTO<TEntity> page = new PageDTO<>(klmbPage.getPageNo(), klmbPage.getPageSize());
        // 排序字段
        if (!CollectionUtil.isEmpty(klmbPage.getSortingFields())) {
            page.addOrder(klmbPage.getSortingFields().stream()
                    .map(sortingField -> SortingField.ORDER_ASC.equals(sortingField.getOrder())
                            ? OrderItem.asc(sortingField.getField())
                            : OrderItem.desc(sortingField.getField()))
                    .collect(Collectors.toList()));
        } else {
            page.addOrder(OrderItem.desc("create_time"));
        }
        // 创建查询条件
        QueryWrapper<TEntity> queryWrapper = this.toQueryWrapper(queryDTO);
        PageDTO<TEntity> pageResult = super.page(page, queryWrapper);
        return KlmbPage.<TEntity>builder().pageNo((int) pageResult.getCurrent())
                .pageSize((int) pageResult.getSize()).content(pageResult.getRecords())
                .totalPages((int) pageResult.getPages()).totalElements(pageResult.getTotal())
                .build();
    }

    @Override
    public KlmbScrollPage<TEntity> pageScroll(TDTO queryDTO,
            KlmbScrollPage<TEntity> klmbScrollPage) {
        QueryWrapper<TEntity> queryWrapper = this.toQueryWrapper(queryDTO);
        // 查询总数
        klmbScrollPage.setTotalElements(this.count(queryWrapper));

        if (klmbScrollPage.isAsc()) {
            queryWrapper.orderByAsc("id");
        } else {
            queryWrapper.orderByDesc("id");
        }
        // 是否传入业务id，如果传入，则根据业务id查询条件进行查询
        if (StrUtil.isNotBlank(klmbScrollPage.getLastBizId())) {
            TEntity entity = this.getByBizId(klmbScrollPage.getLastBizId());
            if (ObjectUtil.isNotNull(entity)) {
                if (klmbScrollPage.isAsc()) {
                    queryWrapper.gt("id", entity.getId());
                } else {
                    queryWrapper.lt("id", entity.getId());
                }
            }
        }
        queryWrapper.last("limit " + (klmbScrollPage.getPageSize() + 1));
        List<TEntity> entities = super.list(queryWrapper);
        // 判断查询出的数据是否大于查询每页数量，大于表示存在下一页
        if (NumberUtil.compare(entities.size(), klmbScrollPage.getPageSize()) == 1) {
            // 设置为存在下一页
            klmbScrollPage.setHasNext(true);
            // 去掉最后一条数据
            entities = CollUtil.sub(entities, 0, klmbScrollPage.getPageSize());
        }
        klmbScrollPage.setContent(entities);
        return klmbScrollPage;
    }

    @Override
    public List<TEntity> list(TDTO queryDTO) {
        return super.list(this.toQueryWrapper(queryDTO));
    }

    @Override
    public void updateStatus(String bizId, Integer status) {
        super.update(new UpdateWrapper<TEntity>().eq("biz_id", bizId).set("status", status));
    }

    /**
     * 查询条件
     *
     * @param queryDTO 查询条件
     * @return 查询条件组合
     */
    protected QueryWrapper<TEntity> toQueryWrapper(TDTO queryDTO) {
        return queryDTO.toQueryWrapper();
    }

}