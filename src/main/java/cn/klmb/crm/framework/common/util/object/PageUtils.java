package cn.klmb.crm.framework.common.util.object;


import cn.klmb.crm.framework.common.pojo.PageParam;

/**
 * {@link PageParam} 工具类
 *
 * @author 快乐萌宝
 */
public class PageUtils {

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

}
