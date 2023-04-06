package cn.klmb.crm.module.member.dao.bis;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.bis.MemberBisQueryDTO;
import cn.klmb.crm.module.member.entity.bis.MemberBisDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户工商信息 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberBisMapper extends KlmbBaseMapper<MemberBisDO, MemberBisQueryDTO> {

}
