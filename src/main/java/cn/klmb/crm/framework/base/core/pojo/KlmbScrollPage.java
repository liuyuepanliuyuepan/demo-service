package cn.klmb.crm.framework.base.core.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 滚动分页
 *
 * @author shilinchuan
 * @date 2022/5/14
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KlmbScrollPage<T> {

    /**
     * 每页大小
     */
    @Builder.Default
    private Integer pageSize = 10;

    /**
     * 最后一条数据的业务id
     */
    private String lastBizId;

    /**
     * 根据最后一条数据的业务id查询排序，是否正序排列，默认 true
     */
    @Builder.Default
    private boolean asc = true;

    /**
     * 是否存在下一个
     */
    @Builder.Default
    private boolean hasNext = false;

    /**
     * 总数量
     */
    @Builder.Default
    private Long totalElements = 0L;

    /**
     * 内容
     */
    private List<T> content;

}
