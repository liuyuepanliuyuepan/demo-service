package cn.klmb.crm.module.system.service.dept;

import cn.hutool.core.collection.CollUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseTreeService;
import cn.klmb.crm.framework.common.util.collection.CollectionUtils;
import cn.klmb.crm.module.system.dto.dept.SysDeptQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 系统部门
 *
 * @author shilinchuan
 * @date 2022/12/2
 */
public interface SysDeptService extends KlmbBaseTreeService<SysDeptDO, SysDeptQueryDTO> {

    /**
     * 校验部门们是否有效。如下情况，视为无效： 1. 部门编号不存在 2. 部门被禁用
     *
     * @param ids 角色编号数组
     */
    void validDepts(Collection<String> ids);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<String, SysDeptDO> getDeptMap(Collection<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<SysDeptDO> list = listByBizIds(ids);
        return CollectionUtils.convertMap(list, SysDeptDO::getBizId);
    }

    /**
     * 查询部门下属部门
     *
     * @param parentId 上级ID
     * @return data
     */
    List<String> queryChildDept(String parentId);


    /**
     * 获取本部门与下级部门列表
     *
     * @param queryDTO 查询条件
     * @return 表单列表
     */
    List<SysDeptDO> listV2(SysDeptQueryDTO queryDTO);

}

