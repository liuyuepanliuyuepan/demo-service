package cn.klmb.demo.framework.common.pojo;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 排序字段 DTO
 * <p>
 * 类名加了 ing 的原因是，避免和 ES SortField 重名。
 */
public class SortingField implements Serializable {

    /**
     * 顺序 - 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 顺序 - 降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 字段
     */
    private String field;
    /**
     * 顺序
     */
    private String order;

    // 空构造方法，解决反序列化
    public SortingField() {
    }

    public SortingField(String field, String order) {
        this.field = field;
        this.order = order;
    }

    public SortingField(String sortStr) {
        String[] split = StrUtil.splitToArray(sortStr, CharUtil.COLON);
        this.field = CharSequenceUtil.toUnderlineCase(split[0]);
        this.order = CharSequenceUtil.toUnderlineCase(split[1]);
    }

    public String getField() {
        return field;
    }

    public SortingField setField(String field) {
        this.field = field;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public SortingField setOrder(String order) {
        this.order = order;
        return this;
    }

    /**
     * 解析  field1:asc,field2:desc
     */
    public static List<SortingField> parseSortingField(String[] sorts) {
        if (ArrayUtil.isEmpty(sorts)) {
            return Collections.emptyList();
        }
        List<SortingField> list = new ArrayList<>();
        for (String sort : sorts) {
            list.add(new SortingField(sort));
        }
        return list;
    }
}
