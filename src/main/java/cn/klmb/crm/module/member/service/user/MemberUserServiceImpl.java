package cn.klmb.crm.module.member.service.user;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.member.enums.ErrorCodeConstants.MEMBER_USER_NOT_EXISTS;
import static cn.klmb.crm.module.member.enums.ErrorCodeConstants.MEMBER_USER_TB_NAME_EXISTS;
import static cn.klmb.crm.module.member.enums.ErrorCodeConstants.MEMBER_USER_TEL_EXISTS;
import static cn.klmb.crm.module.member.enums.ErrorCodeConstants.MEMBER_USER_WX_NAME_EXISTS;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.user.MemberUserMapper;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import org.springframework.stereotype.Service;


/**
 * 客户-用户 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberUserServiceImpl extends
        KlmbBaseServiceImpl<MemberUserDO, MemberUserQueryDTO, MemberUserMapper> implements
        MemberUserService {

    public MemberUserServiceImpl(MemberUserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean saveDO(MemberUserDO memberUserDO) {
        // 校验正确性
        checkCreateOrUpdate(null, memberUserDO.getTel(), memberUserDO.getTbName(),
                memberUserDO.getWxName());
        return super.saveDO(memberUserDO);
    }

    @Override
    public boolean updateDO(MemberUserDO memberUserDO) {
        // 校验正确性
        checkCreateOrUpdate(memberUserDO.getBizId(), memberUserDO.getTel(),
                memberUserDO.getTbName(), memberUserDO.getWxName());
        return super.updateDO(memberUserDO);
    }

    private void checkCreateOrUpdate(String bizId, String tel, String tbName, String wxName) {
        // 校验用户存在
        checkMemberUserExists(bizId);
        // 校验联系方式唯一
        checkTelUnique(bizId, tel);
        // 校验淘宝名唯一
        checkTbNameUnique(bizId, tbName);
        // 校验微信名唯一
        checkWxNameUnique(bizId, wxName);
    }

    @Override
    public void checkMemberUserExists(String bizId) {
        if (bizId == null) {
            return;
        }
        MemberUserDO user = super.getByBizId(bizId);
        if (user == null) {
            throw exception(MEMBER_USER_NOT_EXISTS);
        }
    }

    public void checkTelUnique(String bizId, String tel) {
        if (StrUtil.isBlank(tel)) {
            return;
        }
        MemberUserDO user = mapper.selectByTel(tel);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (bizId == null) {
            throw exception(MEMBER_USER_TEL_EXISTS, tel);
        }
        if (ObjectUtil.notEqual(bizId, user.getBizId())) {
            throw exception(MEMBER_USER_TEL_EXISTS, tel);
        }
    }

    public void checkTbNameUnique(String bizId, String tbName) {
        if (StrUtil.isBlank(tbName)) {
            return;
        }
        MemberUserDO user = mapper.selectByTbName(tbName);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (bizId == null) {
            throw exception(MEMBER_USER_TB_NAME_EXISTS, tbName);
        }
        if (ObjectUtil.notEqual(bizId, user.getBizId())) {
            throw exception(MEMBER_USER_TB_NAME_EXISTS, tbName);
        }
    }

    public void checkWxNameUnique(String bizId, String wxName) {
        if (StrUtil.isBlank(wxName)) {
            return;
        }
        MemberUserDO user = mapper.selectByWxName(wxName);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (bizId == null) {
            throw exception(MEMBER_USER_WX_NAME_EXISTS, wxName);
        }
        if (ObjectUtil.notEqual(bizId, user.getBizId())) {
            throw exception(MEMBER_USER_WX_NAME_EXISTS, wxName);
        }
    }
}
