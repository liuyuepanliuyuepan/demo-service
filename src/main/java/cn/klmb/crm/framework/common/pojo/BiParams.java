package cn.klmb.crm.framework.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 * @author zhangzhiwei bi参数
 */
@ApiModel("bi查询相关参数")
@Data
public class BiParams {

    @ApiModelProperty("部门ID")
    private String deptId;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户IDs")
    private List<String> userIds;

    @ApiModelProperty("部门IDs")
    private List<String> deptIds;

    @ApiModelProperty("类型(今天today,昨天yesterday,明天tomorrow,周week,上周lastWeek,下周nextWeek,本月month,上月lastMonth,下月nextMonth,本季度quarter,上一季度lastQuarter,下一季度nextQuarter,本年度year,上一年度lastYear,下一年度nextYear)")
    private String type;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("0 部门 1员工")
    private Integer isUser = 1;

//
//    @ApiModelProperty("类型ID")
//    private Integer typeId;

    @ApiModelProperty("年份")
    private Integer year;
//
//    @ApiModelProperty("菜单ID")
//    private Integer menuId;
//
//    @ApiModelProperty("月份")
//    private Integer moneyType;

    /**
     * dataType 1仅本人, 2 本人及下属,3进本部门,4本部门及下属部门
     */
    @ApiModelProperty("数据类型(1仅本人, 2 本人及下属,3进本部门,4本部门及下属部门)")
    private Integer dataType;

    /**
     * 排行榜类型
     */
    @ApiModelProperty("排行榜类型(1新增客户数,2新增联系人,3新增跟进记录数)")
    private Integer rankType;

    /**
     * 搜索
     */
    @ApiModelProperty("搜索")
    private String search;

    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    @ApiModelProperty(value = "页码，从 1 开始", required = true, example = "1")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNo = PAGE_NO;

    @ApiModelProperty(value = "每页条数，最大值为 100", required = true, example = "10")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = PAGE_SIZE;

    @ApiModelProperty(hidden = true)
    private List<SortingField> sortingFields;


    @ApiModelProperty("查询类型，跟进记录需要(2客户,3联系人,5商机)")
    private Integer queryType;

    @ApiModelProperty("crm类型(2客户,3联系人,5商机,19跟进记录)")
    private Integer label;

//
//
//    @ApiModelProperty("排序方式")
//    private Integer order;
//
//    @ApiModelProperty("排序字段")
//    private String sortField;
//
//    private Integer checkStatus;
//
//    private Integer subUser;

//    private Integer day;


}
