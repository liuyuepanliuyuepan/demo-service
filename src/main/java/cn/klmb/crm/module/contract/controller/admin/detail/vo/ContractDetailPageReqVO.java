package cn.klmb.crm.module.contract.controller.admin.detail.vo;

import java.math.BigDecimal;
import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.klmb.crm.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 合同详情分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractDetailPageReqVO extends PageParam {

    /**
     * 关键字查询
     */
    private String keyword;

    @ApiModelProperty(value = "场景ID(1全部,2自己负责的,3下属负责的,4关注的,默认传1)")
    private Integer sceneId;
}
