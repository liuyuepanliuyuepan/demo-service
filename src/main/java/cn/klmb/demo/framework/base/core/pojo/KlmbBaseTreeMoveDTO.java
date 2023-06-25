package cn.klmb.demo.framework.base.core.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 树移动 dto基类
 *
 * @author liuyuepan
 * @date 2022/04/11
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class KlmbBaseTreeMoveDTO {

    /**
     * 树节点id（同业务id）
     */
    private String treeId;

    /**
     * 目标节点id
     */
    private String targetTreeId;

    /**
     * 排序
     */
    private Integer sort;

}
