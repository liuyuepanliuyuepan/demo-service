package cn.klmb.crm.module.system.service.dept;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DEPT_EXITS_CHILDREN;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DEPT_NAME_DUPLICATE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DEPT_NOT_ENABLE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DEPT_NOT_FOUND;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DEPT_PARENT_ERROR;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DEPT_PARENT_IS_CHILD;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.DEPT_PARENT_NOT_EXITS;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseTreeServiceImpl;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.framework.common.util.collection.CollectionUtils;
import cn.klmb.crm.module.system.dao.dept.SysDeptMapper;
import cn.klmb.crm.module.system.dto.dept.SysDeptQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import cn.klmb.crm.module.system.enums.dept.SysDeptIdEnum;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;


/**
 * 系统菜单
 *
 * @author shilinchuan
 * @date 2022/12/2
 */
@Service
public class SysDeptServiceImpl extends
        KlmbBaseTreeServiceImpl<SysDeptDO, SysDeptQueryDTO, SysDeptMapper> implements SysDeptService {

    public SysDeptServiceImpl(SysDeptMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean saveDO(SysDeptDO saveDO) {
        // 校验正确性
        checkCreateOrUpdate(null, saveDO.getTreeParentId(), saveDO.getName());
        // 插入部门
        return super.saveDO(saveDO);
    }

    @Override
    public boolean updateDO(SysDeptDO sysDeptDO) {
        // 校验正确性
        if (StrUtil.isBlank(sysDeptDO.getTreeParentId())) {
            sysDeptDO.setTreeParentId(SysDeptIdEnum.ROOT.getId());
        }
        checkCreateOrUpdate(sysDeptDO.getBizId(), sysDeptDO.getTreeParentId(), sysDeptDO.getName());
        // 更新部门
        return super.updateDO(sysDeptDO);
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        bizIds.forEach(bizId -> {
            // 校验是否存在
            checkDeptExists(bizId);
            // 校验是否有子部门
            if (mapper.selectCountByTreeParentId(bizId) > 0) {
                throw exception(DEPT_EXITS_CHILDREN);
            }
        });
        super.removeByBizIds(bizIds);
    }

    private void checkCreateOrUpdate(String id, String parentId, String name) {
        // 校验自己存在
        checkDeptExists(id);
        // 校验父部门的有效性
        checkParentDeptEnable(id, parentId);
        // 校验部门名的唯一性
        checkDeptNameUnique(id, parentId, name);
    }

    private void checkParentDeptEnable(String id, String parentId) {
        if (ObjectUtil.isNull(parentId) || ObjectUtil.equal(SysDeptIdEnum.ROOT.getId(), parentId)) {
            return;
        }
        // 不能设置自己为父部门
        if (parentId.equals(id)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 父岗位不存在
        SysDeptDO dept = mapper.selectByBizId(parentId);
        if (dept == null) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 父部门被禁用
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
            throw exception(DEPT_NOT_ENABLE);
        }
        // 父部门不能是原来的子部门
        List<SysDeptDO> children = this.findSubList(id, false);
        if (children.stream().anyMatch(dept1 -> dept1.getTreeId().equals(parentId))) {
            throw exception(DEPT_PARENT_IS_CHILD);
        }
    }

    private void checkDeptExists(String id) {
        if (id == null) {
            return;
        }
        SysDeptDO dept = mapper.selectByBizId(id);
        if (dept == null) {
            throw exception(DEPT_NOT_FOUND);
        }
    }

    private void checkDeptNameUnique(String id, String parentId, String name) {
        SysDeptDO dept = mapper.selectByTreeParentIdAndTreeName(parentId, name);
        if (dept == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dept.getBizId(), id)) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
    }

    @Override
    public void validDepts(Collection<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        List<SysDeptDO> depts = mapper.selectByBizIdIn(ids);
        Map<String, SysDeptDO> deptMap = CollectionUtils.convertMap(depts, SysDeptDO::getBizId);
        // 校验
        ids.forEach(id -> {
            SysDeptDO dept = deptMap.get(id);
            if (dept == null) {
                throw exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
                throw exception(DEPT_NOT_ENABLE, dept.getName());
            }
        });
    }
}