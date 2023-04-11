package cn.klmb.crm.module.member.service.user;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPoolBO;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import java.util.List;


/**
 * 客户-用户 Service 接口
 *
 * @author 超级管理员
 */
public interface MemberUserService extends KlmbBaseService<MemberUserDO, MemberUserQueryDTO> {


    /**
     * 分页列表
     *
     * @param reqVO 查询条件
     * @return 表单分页列表
     */
    KlmbPage<MemberUserDO> page(MemberUserPageReqVO reqVO);

    /**
     * 修改客户成交状态
     *
     * @param dealStatus 状态
     * @param bizIds     bizIds
     */
    void setDealStatus(Integer dealStatus, List<String> bizIds);

    /**
     * 客户标星
     *
     * @param bizId
     */
    void star(String bizId);


    /**
     * 新建客户
     *
     * @param saveDO 实体
     * @return 实体
     */
    String saveCustomer(MemberUserDO saveDO);


    /**
     * 编辑客户
     *
     * @param saveDO 实体
     * @return 实体
     */
    boolean updateDO(MemberUserDO saveDO);


    /**
     * 根据业务id列表删除(逻辑删除)
     *
     * @param bizIds 业务id列表
     */
    void removeByBizIds(List<String> bizIds);

	/**
	 * 获取附近的客户
	 *
	 * @param lng 经度
	 * @param lat 维度
	 * @param type 2 客户查询  9 公海客户查询
	 * @param radius 半径
	 * @param ownerUserId 负责人id
	 * @return 客户列表
	 */
	List<MemberUserDO> nearbyMember(String lng, String lat, Integer type, Integer radius,
		String ownerUserId);

    /**
     * 客户放入公海
     *
     * @param poolBO bo
     */
    void addPool(MemberUserPoolBO poolBO);

}
