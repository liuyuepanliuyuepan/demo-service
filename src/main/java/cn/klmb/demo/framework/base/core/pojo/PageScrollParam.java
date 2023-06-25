package cn.klmb.demo.framework.base.core.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@ApiModel("滚动分页参数")
@Data
public class PageScrollParam implements Serializable {

    private static final String LAST_BIZ_ID = null;
    private static final Integer PAGE_SIZE = 10;
    private static final Boolean ASC = true;

    @ApiModelProperty(value = "列表中最后一个业务id", required = true)
    private String lastBizId = LAST_BIZ_ID;

    @ApiModelProperty(value = "每页条数，最大值为 100", required = true, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = PAGE_SIZE;

    @ApiModelProperty(value = "是否为正序，默认true", required = true)
    @NotNull(message = "排序不能为空")
    private Boolean asc = ASC;

}
