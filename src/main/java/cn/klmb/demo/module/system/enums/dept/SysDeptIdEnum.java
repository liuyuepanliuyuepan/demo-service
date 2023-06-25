package cn.klmb.demo.module.system.enums.dept;

import cn.klmb.demo.framework.base.core.constant.TreeConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部门编号枚举
 */
@Getter
@AllArgsConstructor
public enum SysDeptIdEnum {

    /**
     * 根节点
     */
    ROOT(TreeConstant.ROOT_NODE);

    private final String id;

}
