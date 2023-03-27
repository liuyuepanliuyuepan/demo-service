package cn.klmb.crm.module.member.convert.delivery;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.member.controller.admin.delivery.vo.MemberDeliveryPageReqVO;
import cn.klmb.crm.module.member.controller.admin.delivery.vo.MemberDeliveryRespVO;
import cn.klmb.crm.module.member.controller.admin.delivery.vo.MemberDeliverySaveReqVO;
import cn.klmb.crm.module.member.controller.admin.delivery.vo.MemberDeliveryUpdateReqVO;
import cn.klmb.crm.module.member.dto.delivery.MemberDeliveryQueryDTO;
import cn.klmb.crm.module.member.entity.delivery.MemberDeliveryDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 客户-收货地址 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberDeliveryConvert {

    MemberDeliveryConvert INSTANCE = Mappers.getMapper(MemberDeliveryConvert.class);

    MemberDeliveryDO convert(MemberDeliverySaveReqVO saveReqVO);

    MemberDeliveryDO convert(MemberDeliveryUpdateReqVO updateReqVO);

    KlmbPage<MemberDeliveryRespVO> convert(KlmbPage<MemberDeliveryDO> page);

    List<MemberDeliveryRespVO> convert(List<MemberDeliveryDO> list);

    MemberDeliveryRespVO convert(MemberDeliveryDO saveDO);

    MemberDeliveryQueryDTO convert(MemberDeliveryPageReqVO reqVO);

}
