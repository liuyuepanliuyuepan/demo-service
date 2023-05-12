package cn.klmb.crm.module.member.enums;

/**
 * @author zhangzhiwei 数据权限枚举
 */

public enum DataTypeEnum {
    /**
     * 数据权限
     */
    SELF(1),
    SELF_AND_CHILD(2),
    DEPT(3),
    DEPT_AND_CHILD(4),
    ALL(5),
    CUSTOMIZE(6);

    private Integer type;

    DataTypeEnum(Integer type) {
        this.type = type;
    }

    public static DataTypeEnum parse(Integer type) {
        for (DataTypeEnum typeEnum : values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
