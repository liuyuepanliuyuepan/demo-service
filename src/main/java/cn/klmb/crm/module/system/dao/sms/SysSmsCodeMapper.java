package cn.klmb.crm.module.system.dao.sms;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.framework.mybatis.core.query.QueryWrapperX;
import cn.klmb.crm.module.system.dto.sms.SysSmsCodeQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsCodeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-短信验证码
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsCodeMapper extends KlmbBaseMapper<SysSmsCodeDO, SysSmsCodeQueryDTO> {

    /**
     * 获得手机号的最后一个手机验证码
     *
     * @param mobile 手机号
     * @param scene  发送场景，选填
     * @param code   验证码 选填
     * @return 手机验证码
     */
    default SysSmsCodeDO selectLastByMobile(String mobile, String code, Integer scene) {
        return selectOne(new QueryWrapperX<SysSmsCodeDO>()
                .eq("mobile", mobile)
                .eqIfPresent("scene", scene)
                .eqIfPresent("code", code)
                .orderByDesc("id")
                .limit1());
    }

}
