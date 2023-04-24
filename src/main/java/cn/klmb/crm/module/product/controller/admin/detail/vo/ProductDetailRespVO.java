package cn.klmb.crm.module.product.controller.admin.detail.vo;

import cn.klmb.crm.module.system.entity.file.SysFileDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 产品 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDetailRespVO extends ProductDetailBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "b2661a0914904461bc856333deff7ba3")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    /**
     * 主图详细信息
     */
    @ApiModelProperty(value = "主图详细信息")
    private List<SysFileDO> mainFileInfo;

    /**
     * 详情图详细信息
     */
    @ApiModelProperty(value = "详情图详细信息")
    private List<SysFileDO> detailFileInfo;

}
