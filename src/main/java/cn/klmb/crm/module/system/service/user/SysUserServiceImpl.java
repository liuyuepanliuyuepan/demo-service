package cn.klmb.crm.module.system.service.user;

import static cn.hutool.core.util.RandomUtil.BASE_CHAR;
import static cn.hutool.core.util.RandomUtil.BASE_NUMBER;
import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.USER_EMAIL_EXISTS;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.USER_MOBILE_EXISTS;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.USER_USERNAME_EXISTS;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.framework.common.util.collection.CollectionUtils;
import cn.klmb.crm.framework.common.util.data.RecursionUtil;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserRespVO;
import cn.klmb.crm.module.system.convert.user.SysUserConvert;
import cn.klmb.crm.module.system.dao.user.SysUserMapper;
import cn.klmb.crm.module.system.dto.user.SysUserQueryDTO;
import cn.klmb.crm.module.system.entity.dept.SysDeptDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import cn.klmb.crm.module.system.service.permission.SysPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统用户
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@Service
public class SysUserServiceImpl extends
        KlmbBaseServiceImpl<SysUserDO, SysUserQueryDTO, SysUserMapper>
        implements SysUserService {

    private final PasswordEncoder passwordEncoder;
    private final SysDeptService sysDeptService;
    private final SysPermissionService sysPermissionService;

    public SysUserServiceImpl(PasswordEncoder passwordEncoder, SysDeptService sysDeptService,
            SysUserMapper mapper, SysPermissionService sysPermissionService) {
        this.passwordEncoder = passwordEncoder;
        this.sysDeptService = sysDeptService;
        this.sysPermissionService = sysPermissionService;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDO(SysUserDO saveDO) {
        // 校验正确性
        checkCreateOrUpdate(null, saveDO.getUsername(), saveDO.getMobile(), saveDO.getEmail(),
                saveDO.getDeptId());
        // 插入用户
        saveDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        saveDO.setPassword(encodePassword(saveDO.getPassword()));
        return super.saveDO(saveDO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchDO(Collection<SysUserDO> saveDOList) {
        saveDOList.forEach(this::saveDO);
        return true;
    }

    @Override
    public boolean updateDO(SysUserDO updateDO) {
        // 校验正确性
        checkCreateOrUpdate(updateDO.getBizId(), updateDO.getUsername(), updateDO.getMobile(),
                updateDO.getEmail(), updateDO.getDeptId());
        // 更新用户
        return super.updateDO(updateDO);
    }

    @Override
    public List<SysUserDO> list(SysUserQueryDTO queryDTO) {
        List<SysUserDO> entities = super.list(queryDTO);
        // 是否根据角色列表查询
        if (CollUtil.isNotEmpty(entities) && CollUtil.isNotEmpty(queryDTO.getRoleIds())) {
            // 角色包含的所有用户ID
            Set<String> roleUserIds = sysPermissionService.getUserRoleIdListByRoleIds(
                    queryDTO.getRoleIds());
            if (CollUtil.isEmpty(roleUserIds)) {
                entities = Collections.emptyList();
            } else {
                entities = entities.stream().filter(u -> roleUserIds.contains(u.getBizId()))
                        .collect(Collectors.toList());
            }
        }
        return entities;
    }

    @Override
    public void updatePassword(String bizId, String password) {
        // 校验用户存在
        checkUserExists(bizId);
        // 更新密码
        mapper.updatePassword(bizId, encodePassword(password));
    }

    @Override
    public String resetPassword(String bizId) {
        // 校验用户存在
        checkUserExists(bizId);
        // 生成密码
        String password = RandomUtil.randomString(BASE_CHAR + BASE_CHAR.toUpperCase() + BASE_NUMBER,
                8);
        // 更新密码
        mapper.updatePassword(bizId, encodePassword(password));
        return password;
    }

    @Override
    public void updateStatus(String bizId, Integer status) {
        // 校验用户存在
        checkUserExists(bizId);
        // 更新状态
        mapper.updateStatus(bizId, status);
    }

    @Override
    public void updateUserLogin(String bizId, String loginIp) {
        this.updateByBizId(SysUserDO.builder()
                .bizId(bizId)
                .loginIp(loginIp)
                .loginDate(LocalDateTime.now())
                .build());
    }

    @Override
    public SysUserDO findByUsername(String username) {
        return mapper.selectByUsername(username);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public String getLoginUserDeptId() {
        SysUserDO currentUser = super.getByBizId(getLoginUserId());
        if (ObjectUtil.isNull(currentUser)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        if (StrUtil.isBlank(currentUser.getDeptId())) {
            throw exception(ErrorCodeConstants.DEPT_NOT_FOUND);
        }
        return currentUser.getDeptId();
    }

    private void checkCreateOrUpdate(String bizId, String username, String mobile, String email,
            String deptId) {
        // 校验用户存在
        checkUserExists(bizId);
        // 校验用户名唯一
        checkUsernameUnique(bizId, username);
        // 校验手机号唯一
        checkMobileUnique(bizId, mobile);
        // 校验邮箱唯一
        checkEmailUnique(bizId, email);
        // 校验部门处于开启状态
        if (StrUtil.isNotBlank(deptId)) {
            sysDeptService.validDepts(CollectionUtils.singleton(deptId));
        }
    }

    public void checkUserExists(String bizId) {
        if (bizId == null) {
            return;
        }
        SysUserDO user = super.getByBizId(bizId);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    public void checkUsernameUnique(String bizId, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        SysUserDO user = mapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (bizId == null) {
            throw exception(USER_USERNAME_EXISTS, username);
        }
        if (ObjectUtil.notEqual(bizId, user.getBizId())) {
            throw exception(USER_USERNAME_EXISTS, username);
        }
    }

    public void checkEmailUnique(String bizId, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        SysUserDO user = mapper.selectByEmail(email);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (bizId == null) {
            throw exception(USER_EMAIL_EXISTS, email);
        }
        if (ObjectUtil.notEqual(bizId, user.getBizId())) {
            throw exception(USER_EMAIL_EXISTS, email);
        }
    }

    public void checkMobileUnique(String bizId, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        SysUserDO user = mapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (bizId == null) {
            throw exception(USER_MOBILE_EXISTS, mobile);
        }
        if (ObjectUtil.notEqual(bizId, user.getBizId())) {
            throw exception(USER_MOBILE_EXISTS, mobile);
        }
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    /**
     * 查询该用户下级的用户
     *
     * @param userId 用户ID 0代表全部
     * @return data
     */
    @Override
    public List<String> queryChildUserId(String userId) {
        return RecursionUtil.getChildList(list(), "parentId", userId, "bizId", "bizId");
    }



    /**
     * 根据部门ids查询用户列表
     *
     * @param ids id列表
     * @return data
     */
    @Override
    public List<String> queryUserByDeptIds(List<String> ids) {
        if (ids.size() == 0) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysUserDO::getBizId);
        if (ids.size() > 1) {
            queryWrapper.in(SysUserDO::getDeptId, ids);
        } else {
            queryWrapper.eq(SysUserDO::getDeptId, ids.get(0));
        }
        return listObjs(queryWrapper, Object::toString);
    }

    @Override
    public SysUserRespVO getUserDetailByUserId(String userId) {
        SysUserDO sysUserDO = super.getByBizId(userId);
        if (ObjectUtil.isNotNull(sysUserDO) && StrUtil.isNotBlank(sysUserDO.getDeptId())
                && !StrUtil.equals(sysUserDO.getDeptId(),
                "0")) {
            SysDeptDO sysDeptDO = sysDeptService.getByBizId(sysUserDO.getDeptId());
            sysUserDO.setDeptName(sysDeptDO.getName());
        }
        return SysUserConvert.INSTANCE.convert01(sysUserDO);
    }

}