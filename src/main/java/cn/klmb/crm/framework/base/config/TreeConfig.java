package cn.klmb.crm.framework.base.config;


import cn.hutool.core.lang.tree.TreeNodeConfig;

/**
 * tree serviceImpl基类
 *
 * @author shilinchuan
 * @date 2022/04/13
 */
public class TreeConfig {

    /**
     * 树配置
     *
     * @return 配置信息
     */
    public static TreeNodeConfig getConfig() {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("treeId");
        treeNodeConfig.setNameKey("treeName");
        treeNodeConfig.setParentIdKey("treeParentId");
        treeNodeConfig.setWeightKey("sort");
        return treeNodeConfig;
    }
}
