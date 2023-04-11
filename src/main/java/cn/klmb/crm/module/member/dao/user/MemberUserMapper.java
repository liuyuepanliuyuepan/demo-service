package cn.klmb.crm.module.member.dao.user;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户-用户 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberUserMapper extends KlmbBaseMapper<MemberUserDO, MemberUserQueryDTO> {


	/**
	 * 根据坐标获取附近的客户
	 *
	 * @param lng 经度
	 * @param lat 纬度
	 * @param type 2 客户查询  9 公海客户查询
	 * @param radius 半径
	 * @param ownerUserId 负责人id
	 * @param authUserIdList 当前用户及下属用户
	 * @return 附近的客户列表
	 */
	List<MemberUserDO> nearbyMember(
		@Param("lng") String lng, @Param("lat") String lat, @Param("type") Integer type,
		@Param("radius") Integer radius, @Param("ownerUserId") String ownerUserId,
		@Param("userIds") List<String> authUserIdList
	);

}
