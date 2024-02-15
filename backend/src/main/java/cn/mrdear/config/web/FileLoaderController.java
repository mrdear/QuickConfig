package cn.mrdear.config.web;

import cn.mrdear.config.model.FileInfo;
import cn.mrdear.config.model.SaveFilePayload;
import cn.mrdear.config.util.MD5Util;
import io.netty.util.internal.StringUtil;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author quding
 * @since 2024/2/15
 */
@Path("api/file")
public class FileLoaderController {


    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public FileInfo getFile(@FormParam("filePath") String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("filePath can't be null");
        }

        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("filePath is not a file");
        }

        if (!file.canRead()) {
            throw new IllegalArgumentException("filePath can't read");
        }

        try {
            long length = file.length();
            if (length > 10485760) {
                // 文件大于 10M
                throw new IllegalArgumentException("The file size exceeds 10M");
            } else {
                FileInfo info = new FileInfo();
                info.setContent(Files.readString(file.toPath()));
                info.setMd5(MD5Util.getMD5(info.getContent()));
                info.setPath(path);
                return info;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存文件
     */
    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public FileInfo saveFile(SaveFilePayload payload) {
        if (null == payload) {
            throw new IllegalArgumentException("payload can't be null");
        }

        String fileContent = payload.getFileContent();
        String filePath = payload.getFilePath();
        String md5 = payload.getPrevMd5();

        FileInfo fileInfo = getFile(filePath);
        if (!fileInfo.getMd5().equalsIgnoreCase(md5)) {
            throw new IllegalArgumentException("origin file has changed");
        }

        try {
            Files.writeString(new File(filePath).toPath(), fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getFile(filePath);
    }

}
