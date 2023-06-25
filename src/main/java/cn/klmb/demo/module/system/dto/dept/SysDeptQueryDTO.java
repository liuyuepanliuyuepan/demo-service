package cn.klmb.demo.module.system.dto.dept;

import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.demo.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.demo.framework.base.core.pojo.KlmbBaseTreeQueryDTO;
import cn.klmb.demo.module.system.entity.user.SysUserDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统部门
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysDeptQueryDTO extends KlmbBaseTreeQueryDTO<SysDeptQueryDTO> {

    /**
     * 部门名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 部门名称（简称）
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String nameShort;
    /**
     * 负责人
     * <p>
     * 关联 {@link SysUserDO#getBizId()}
     */
    @DtoFieldQuery
    private String leaderUserId;
    /**
     * 联系电话
     */
    @DtoFieldQuery
    private String phone;
    /**
     * 邮箱
     */
    @DtoFieldQuery
    private String email;

}
