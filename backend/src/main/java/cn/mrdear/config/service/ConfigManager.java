package cn.mrdear.config.service;

import cn.mrdear.config.config.DataKey;
import cn.mrdear.config.config.DataKeyProvider;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置属性
 *
 * @author quding
 * @since 2024/2/16
 */
@Singleton
public class ConfigManager implements DataKeyProvider {

    @ConfigProperty(name = "quick.config.root-path")
    String configPath;

    String configName = "QuickConfig.properties";


    @PostConstruct
    void initConfig() {
        try {
            Log.infof("config path is %s", configPath);
            File file = new File(configPath);
            if (!file.exists() && file.createNewFile()) {
                // 初始化配置
                boolean newFile = new File(file.getAbsolutePath() + File.separator + configName)
                        .createNewFile();
                assert newFile;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询配置
     */
    public Properties rootConfig() {
        String path = this.configPath + File.separator + this.configName;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getData(DataKey<T> dataKey) {
        Properties properties = rootConfig();
        String property = properties.getProperty(dataKey.dataId);

        if (dataKey.clazz.equals(Boolean.class)) {
            return (T) Boolean.valueOf(property);
        }
        return (T) property;
    }
}
