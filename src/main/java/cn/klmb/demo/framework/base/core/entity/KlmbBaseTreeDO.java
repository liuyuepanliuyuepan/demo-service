package cn.klmb.demo.framework.base.core.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * tree DO基类
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "baseTreeBuilder")
@Data
public class KlmbBaseTreeDO<T> extends KlmbBaseDO {

    /**
     * 树节点id（同业务id）
     */
    @TableField(fill = FieldFill.INSERT)
    private String treeId;

    /**
     * 树节点名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String treeName;

    /**
     * 树父节点id
     */
    @TableField(fill = FieldFill.INSERT)
    private String treeParentId;

    /**
     * 树路径，树结构全路径（使用"#"进行分隔，示例：uuid1#uuid2#uuid3）
     */
    @TableField(fill = FieldFill.INSERT)
    private String treePath;

    /**
     * 树深度
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer treeDepth;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<T> children;

}
