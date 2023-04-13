package cn.klmb.crm.module.member.service.instrument;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.common.pojo.BiAuthority;
import cn.klmb.crm.framework.common.pojo.BiParams;
import cn.klmb.crm.framework.common.util.data.BiTimeUtil;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmInstrumentVO;
import cn.klmb.crm.module.member.dao.instrument.CrmInstrumentMapper;
import cn.klmb.crm.module.member.enums.DataTypeEnum;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CrmInstrumentServiceImpl implements CrmInstrumentService {


    private final SysUserService sysUserService;

    private final SysDeptService sysDeptService;

    private final CrmInstrumentMapper crmInstrumentMapper;

    public CrmInstrumentServiceImpl(SysUserService sysUserService,
            SysDeptService sysDeptService, CrmInstrumentMapper crmInstrumentMapper) {
        this.sysUserService = sysUserService;
        this.sysDeptService = sysDeptService;
        this.crmInstrumentMapper = crmInstrumentMapper;
    }


    @Override
    public CrmInstrumentVO queryBulletin(BiParams biParams) {
        BiAuthority biAuthority = handleDataType(biParams.setDataType(biParams.getDataType()));
        BiTimeUtil.BiTimeEntity biTimeEntity = BiTimeUtil.analyzeTime(biParams);
        List<String> userIds = biAuthority.getUserIds();
        CrmInstrumentVO crmInstrumentVO = crmInstrumentMapper.queryBulletin(biTimeEntity, userIds);
        return crmInstrumentVO;
    }


    private BiAuthority handleDataType(BiParams biParams) {
        //数据类型
        SysUserDO sysUserDO = sysUserService.getByBizId(WebFrameworkUtils.getLoginUserId());
        DataTypeEnum typeEnum = DataTypeEnum.parse(biParams.getDataType());
        List<String> userIdList = new ArrayList<>();
        List<String> deptIdList = new ArrayList<>();
        if (typeEnum != null) {
            if (typeEnum == DataTypeEnum.SELF) {
                userIdList.add(WebFrameworkUtils.getLoginUserId());
            } else if (typeEnum == DataTypeEnum.SELF_AND_CHILD) {
                userIdList.addAll(
                        sysUserService.queryChildUserId(WebFrameworkUtils.getLoginUserId()));
                userIdList.add(WebFrameworkUtils.getLoginUserId());
            } else if (typeEnum == DataTypeEnum.DEPT) {
                deptIdList.add(sysUserDO.getDeptId());
                userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
            } else if (typeEnum == DataTypeEnum.DEPT_AND_CHILD) {
                deptIdList.addAll(sysDeptService.queryChildDept(sysUserDO.getDeptId()));
                deptIdList.add(sysUserDO.getDeptId());
                userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
            } else {
                deptIdList.addAll(sysDeptService.queryChildDept("0"));
                userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
            }
        } else {
            if (1 == biParams.getIsUser()) {
                List<String> userIds = biParams.getUserIds();
                if (CollUtil.isEmpty(userIds)) {
                    userIds = new ArrayList<>();
                }
                if (StrUtil.isNotBlank(biParams.getUserId())) {
                    userIds.add(biParams.getUserId());
                }
                if (userIds.size() == 0) {
                    if (StrUtil.equals(sysUserDO.getDeptId(), "0")) {
                        userIdList.addAll(sysUserService.queryUserByDeptIds(
                                sysDeptService.queryChildDept("0")));
                    } else {
                        userIdList.addAll(sysUserService.queryChildUserId(
                                WebFrameworkUtils.getLoginUserId()));
                        userIdList.add(WebFrameworkUtils.getLoginUserId());
                    }
                } else {
                    if (StrUtil.equals(sysUserDO.getDeptId(), "0")) {
                        userIdList.addAll(userIds);
                    }
                }
            } else if (0 == biParams.getIsUser() && biParams.getDeptId() != null) {
                List<String> data = sysDeptService.queryChildDept(biParams.getDeptId());
                data.add(biParams.getDeptId());
                deptIdList.addAll(data);
                userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
            }
        }
        BiAuthority authority = new BiAuthority();
        authority.setUserIds(userIdList);
        authority.setDeptIds(deptIdList);
        return authority;
    }


}
