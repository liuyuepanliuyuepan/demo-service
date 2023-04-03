package cn.klmb.crm.framework.common;

import java.io.Serializable;

/**
 * 默认配置常量信息
 *
 * @author liuyuepan
 */
public class Const implements Serializable {

    /**
     * 查询数据权限递归次数,可以通过继承这个类修改
     */
    public static final int AUTH_DATA_RECURSION_NUM = 20;


    /**
     * 批量保存的条数
     */
    public static final int BATCH_SAVE_SIZE = 200;


}
