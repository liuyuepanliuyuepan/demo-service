package cn.klmb.crm.module.business.controller.admin.detail.vo;

import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 商机 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessDetailRespVO extends BusinessDetailBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "efd3e4cbc8834ff59aab0c631f9268d3")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", required = true)
    private LocalDateTime updateTime;

    /**
     * 商机产品关系集合
     */
    @ApiModelProperty(value = "商机产品关系集合")
    private List<BusinessProductRespVO> businessProductRespList;


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
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;
}
