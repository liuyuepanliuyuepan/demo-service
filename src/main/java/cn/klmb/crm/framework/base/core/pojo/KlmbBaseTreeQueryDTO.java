package cn.klmb.crm.framework.base.core.pojo;


import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * dto基类
 *
 * @author shilinchuan
 * @date 2022/04/11
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(builderMethodName = "baseTreeBuilder")
@Data
public class KlmbBaseTreeQueryDTO<TDTO extends KlmbBaseTreeQueryDTO<TDTO>> extends
        KlmbBaseQueryDTO {

    /**
     * 树节点id（同业务id）
     */
    @DtoFieldQuery
    private String treeId;

    /**
     * 树节点名称
     */
    @DtoFieldQuery
    private String treeName;

    /**
     * 父节点id
     */
    @DtoFieldQuery
    private String treeParentId;

    /**
     * 树路径，树结构全路径（使用"#"进行分隔，示例：id1#，id1#id2#id3#）
     */
    @DtoFieldQuery(queryType = Operator.LIKE_RIGHT)
    private String treePath;

    /**
     * 树深度 1，2，3...
     */
    private Integer treeDepth;

    /**
     * 树深度范围 小 （包含）
     */
    @DtoFieldQuery(queryType = Operator.GE, fieldName = "treeDepth")
    private Integer treeDepthMin;

    /**
     * 树深度范围 大（包含）
     */
    @DtoFieldQuery(queryType = Operator.LE, fieldName = "treeDepth")
    private Integer treeDepthMax;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子节点
     */
    private List<TDTO> children;

}
