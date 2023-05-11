package cn.klmb.crm.module.product.controller.admin.detail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台-产品批量删除 Request VO")
@Data
@ToString(callSuper = true)
public class ProductDeleteReqVO {

    @ApiModelProperty(value = "业务ids", required = true)
    @NotEmpty(message = "业务ids不能为空")
    private List<String> bizIds;


}
