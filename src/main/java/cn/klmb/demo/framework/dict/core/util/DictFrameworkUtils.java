package cn.klmb.demo.framework.dict.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.klmb.demo.framework.common.core.KeyValue;
import cn.klmb.demo.framework.common.util.cache.CacheUtils;
import cn.klmb.demo.module.system.convert.dict.SysDictDataConvert;
import cn.klmb.demo.module.system.dto.SysDictDataRespDTO;
import cn.klmb.demo.module.system.service.dict.SysDictDataService;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.time.Duration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典工具类
 *
 * @author 快乐萌宝
 */
@Slf4j
public class DictFrameworkUtils {

    private static SysDictDataService sysDictDataService;

    private static final SysDictDataRespDTO DICT_DATA_NULL = new SysDictDataRespDTO();

    /**
     * 针对 {@link #getDictDataLabel(String, String)} 的缓存
     */
    private static final LoadingCache<KeyValue<String, String>, SysDictDataRespDTO> GET_DICT_DATA_CACHE = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<String, String>, SysDictDataRespDTO>() {

                @Override
                public SysDictDataRespDTO load(KeyValue<String, String> key) {
                    SysDictDataRespDTO respDTO = SysDictDataConvert.INSTANCE.convert1(
                            sysDictDataService.getByDictTypeAndValue(key.getKey(), key.getValue()));
                    return ObjectUtil.defaultIfNull(respDTO, DICT_DATA_NULL);
                }

            });

    /**
     * 针对 {@link #parseDictDataValue(String, String)} 的缓存
     */
    private static final LoadingCache<KeyValue<String, String>, SysDictDataRespDTO> PARSE_DICT_DATA_CACHE = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<String, String>, SysDictDataRespDTO>() {

                @Override
                public SysDictDataRespDTO load(KeyValue<String, String> key) {
                    SysDictDataRespDTO respDTO = SysDictDataConvert.INSTANCE.convert1(
                            sysDictDataService.getByDictTypeAndLabel(key.getKey(), key.getValue()));
                    return ObjectUtil.defaultIfNull(respDTO, DICT_DATA_NULL);
                }

            });

    public static void init(SysDictDataService sysDictDataService) {
        DictFrameworkUtils.sysDictDataService = sysDictDataService;
        log.info("[init][初始化 DictFrameworkUtils 成功]");
    }

    @SneakyThrows
    public static String getDictDataLabel(String dictType, String value) {
        return GET_DICT_DATA_CACHE.get(new KeyValue<>(dictType, value)).getLabel();
    }

    @SneakyThrows
    public static String parseDictDataValue(String dictType, String label) {
        return PARSE_DICT_DATA_CACHE.get(new KeyValue<>(dictType, label)).getValue();
    }

}
