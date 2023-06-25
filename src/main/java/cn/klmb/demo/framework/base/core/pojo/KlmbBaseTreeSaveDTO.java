package cn.klmb.demo.framework.base.core.pojo;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseTreeDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 树添加 dto基类
 *
 * @author liuyuepan
 * @date 2022/04/14
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@SuperBuilder
@Data
public class KlmbBaseTreeSaveDTO<TEntity extends KlmbBaseTreeDO<TEntity>> {

    /**
     * 树节点
     */
    private String treeId;

    /**
     * 多级树
     */
    private List<TEntity> saveMultiTree;

}
