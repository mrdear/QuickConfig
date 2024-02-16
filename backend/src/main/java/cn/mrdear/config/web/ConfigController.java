package cn.mrdear.config.web;

import cn.mrdear.config.service.ConfigManager;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Properties;

/**
 * @author quding
 * @since 2024/2/16
 */
@Path("api/config")
public class ConfigController {

    @Inject
    ConfigManager configManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Properties config() {
        return configManager.rootConfig();
    }

}
