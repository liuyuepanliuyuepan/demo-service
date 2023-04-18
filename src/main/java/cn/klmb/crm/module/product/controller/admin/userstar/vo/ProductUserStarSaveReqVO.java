package cn.klmb.crm.module.product.controller.admin.userstar.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 用户产品标星关系创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductUserStarSaveReqVO extends ProductUserStarBaseVO {

}
