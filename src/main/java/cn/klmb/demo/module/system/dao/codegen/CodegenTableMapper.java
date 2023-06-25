package cn.klmb.demo.module.system.dao.codegen;

import cn.klmb.demo.framework.common.pojo.PageResult;
import cn.klmb.demo.framework.mybatis.core.mapper.BaseMapperX;
import cn.klmb.demo.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.klmb.demo.module.system.controller.admin.codegen.vo.table.CodegenTablePageReqVO;
import cn.klmb.demo.module.system.entity.codegen.CodegenTableDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代码生成 table 表定义
 *
 * @author liuyuepan
 * @date 2022/12/28
 */
@Mapper
public interface CodegenTableMapper extends BaseMapperX<CodegenTableDO> {

    default CodegenTableDO selectByTableName(String tableName) {
        return selectOne(CodegenTableDO::getTableName, tableName);
    }

    default PageResult<CodegenTableDO> selectPage(CodegenTablePageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<CodegenTableDO>()
                .likeIfPresent(CodegenTableDO::getTableName, pageReqVO.getTableName())
                .likeIfPresent(CodegenTableDO::getTableComment, pageReqVO.getTableComment())
                .betweenIfPresent(CodegenTableDO::getCreateTime, pageReqVO.getCreateTime()));
    }

}
