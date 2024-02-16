package cn.mrdear.config.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据配置主键
 *
 * @author quding
 * @since 2024/2/16
 */
public class DataKey<T> {

    private static final Map<String, DataKey<?>> CACHE = new ConcurrentHashMap<>();

    public final String dataId;

    public final Class<T> clazz;

    private DataKey(String dataId, Class<T> clazz) {
        this.dataId = dataId;
        this.clazz = clazz;
    }

    /**
     * 获取数据
     *
     * @param provider 提供方
     * @return 获取结果
     */
    public T get(DataKeyProvider provider) {
        return provider.getData(this);
    }

    public T getOr(DataKeyProvider provider, T defaultValue) {
        T data = provider.getData(this);
        if (null == data) {
            return defaultValue;
        }
        return data;
    }


    /**
     * 构建string类型Key
     *
     * @param dataId 数据id
     * @return 构建结果
     */
    @SuppressWarnings("unchecked")
    public static DataKey<String> ofString(String dataId) {
        return (DataKey<String>) CACHE.computeIfAbsent(dataId, k -> new DataKey<>(dataId, String.class));
    }

    @SuppressWarnings("unchecked")
    public static DataKey<Boolean> ofBool(String dataId) {
        return (DataKey<Boolean>) CACHE.computeIfAbsent(dataId, k -> new DataKey<>(dataId, Boolean.class));
    }


}
