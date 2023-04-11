package cn.klmb.crm.module.member.dao.record;


import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.record.MemberOwnerRecordQueryDTO;
import cn.klmb.crm.module.member.entity.record.MemberOwnerRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 负责人变更记录 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberOwnerRecordMapper extends
        KlmbBaseMapper<MemberOwnerRecordDO, MemberOwnerRecordQueryDTO> {

}
