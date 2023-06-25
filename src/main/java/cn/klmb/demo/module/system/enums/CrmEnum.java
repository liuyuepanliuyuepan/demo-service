package cn.klmb.demo.module.system.enums;

import cn.hutool.core.util.StrUtil;

/**
 * @author liuyuepan demo模块枚举
 */

public enum CrmEnum {
    /**
     * 线索
     */
    LEADS(1, "线索"),
    /**
     * 客户
     */
    CUSTOMER(2, "客户"),
    /**
     * 联系人
     */
    CONTACTS(3, "联系人"),
    /**
     * 产品
     */
    PRODUCT(4, "产品"),
    /**
     * 商机
     */
    BUSINESS(5, "商机"),
    /**
     * 合同
     */
    CONTRACT(6, "合同"),
    /**
     * 回款
     */
    RECEIVABLES(7, "回款"),
    /**
     * 回款计划
     */
    RECEIVABLES_PLAN(8, "回款计划"),
    /**
     * 公海
     */
    CUSTOMER_POOL(9, "公海"),
    /**
     * 市场活动
     */
    MARKETING(10, "市场活动"),
    /**
     * 回访
     */
    RETURN_VISIT(17, "客户回访"),
    /**
     * 发票
     */
    INVOICE(18, "发票"),

    /**
     * 跟进记录
     */
    RECORDING(19,"跟进记录"),

    /**
     * NULL
     */
    NULL(0, "");

    private final Integer type;
    private final String remarks;

    CrmEnum(Integer type, String remarks) {
        this.type = type;
        this.remarks = remarks;
    }

    public static CrmEnum parse(Integer type) {
        for (CrmEnum demoEnum : CrmEnum.values()) {
            if (demoEnum.getType().equals(type)) {
                return demoEnum;
            }
        }
        return NULL;
    }

    public static CrmEnum parse(String name) {
        for (CrmEnum demoEnum : CrmEnum.values()) {
            if (demoEnum.name().equals(name)) {
                return demoEnum;
            }
        }
        return NULL;
    }

    public String getRemarks() {
        return remarks;
    }

    public Integer getType() {
        return type;
    }

    public String getIndex() {
        return "wk_single_" + name().toLowerCase();
    }

    public String getTableName() {
        return name().toLowerCase();
    }

    /**
     * 获取主键ID
     *
     * @param camelCase 是否驼峰
     * @return primaryKey
     */
    public String getPrimaryKey(boolean camelCase) {
        String name;
        if (this == CrmEnum.RETURN_VISIT) {
            name = "visit";
        } else {
            name = name().toLowerCase();
        }
        if (camelCase) {
            return StrUtil.toCamelCase(name) + "Id";
        }
        return name + "_id";
    }

    public String getPrimaryKey() {
        return getPrimaryKey(true);
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
