package cn.klmb.demo.module.system.convert.auth;

import cn.klmb.demo.framework.common.util.collection.CollectionUtils;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthLoginRespVO;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthMenuRespVO;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthPermissionInfoRespVO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import cn.klmb.demo.module.system.entity.permission.SysMenuDO;
import cn.klmb.demo.module.system.entity.permission.SysRoleDO;
import cn.klmb.demo.module.system.entity.user.SysUserDO;
import cn.klmb.demo.module.system.enums.permission.SysMenuIdEnum;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

/**
 * 系统权限转换类
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@Mapper
public interface SysAuthConvert {

    SysAuthConvert INSTANCE = Mappers.getMapper(SysAuthConvert.class);

    SysAuthLoginRespVO convert(SysOAuth2AccessTokenDO bean);

    default SysAuthPermissionInfoRespVO convert(SysUserDO user, List<SysRoleDO> roleList,
            List<SysMenuDO> menuList) {
        return SysAuthPermissionInfoRespVO.builder()
                .user(SysAuthPermissionInfoRespVO.UserVO.builder().bizId(user.getBizId())
                        .nickname(user.getNickname()).avatar(user.getAvatar()).build())
                .roles(CollectionUtils.convertSet(roleList, SysRoleDO::getCode))
                .permissions(CollectionUtils.convertSet(menuList, SysMenuDO::getPermission))
                .build();
    }

    SysAuthMenuRespVO convertTreeNode(SysMenuDO menu);

    /**
     * 将菜单列表，构建成菜单树
     *
     * @param menuList 菜单列表
     * @return 菜单树
     */
    default List<SysAuthMenuRespVO> buildMenuTree(List<SysMenuDO> menuList) {
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(SysMenuDO::getSort));
        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<String, SysAuthMenuRespVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getBizId(),
                SysAuthConvert.INSTANCE.convertTreeNode(menu)));
        // 处理父子关系
        treeNodeMap.values().stream()
                .filter(node -> !node.getParentId().equals(SysMenuIdEnum.ROOT.getId()))
                .forEach(childNode -> {
                    // 获得父节点
                    SysAuthMenuRespVO parentNode = treeNodeMap.get(childNode.getParentId());
                    if (parentNode == null) {
                        LoggerFactory.getLogger(getClass())
                                .error("[buildRouterTree][resource({}) 找不到父资源({})]",
                                        childNode.getBizId(), childNode.getParentId());
                        return;
                    }
                    // 将自己添加到父节点中
                    if (parentNode.getChildren() == null) {
                        parentNode.setChildren(new ArrayList<>());
                    }
                    parentNode.getChildren().add(childNode);
                });
        // 获得到所有的根节点
        return CollectionUtils.filterList(treeNodeMap.values(),
                node -> SysMenuIdEnum.ROOT.getId().equals(node.getParentId()));
    }

}
