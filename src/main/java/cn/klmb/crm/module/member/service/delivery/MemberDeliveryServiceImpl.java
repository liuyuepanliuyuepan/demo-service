package cn.klmb.crm.module.member.service.delivery;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.member.enums.ErrorCodeConstants.MEMBER_DELIVERY_NOT_EXISTS;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.delivery.MemberDeliveryMapper;
import cn.klmb.crm.module.member.dto.delivery.MemberDeliveryQueryDTO;
import cn.klmb.crm.module.member.entity.delivery.MemberDeliveryDO;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import org.springframework.stereotype.Service;


/**
 * 客户-收货地址 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberDeliveryServiceImpl extends
        KlmbBaseServiceImpl<MemberDeliveryDO, MemberDeliveryQueryDTO, MemberDeliveryMapper> implements
        MemberDeliveryService {

    private final MemberUserService memberUserService;

    public MemberDeliveryServiceImpl(MemberUserService memberUserService, MemberDeliveryMapper mapper) {
        this.memberUserService = memberUserService;
        this.mapper = mapper;
    }

    @Override
    public boolean saveDO(MemberDeliveryDO memberDeliveryDO) {
        // 校验正确性
        return super.saveDO(memberDeliveryDO);
    }

    @Override
    public boolean updateDO(MemberDeliveryDO memberDeliveryDO) {
        // 校验正确性
        checkCreateOrUpdate(memberDeliveryDO.getBizId(), memberDeliveryDO.getMemberUserId());
        return super.updateDO(memberDeliveryDO);
    }

    private void checkCreateOrUpdate(String bizId, String memberUserId) {
        // 校验收货地址存在
        checkMemberDeliveryExists(bizId);
    }

    public void checkMemberDeliveryExists(String bizId) {
        if (bizId == null) {
            return;
        }
        MemberDeliveryDO delivery = super.getByBizId(bizId);
        if (delivery == null) {
            throw exception(MEMBER_DELIVERY_NOT_EXISTS);
        }
    }
}
