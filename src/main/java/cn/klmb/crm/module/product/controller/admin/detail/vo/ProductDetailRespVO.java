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

    @ApiModelProperty(value = "更新时间", required = true)
    private LocalDateTime updateTime;


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


    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String ownerUserName;


    /**
     * 是否标星
     */
    @ApiModelProperty(value = "是否标星")
    private Boolean star;

    /**
     * 上下架状态(1上架，0下架)
     */
    @ApiModelProperty(value = "上下架状态(1上架，0下架)")
    private Integer status;

    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String categoryName;

}
