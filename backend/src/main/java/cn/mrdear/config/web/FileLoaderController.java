package cn.mrdear.config.web;

import cn.mrdear.config.model.FileDetailInfo;
import cn.mrdear.config.model.FileInfo;
import cn.mrdear.config.model.SaveFilePayload;
import cn.mrdear.config.service.LocalFileService;
import io.netty.util.internal.StringUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.util.List;

/**
 * @author quding
 * @since 2024/2/15
 */
@Path("api/file")
public class FileLoaderController {

    @Inject
    LocalFileService localFileService;

    /**
     * 遍历一级文件夹
     *
     * @param path 文件夹
     * @return 文件路径
     */
    @POST
    @Path("walk")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FileInfo> walk(@FormParam("filePath") String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("filePath can't be null");
        }
        File file = new File(path);
        return localFileService.walkFiles(file);
    }


    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public FileDetailInfo getFile(@FormParam("filePath") String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("filePath can't be null");
        }

        File file = new File(path);
        return localFileService.getTextFile(file);
    }

    /**
     * 保存文件
     */
    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public FileDetailInfo saveFile(SaveFilePayload payload) {
        if (null == payload) {
            throw new IllegalArgumentException("payload can't be null");
        }

        String fileContent = payload.getFileContent();
        String filePath = payload.getFilePath();
        String md5 = payload.getPrevMd5();

        FileDetailInfo oldFileInfo = new FileDetailInfo();
        oldFileInfo.setPath(filePath);
        oldFileInfo.setMd5(md5);

        return localFileService.saveFile(fileContent, oldFileInfo);
    }

}
