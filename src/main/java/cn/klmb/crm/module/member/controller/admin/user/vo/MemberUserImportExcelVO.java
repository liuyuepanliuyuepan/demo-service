package cn.klmb.crm.module.member.controller.admin.user.vo;


import cn.klmb.crm.framework.excel.core.annotations.DictFormat;
import cn.klmb.crm.framework.excel.core.convert.DictConvert;
import cn.klmb.crm.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 客户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
@ExcelIgnoreUnannotated
public class MemberUserImportExcelVO {

    @ExcelProperty("*客户名称")
    private String name;

    @ExcelProperty("负责人")
    private String ownerUserName;


    @ExcelProperty(value = "客户来源", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.MEMBER_USER_SOURCE)
    private String source;

    @ExcelProperty("手机")
    private String mobile;

    @ExcelProperty("电话")
    private String tel;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("网址")
    private String website;

    @ExcelProperty(value = "客户行业", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.MEMBER_USER_INDUSTRY)
    private String industry;


    @ExcelProperty(value = "客户级别", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.MEMBER_USER_LEVEL)
    private String level;

    @ExcelProperty("下次联系时间")
    private String nextTime;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("自定义标签")
    private String tag;

    @ExcelProperty("省")
    private String province;

    @ExcelProperty("市")
    private String city;

    @ExcelProperty("区")
    private String district;

    @ExcelProperty("详细地址")
    private String detailAddress;
}
