package cn.klmb.demo.framework.base.core.pojo;


import cn.klmb.demo.framework.common.pojo.SortingField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 分页
 *
 * @author liuyuepan
 * @date 2022/11/28
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KlmbPage<T> {

    /**
     * 偏移量
     */
    @JsonIgnore
    private Integer offset;

    /**
     * 页码
     */
    @Builder.Default
    private Integer pageNo = 1;

    /**
     * 每页大小
     */
    @Builder.Default
    private Integer pageSize = 10;

    /**
     * 总页数
     */
    @Builder.Default
    private Integer totalPages = 0;

    /**
     * 总数量
     */
    @Builder.Default
    private Long totalElements = 0L;

    /**
     * 排序字段信息
     */
    private Collection<SortingField> sortingFields;

    /**
     * 内容
     */
    private List<T> content;

    /**
     * 设置总数，计算页数和偏移量
     *
     * @param totalElements 总数
     */
    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        // 计算总页数
        double totalPagesDouble = totalElements.doubleValue() / this.getPageSize().doubleValue();
        this.totalPages = (int) Math.ceil(totalPagesDouble);
        // 计算偏移量
        this.offset = (this.pageNo - 1) * this.pageSize;
    }

}
