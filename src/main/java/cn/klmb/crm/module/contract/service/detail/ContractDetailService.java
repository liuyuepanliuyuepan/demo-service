package cn.klmb.crm.module.contract.service.detail;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailPageReqVO;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;


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
    String saveDefinition(ContractDetailDO saveDO);

    /**
     * 合同管理分页查询
     * @param reqVO 查询条件
     * @return 分页数据
     */
    KlmbPage<ContractDetailDO> pageDefinition(ContractDetailPageReqVO reqVO);
}
