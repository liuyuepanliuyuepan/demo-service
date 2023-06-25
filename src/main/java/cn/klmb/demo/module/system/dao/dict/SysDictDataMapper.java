package cn.klmb.demo.module.system.dao.dict;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.dict.SysDictDataQueryDTO;
import cn.klmb.demo.module.system.entity.dict.SysDictDataDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-字典数据
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
@Mapper
public interface SysDictDataMapper extends KlmbBaseMapper<SysDictDataDO, SysDictDataQueryDTO> {

    default SysDictDataDO selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(
                new LambdaQueryWrapper<SysDictDataDO>().eq(SysDictDataDO::getDictType, dictType)
                        .eq(SysDictDataDO::getValue, value));
    }

    default SysDictDataDO selectByDictTypeAndLabel(String dictType, String label) {
        return selectOne(
                new LambdaQueryWrapper<SysDictDataDO>().eq(SysDictDataDO::getDictType, dictType)
                        .eq(SysDictDataDO::getLabel, label));
    }

    default List<SysDictDataDO> selectByDictTypeAndValues(String dictType,
            Collection<String> values) {
        return selectList(
                new LambdaQueryWrapper<SysDictDataDO>().eq(SysDictDataDO::getDictType, dictType)
                        .in(SysDictDataDO::getValue, values));
    }

    default long selectCountByDictType(String dictType) {
        return selectCount(SysDictDataDO::getDictType, dictType);
    }

}
