package cn.mrdear.config.config;

/**
 * @author quding
 * @since 2024/2/16
 */
public class ConfigDataKey {

    /**
     * 展示目录
     */
    public static final DataKey<String> DISPLAY_ROOT_PATH = DataKey.ofString("quick-config.display.root-path");
    /**
     * 展示隐藏文件
     */
    public static final DataKey<Boolean> DISPLAY_HIDE_FILE_SHOW = DataKey.ofBool("quick-config.display.hide-file.show");
    /**
     * 展示空文件夹
     */
    public static final DataKey<Boolean> DISPLAY_EMPTY_DIR_SHOW = DataKey.ofBool("quick-config.display.empty-dir.show");

}
