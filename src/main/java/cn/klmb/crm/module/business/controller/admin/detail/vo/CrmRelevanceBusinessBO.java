package cn.klmb.crm.module.business.controller.admin.detail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * @author liuyuepan 业务关联对象
 */
@Data
@ToString
@ApiModel("业务关联对象")
public class CrmRelevanceBusinessBO {

    @ApiModelProperty("业务ID")
    private String businessId;

    @ApiModelProperty("关联对象列表")
    private List<String> contactsIds;
}
