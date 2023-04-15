package cn.klmb.crm.module.member.controller.admin.user.vo;

import cn.klmb.crm.framework.base.core.pojo.PageScrollParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 客户滚动分页查询
 *
 * @author liuyuepan
 * @date 2023/4/15
 */
@ApiModel(description = "客户滚动分页查")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberUserScrollPageReqVO extends PageScrollParam {

    /**
     * 关键字查询
     */
    private String keyword;

    @ApiModelProperty(value = "场景ID(1全部,2自己负责的,3下属负责的,4关注的,默认传1)")
    private Integer sceneId;

    /**
     * 当前用户id
     */
    @ApiModelProperty(value = "当前用户id(无需传值)")
    private String userId;

    /**
     * 公海id
     */
    @ApiModelProperty(value = "公海id(只有公海分页时此时必传)")
    private String poolId;


}
