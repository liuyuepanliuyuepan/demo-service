package cn.klmb.crm.module.product.service.category;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.product.controller.admin.category.vo.CrmProductCategoryBO;
import cn.klmb.crm.module.product.dto.category.ProductCategoryQueryDTO;
import cn.klmb.crm.module.product.entity.category.ProductCategoryDO;
import java.util.List;


/**
 * 产品分类 Service 接口
 *
 * @author 超级管理员
 */
public interface ProductCategoryService extends
        KlmbBaseService<ProductCategoryDO, ProductCategoryQueryDTO> {

    /**
     * 查询产品类别列表
     *
     * @param type pid
     * @return data
     */
    List<CrmProductCategoryBO> queryList(String type);


    /**
     * 根据业务id列表删除(逻辑删除)
     *
     * @param bizIds 业务id列表
     */
    void removeByBizIds(List<String> bizIds);

}
