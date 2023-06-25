package cn.klmb.demo.framework.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 当前用户可查询对象
 *
 * @author zhangzhiwei
 */
@ApiModel("用户可查询对象")
@Data
public class BiAuthority {

    @ApiModelProperty("用户列表")
    private List<String> userIds;

    @ApiModelProperty("部门列表")
    private List<String> deptIds;
}
