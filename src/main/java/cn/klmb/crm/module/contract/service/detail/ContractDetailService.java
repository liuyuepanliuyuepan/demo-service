package cn.klmb.crm.module.contract.service.detail;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailFullRespVO;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailPageReqVO;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.member.controller.admin.user.vo.CrmChangeOwnerUserBO;
import java.util.List;


/**
 * 合同详情 Service 接口
 *
 * @author 超级管理员
 */
public interface ContractDetailService extends KlmbBaseService<ContractDetailDO, ContractDetailQueryDTO> {

    /**
     * 自定义新增合同
     * @param saveDO 表单数据
     * @return 业务id
     */
    ContractDetailDO saveDefinition(ContractDetailDO saveDO);

    /**
     * 合同修改合同负责人
     *
     * @param crmChangeOwnerUserBO 修改合同
     */
    void changeOwnerUser(CrmChangeOwnerUserBO crmChangeOwnerUserBO);


    /**
     * 分页列表
     *
     * @param reqVO 查询条件
     * @return 表单分页列表
     */
    ContractDetailFullRespVO page(ContractDetailPageReqVO reqVO);


    /**
     * 根据业务id列表删除(逻辑删除)
     *
     * @param bizIds 业务id列表
     */
    void removeByBizIds(List<String> bizIds);


    /**
     * 合同标星
     *
     * @param bizId
     */
    void star(String bizId);
}
