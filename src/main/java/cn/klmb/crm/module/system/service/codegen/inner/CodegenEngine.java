package cn.klmb.crm.module.system.service.codegen.inner;

import static cn.hutool.core.map.MapUtil.getStr;
import static cn.hutool.core.text.CharSequenceUtil.lowerFirst;
import static cn.hutool.core.text.CharSequenceUtil.removePrefix;
import static cn.hutool.core.text.CharSequenceUtil.toSymbolCase;
import static cn.hutool.core.text.CharSequenceUtil.toUnderlineCase;
import static cn.hutool.core.text.CharSequenceUtil.upperFirst;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.crm.framework.codegen.config.CodegenProperties;
import cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.common.pojo.PageParam;
import cn.klmb.crm.framework.common.pojo.PageResult;
import cn.klmb.crm.framework.common.util.collection.CollectionUtils;
import cn.klmb.crm.framework.common.util.date.DateUtils;
import cn.klmb.crm.framework.common.util.object.ObjectUtils;
import cn.klmb.crm.framework.excel.core.annotations.DictFormat;
import cn.klmb.crm.framework.excel.core.convert.DictConvert;
import cn.klmb.crm.framework.excel.core.util.ExcelUtils;
import cn.klmb.crm.framework.mybatis.core.mapper.BaseMapperX;
import cn.klmb.crm.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.klmb.crm.module.system.entity.codegen.CodegenColumnDO;
import cn.klmb.crm.module.system.entity.codegen.CodegenTableDO;
import cn.klmb.crm.module.system.enums.codegen.CodegenSceneEnum;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 代码生成的引擎，用于具体生成代码 目前基于 {@link org.apache.velocity.app.Velocity} 模板引擎实现
 * <p>
 * 考虑到 Java 模板引擎的框架非常多，Freemarker、Velocity、Thymeleaf 等等，所以我们采用 hutool 封装的
 * {@link cn.hutool.extra.template.Template} 抽象
 *
 * @author 快乐萌宝
 */
@Component
public class CodegenEngine {

    /**
     * 模板配置 key：模板在 resources 的地址 value：生成的路径
     */
    private static final Map<String, String> TEMPLATES = MapUtil.<String, String>builder(
                    new LinkedHashMap<>()) // 有序
            // Java module-biz Main
            .put(javaTemplatePath("controller/vo/baseVO"), javaModuleImplVOFilePath("BaseVO"))
            .put(javaTemplatePath("controller/vo/saveReqVO"), javaModuleImplVOFilePath("SaveReqVO"))
            .put(javaTemplatePath("controller/vo/pageReqVO"), javaModuleImplVOFilePath("PageReqVO"))
            .put(javaTemplatePath("controller/vo/respVO"), javaModuleImplVOFilePath("RespVO"))
            .put(javaTemplatePath("controller/vo/updateReqVO"), javaModuleImplVOFilePath("UpdateReqVO"))
//            .put(javaTemplatePath("controller/vo/exportReqVO"), javaModuleImplVOFilePath("ExportReqVO"))
//            .put(javaTemplatePath("controller/vo/excelVO"), javaModuleImplVOFilePath("ExcelVO"))
            .put(javaTemplatePath("controller/controller"), javaModuleImplControllerFilePath())
            .put(javaTemplatePath("convert/convert"), javaModuleImplMainFilePath("convert/${table.businessName}/${table.className}Convert"))
            .put(javaTemplatePath("dto/dto"), javaModuleImplMainFilePath("dto/${table.businessName}/${table.className}QueryDTO"))
            .put(javaTemplatePath("entity/do"), javaModuleImplMainFilePath("entity/${table.businessName}/${table.className}DO"))
            .put(javaTemplatePath("dao/mapper"), javaModuleImplMainFilePath("dao/${table.businessName}/${table.className}Mapper"))
            .put(javaTemplatePath("dao/mapper.xml"), mapperXmlFilePath())
            .put(javaTemplatePath("service/serviceImpl"), javaModuleImplMainFilePath("service/${table.businessName}/${table.className}ServiceImpl"))
            .put(javaTemplatePath("service/service"), javaModuleImplMainFilePath("service/${table.businessName}/${table.className}Service"))
            // Java module-api Main
            .put(javaTemplatePath("enums/errorcode"), javaModuleApiMainFilePath("enums/ErrorCodeConstants_手动操作"))
            // SQL
            .put("codegen/sql/sql.vm", "sql/sql.sql")
            .build();

    @Resource
    private CodegenProperties codegenProperties;

    /**
     * 模板引擎，由 hutool 实现
     */
    private final TemplateEngine templateEngine;
    /**
     * 全局通用变量映射
     */
    private final Map<String, Object> globalBindingMap = new HashMap<>();

    public CodegenEngine() {
        // 初始化 TemplateEngine 属性
        TemplateConfig config = new TemplateConfig();
        config.setResourceMode(TemplateConfig.ResourceMode.CLASSPATH);
        this.templateEngine = new VelocityEngine(config);
    }

    @PostConstruct
    private void initGlobalBindingMap() {
        // 全局配置
        globalBindingMap.put("basePackage", codegenProperties.getBasePackage());
        globalBindingMap.put("baseFrameworkPackage", codegenProperties.getBasePackage()
                + '.' + "framework"); // 用于后续获取测试类的 package 地址
        // 全局 Java Bean
        globalBindingMap.put("CommonResultClassName", CommonResult.class.getName());
        globalBindingMap.put("PageResultClassName", PageResult.class.getName());
        // VO 类，独有字段
        globalBindingMap.put("PageParamClassName", PageParam.class.getName());
        globalBindingMap.put("DictFormatClassName", DictFormat.class.getName());
        // DO 类，独有字段
        globalBindingMap.put("BaseDOClassName", KlmbBaseDO.class.getName());
        globalBindingMap.put("baseDOFields", CodegenBuilder.BASE_DO_FIELDS);
        globalBindingMap.put("QueryWrapperClassName", LambdaQueryWrapperX.class.getName());
        globalBindingMap.put("BaseMapperClassName", BaseMapperX.class.getName());
        // Util 工具类
        globalBindingMap.put("ServiceExceptionUtilClassName", ServiceExceptionUtil.class.getName());
        globalBindingMap.put("DateUtilsClassName", DateUtils.class.getName());
        globalBindingMap.put("ExcelUtilsClassName", ExcelUtils.class.getName());
        globalBindingMap.put("ObjectUtilsClassName", ObjectUtils.class.getName());
        globalBindingMap.put("DictConvertClassName", DictConvert.class.getName());
//        globalBindingMap.put("OperateLogClassName", OperateLog.class.getName());
//        globalBindingMap.put("OperateTypeEnumClassName", OperateTypeEnum.class.getName());
    }

    public Map<String, String> execute(CodegenTableDO table, List<CodegenColumnDO> columns) {
        // 创建 bindingMap
        Map<String, Object> bindingMap = new HashMap<>(globalBindingMap);
        bindingMap.put("table", table);
        bindingMap.put("columns", columns);
        bindingMap.put("primaryColumn", CollectionUtils.findFirst(columns, CodegenColumnDO::getPrimaryKey)); // 主键字段
        bindingMap.put("sceneEnum", CodegenSceneEnum.valueOf(table.getScene()));

        // className 相关
        // 去掉指定前缀，将 TestDictType 转换成 DictType. 因为在 create 等方法后，不需要带上 Test 前缀
        String simpleClassName = removePrefix(table.getClassName(), upperFirst(table.getModuleName()));
        bindingMap.put("simpleClassName", simpleClassName);
        bindingMap.put("simpleClassName_underlineCase", toUnderlineCase(simpleClassName)); // 将 DictType 转换成 dict_type
        bindingMap.put("classNameVar", lowerFirst(table.getClassName())); // 将 TestDictType 转换成 testDictType，用于变量
        // 将 DictType 转换成 dict-type
        String simpleClassNameStrikeCase = toSymbolCase(simpleClassName, '-');
        bindingMap.put("simpleClassName_strikeCase", simpleClassNameStrikeCase);
        // permission 前缀
        bindingMap.put("permissionPrefix", table.getModuleName() + ":" + simpleClassNameStrikeCase);

        // 执行生成
        final Map<String, String> result = Maps.newLinkedHashMapWithExpectedSize(TEMPLATES.size()); // 有序
        TEMPLATES.forEach((vmPath, filePath) -> {
            filePath = formatFilePath(filePath, bindingMap);
            String content = templateEngine.getTemplate(vmPath).render(bindingMap);
            result.put(filePath, content);
        });
        return result;
    }

    private String formatFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StrUtil.replace(filePath, "${basePackage}",
                getStr(bindingMap, "basePackage").replaceAll("\\.", "/"));
        filePath = StrUtil.replace(filePath, "${classNameVar}",
                getStr(bindingMap, "classNameVar"));
        // sceneEnum 包含的字段
        CodegenSceneEnum sceneEnum = (CodegenSceneEnum) bindingMap.get("sceneEnum");
        filePath = StrUtil.replace(filePath, "${sceneEnum.prefixClass}", sceneEnum.getPrefixClass());
        filePath = StrUtil.replace(filePath, "${sceneEnum.basePackage}", sceneEnum.getBasePackage());
        // table 包含的字段
        CodegenTableDO table = (CodegenTableDO) bindingMap.get("table");
        filePath = StrUtil.replace(filePath, "${table.moduleName}", table.getModuleName());
        filePath = StrUtil.replace(filePath, "${table.businessName}", table.getBusinessName());
        filePath = StrUtil.replace(filePath, "${table.className}", table.getClassName());
        return filePath;
    }

    private static String javaTemplatePath(String path) {
        return "codegen/java/" + path + ".vm";
    }

    private static String javaModuleImplVOFilePath(String path) {
        return javaModuleFilePath("controller/${sceneEnum.basePackage}/${table.businessName}/" +
                "vo/${sceneEnum.prefixClass}${table.className}" + path, "main");
    }

    private static String javaModuleImplControllerFilePath() {
        return javaModuleFilePath("controller/${sceneEnum.basePackage}/${table.businessName}/" +
                "${sceneEnum.prefixClass}${table.className}Controller", "main");
    }

    private static String javaModuleImplMainFilePath(String path) {
        return javaModuleFilePath(path, "main");
    }

    private static String javaModuleApiMainFilePath(String path) {
        return javaModuleFilePath(path, "main");
    }

    private static String javaModuleFilePath(String path, String src) {
        return "src/" + src + "/java/${basePackage}/module/${table.moduleName}/" + path + ".java";
    }

    private static String mapperXmlFilePath() {
        return "src/main/resources/mapper/${table.businessName}/${table.className}Mapper.xml";
    }

}
