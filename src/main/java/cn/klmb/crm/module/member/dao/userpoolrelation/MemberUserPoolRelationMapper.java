package cn.klmb.crm.module.member.dao.userpoolrelation;


import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.userpoolrelation.MemberUserPoolRelationQueryDTO;
import cn.klmb.crm.module.member.entity.userpoolrelation.MemberUserPoolRelationDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户公海关联 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberUserPoolRelationMapper extends
        KlmbBaseMapper<MemberUserPoolRelationDO, MemberUserPoolRelationQueryDTO> {

}
