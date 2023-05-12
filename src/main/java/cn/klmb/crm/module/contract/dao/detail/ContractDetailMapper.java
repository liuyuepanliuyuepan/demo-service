package cn.klmb.crm.module.contract.dao.detail;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.ContractDetailRespVO;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 合同详情 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ContractDetailMapper extends KlmbBaseMapper<ContractDetailDO, ContractDetailQueryDTO> {



    /**
     * 分页关联查询
     *
     * @param condition 查询条件
     * @param pageDTO   分页条件
     * @return 审核记录表
     */
    PageDTO<ContractDetailRespVO> list(
            @Param("condition") ContractDetailQueryDTO condition,
            PageDTO<ContractDetailRespVO> pageDTO);


}
