package cn.klmb.crm.module.member.dao.userpool;


import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.userpool.MemberUserPoolQueryDTO;
import cn.klmb.crm.module.member.entity.userpool.MemberUserPoolDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公海 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberUserPoolMapper extends
        KlmbBaseMapper<MemberUserPoolDO, MemberUserPoolQueryDTO> {

}
