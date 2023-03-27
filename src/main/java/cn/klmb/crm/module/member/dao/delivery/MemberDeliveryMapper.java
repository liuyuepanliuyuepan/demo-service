package cn.klmb.crm.module.member.dao.delivery;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.delivery.MemberDeliveryQueryDTO;
import cn.klmb.crm.module.member.entity.delivery.MemberDeliveryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户-收货地址 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberDeliveryMapper extends KlmbBaseMapper<MemberDeliveryDO, MemberDeliveryQueryDTO> {

}
