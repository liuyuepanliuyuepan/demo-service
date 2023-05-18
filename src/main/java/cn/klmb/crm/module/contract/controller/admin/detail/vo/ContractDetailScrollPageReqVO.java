package cn.klmb.crm.module.contract.controller.admin.detail.vo;

import cn.klmb.crm.framework.base.core.pojo.PageScrollParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 合同滚动分页查询
 *
 * @author liuyuepan
 * @date 2023/5/18
 */
@ApiModel(description = "合同滚动分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractDetailScrollPageReqVO extends PageScrollParam {

    /**
     * 关键字查询
     */
    private String keyword;

    @ApiModelProperty(value = "场景ID(1全部,2自己负责的,3下属负责的,4关注的,默认传1)")
    private Integer sceneId;

}
