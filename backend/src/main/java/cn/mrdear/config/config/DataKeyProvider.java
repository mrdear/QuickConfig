package cn.mrdear.config.config;

/**
 * @author quding
 * @since 2024/2/16
 */
public interface DataKeyProvider {

    /**
     * 提供datakey对应的值以及转换
     *
     * @param dataKey 属性key
     */
    <T> T getData(DataKey<T> dataKey);

}
