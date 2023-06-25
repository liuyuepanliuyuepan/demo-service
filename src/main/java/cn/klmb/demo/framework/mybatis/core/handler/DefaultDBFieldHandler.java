package cn.klmb.demo.framework.mybatis.core.handler;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.demo.framework.base.core.entity.KlmbBaseTreeDO;
import cn.klmb.demo.framework.web.core.util.WebFrameworkUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.time.LocalDateTime;
import java.util.Objects;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 通用参数填充实现类
 * <p>
 * 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 *
 * @author hexiaowu
 */
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof KlmbBaseDO) {
            KlmbBaseDO baseDO = (KlmbBaseDO) metaObject.getOriginalObject();
            LocalDateTime current = LocalDateTime.now();
            // 业务id为空，则创建业务id
            if (StrUtil.isBlank(baseDO.getBizId())) {
                baseDO.setBizId(IdUtil.fastSimpleUUID());
            }

            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseDO.getCreateTime())) {
                baseDO.setCreateTime(current);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(current);
            }

            String userId = WebFrameworkUtils.getLoginUserId();
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getCreator())) {
                baseDO.setCreator(userId);
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getUpdater())) {
                baseDO.setUpdater(userId);
            }

            // 判断是否为树类型
            if (metaObject.getOriginalObject() instanceof KlmbBaseTreeDO) {
                KlmbBaseTreeDO<?> baseTreeDO = (KlmbBaseTreeDO<?>) metaObject.getOriginalObject();
                // 树id为空，则使用业务id进行存储
                if (StrUtil.isBlank(baseTreeDO.getTreeId())) {
                    baseTreeDO.setTreeId(baseDO.getBizId());
                }
                // 如果类中存在name的字段，并且treeName为空，自动设置name到treeName中
                if (StrUtil.isBlank(baseTreeDO.getTreeName()) && metaObject.hasGetter("name")) {
                    String name = (String) getFieldValByName("name", metaObject);
                    if (StrUtil.isNotBlank(name)) {
                        baseTreeDO.setTreeName(name);
                    }
                }
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

        // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        Object modifier = getFieldValByName("updater", metaObject);
        String userId = WebFrameworkUtils.getLoginUserId();
        if (Objects.nonNull(userId) && Objects.isNull(modifier)) {
            setFieldValByName("updater", userId, metaObject);
        }

        // 判断是否为树类型
        if (metaObject.getOriginalObject() instanceof KlmbBaseTreeDO) {
            // 如果类中存在name的字段，自动设置name到treeName中
            if (metaObject.hasGetter("name")) {
                setFieldValByName("treeName", getFieldValByName("name", metaObject), metaObject);
            }
        }
    }
}
