package cn.klmb.crm.module.contract.service.star;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.contract.dao.star.ContractStarMapper;
import cn.klmb.crm.module.contract.dto.star.ContractStarQueryDTO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;


/**
 * 合同标星关系 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ContractStarServiceImpl extends
        KlmbBaseServiceImpl<ContractStarDO, ContractStarQueryDTO, ContractStarMapper> implements
        ContractStarService {

    public ContractStarServiceImpl(ContractStarMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void cancelStar(String contractId) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        ContractStarDO contractStarDO = super.getOne(
                new LambdaQueryWrapper<ContractStarDO>().eq(ContractStarDO::getUserId,
                                userId).eq(ContractStarDO::getDeleted, false)
                        .eq(ContractStarDO::getContractId, contractId));

        super.removeByBizId(contractStarDO.getBizId());
    }
}
