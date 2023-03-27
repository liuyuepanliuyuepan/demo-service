package cn.klmb.crm.module.system.service.codegen;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 数据库表 Service 实现类
 *
 * @author 快乐萌宝
 */
@Service
public class DatabaseTableServiceImpl implements DatabaseTableService {

//    /**
//     * 应用名
//     */
//    @Value("${spring.datasource.druid.url}")
//    private String applicationName;

    @Resource
    private DruidDataSource druidDataSource;

    @Override
    public List<TableInfo> getTableList(String nameLike, String commentLike) {
        List<TableInfo> tables = getTableList0(null);
        return tables.stream().filter(tableInfo -> (StrUtil.isEmpty(nameLike) || tableInfo.getName().contains(nameLike))
                        && (StrUtil.isEmpty(commentLike) || tableInfo.getComment().contains(commentLike)))
                .collect(Collectors.toList());
    }

    @Override
    public TableInfo getTable(String name) {
        return CollUtil.getFirst(getTableList0(name));
    }

    public List<TableInfo> getTableList0(String name) {
        // 使用 MyBatis Plus Generator 解析表结构
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(druidDataSource.getUrl(),
                druidDataSource.getUsername(), druidDataSource.getPassword()).build();
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder();
        if (StrUtil.isNotEmpty(name)) {
            strategyConfig.addInclude(name);
        }
        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.TIME_PACK).build(); // 只使用 Date 类型，不使用 LocalDate
        ConfigBuilder builder = new ConfigBuilder(null, dataSourceConfig, strategyConfig.build(),
                null, globalConfig, null);
        // 按照名字排序
        List<TableInfo> tables = builder.getTableInfoList();
        tables.sort(Comparator.comparing(TableInfo::getName));
        return tables;
    }

}
