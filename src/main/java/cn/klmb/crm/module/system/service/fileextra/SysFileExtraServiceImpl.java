package cn.klmb.crm.module.system.service.fileextra;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.fileextra.SysFileExtraMapper;
import cn.klmb.crm.module.system.dto.fileextra.SysFileExtraQueryDTO;
import cn.klmb.crm.module.system.entity.fileextra.SysFileExtraDO;
import org.springframework.stereotype.Service;


/**
 * CRM附件关联 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class SysFileExtraServiceImpl extends
        KlmbBaseServiceImpl<SysFileExtraDO, SysFileExtraQueryDTO, SysFileExtraMapper> implements
        SysFileExtraService {

    public SysFileExtraServiceImpl(SysFileExtraMapper mapper) {
        this.mapper = mapper;
    }

}
