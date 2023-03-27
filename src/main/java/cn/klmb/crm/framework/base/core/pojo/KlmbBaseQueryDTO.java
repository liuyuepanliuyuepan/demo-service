package cn.klmb.crm.framework.base.core.pojo;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.annotations.DtoKeywordQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * dto基类
 *
 * @author shilinchuan
 * @date 2022/04/11
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class KlmbBaseQueryDTO {

    /**
     * 主键
     */
    @DtoFieldQuery
    private Long id;

    /**
     * 业务id
     */
    @DtoFieldQuery
    private String bizId;

    /**
     * 创建时间范围查询
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime[] createTime;

    /**
     * 更新时间查询
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime[] updateTime;

    /**
     * 创建者
     */
    @DtoFieldQuery
    private String creator;

    /**
     * 更新者
     */
    @DtoFieldQuery
    private String updater;

    /**
     * 业务id列表
     */
    @DtoFieldQuery(queryType = Operator.IN, fieldName = "bizId")
    private List<String> bizIds;

    /**
     * 状态
     */
    @DtoFieldQuery
    private Integer status;

    /**
     * 状态列表查询
     */
    @DtoFieldQuery(queryType = Operator.IN, fieldName = "status")
    private Collection<Integer> statuses;

    /**
     * 是否删除
     */
    @DtoFieldQuery
    private Boolean deleted;

    /**
     * 关键字模糊查询
     */
    private String keyword;

    /**
     * 根据注解拼接查询条件
     *
     * @return 查询条件
     */
    public <T> QueryWrapper<T> toQueryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        processQueryWrapper(queryWrapper, this);
        processKeyword(queryWrapper, keyword, this);
        return queryWrapper;
    }

    /**
     * 解析DTO自定义查询注解
     */
    private static void processQueryWrapper(QueryWrapper<?> queryWrapper, KlmbBaseQueryDTO dto) {
        for (Field field : ReflectUtil.getFields(dto.getClass())) {
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(dto);
                if (ObjectUtil.isNotEmpty(fieldValue)) {
                    DtoFieldQuery[] fieldQueries = field.getAnnotationsByType(DtoFieldQuery.class);
                    for (DtoFieldQuery fieldQuery : fieldQueries) {
                        String fieldName = StrUtil.isEmpty(fieldQuery.fieldName()) ? field.getName()
                                : fieldQuery.fieldName();
                        frameQueryWrapper(queryWrapper, fieldQuery.queryType(), fieldName,
                                fieldValue);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 按传入字段进行关键字模糊查询
     *
     * @param keyword 查询关键字
     */
    private static void processKeyword(QueryWrapper<?> queryWrapper, String keyword,
            KlmbBaseQueryDTO dto) {
        if (StrUtil.isNotBlank(keyword)) {
            // 获取模糊查询的字段列表
            List<String> fieldNames = new ArrayList<>();
            for (Field field : ReflectUtil.getFields(dto.getClass())) {
                try {
                    DtoKeywordQuery keywordQuery = field.getAnnotation(DtoKeywordQuery.class);
                    if (null != keywordQuery) {
                        String fieldName =
                                StrUtil.isEmpty(keywordQuery.fieldName()) ? field.getName()
                                        : keywordQuery.fieldName();
                        // 修改字段名，驼峰转下划线
                        fieldName = StrUtil.toSymbolCase(fieldName, '_');
                        fieldNames.add(fieldName);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (CollUtil.isEmpty(fieldNames)) {
                return;
            }
            if (!queryWrapper.isEmptyOfNormal()) {
                queryWrapper.and(
                        w -> fieldNames.forEach(fieldName -> w.or().like(fieldName, keyword)));
            } else {
                fieldNames.forEach(fieldName -> queryWrapper.or().like(fieldName, keyword));
            }
        }
    }

    /**
     * 底层框架查询隔离层
     */
    private static void frameQueryWrapper(QueryWrapper<?> queryWrapper,
            DtoFieldQuery.Operator queryType, String fieldName, Object fieldValue) {
        Object[] fieldValues;
        // 修改字段名，驼峰转下划线
        fieldName = StrUtil.toSymbolCase(fieldName, '_');
        switch (queryType) {
            case EQ:
                queryWrapper.eq(fieldName, fieldValue);
                return;
            case NE:
                queryWrapper.ne(fieldName, fieldValue);
                return;
            case GT:
                queryWrapper.gt(fieldName, fieldValue);
                return;
            case GE:
                queryWrapper.ge(fieldName, fieldValue);
                return;
            case LT:
                queryWrapper.lt(fieldName, fieldValue);
                return;
            case LE:
                queryWrapper.le(fieldName, fieldValue);
                return;
            case BETWEEN:
                fieldValues = (Object[]) fieldValue;
                queryWrapper.between(fieldName, fieldValues[0], fieldValues[1]);
                return;
            case NOT_BETWEEN:
                fieldValues = (Object[]) fieldValue;
                queryWrapper.notBetween(fieldName, fieldValues[0], fieldValues[1]);
                return;
            case LIKE:
                queryWrapper.like(fieldName, fieldValue);
                return;
            case NOT_LIKE:
                queryWrapper.notLike(fieldName, fieldValue);
                return;
            case LIKE_LEFT:
                queryWrapper.likeLeft(fieldName, fieldValue);
                return;
            case LIKE_RIGHT:
                queryWrapper.likeRight(fieldName, fieldValue);
                return;
            case IS_NULL:
                queryWrapper.isNull(fieldName);
                return;
            case IS_NOT_NULL:
                queryWrapper.isNotNull(fieldName);
                return;
            case IN:
                queryWrapper.in(fieldName, (Collection<?>) fieldValue);
                return;
            case NOT_IN:
                queryWrapper.notIn(fieldName, (Collection<?>) fieldValue);
                return;
            default:
        }
    }

}
