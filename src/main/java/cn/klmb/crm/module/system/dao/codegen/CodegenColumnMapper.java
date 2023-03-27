package cn.klmb.crm.module.system.dao.codegen;

import cn.klmb.crm.framework.mybatis.core.mapper.BaseMapperX;
import cn.klmb.crm.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.klmb.crm.module.system.entity.codegen.CodegenColumnDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代码生成 column 字段定义
 *
 * @author shilinchuan
 * @date 2022/12/28
 */
@Mapper
public interface CodegenColumnMapper extends BaseMapperX<CodegenColumnDO> {

    default List<CodegenColumnDO> selectListByTableId(Long tableId) {
        return selectList(new LambdaQueryWrapperX<CodegenColumnDO>()
                .eq(CodegenColumnDO::getTableId, tableId)
                .orderByAsc(CodegenColumnDO::getId));
    }

    default void deleteListByTableId(Long tableId) {
        delete(new LambdaQueryWrapperX<CodegenColumnDO>()
                .eq(CodegenColumnDO::getTableId, tableId));
    }

}
