package cn.mrdear.config.service;

import cn.mrdear.config.config.ConfigDataKey;
import cn.mrdear.config.model.FileDetailInfo;
import cn.mrdear.config.model.FileInfo;
import cn.mrdear.config.util.FileTypeUtil;
import cn.mrdear.config.util.MD5Util;
import cn.mrdear.config.util.Pair;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 本地文件服务
 *
 * @author quding
 * @since 2024/2/16
 */
@Singleton
public class LocalFileService {
    /**
     * 最大文本文件
     */
    private static final Long MAX_TEXT_FILE = 10485760L;

    @Inject
    ConfigManager configManager;

    /**
     * 遍历一级文件夹
     *
     * @param file 文件
     * @return 遍历结果
     */
    public List<FileInfo> walkFiles(File file) {
        if (!file.isDirectory()) {
            return Collections.emptyList();
        }

        Boolean showHide = ConfigDataKey.DISPLAY_HIDE_FILE_SHOW.getOr(configManager, false);
        Boolean showEmpty = ConfigDataKey.DISPLAY_EMPTY_DIR_SHOW.getOr(configManager, false);

        try {
            Stream<Path> walk = Files.walk(file.toPath(), 1);
            return walk
                    .map(Path::toFile)
                    .filter(x -> {
                        if (showHide && x.isHidden()) {
                            return true;
                        }
                        return !x.isHidden();
                    })
                    .map(xFile -> {

                        FileInfo info = new FileInfo();
                        info.setPath(xFile.getAbsolutePath());
                        info.setName(xFile.getName());
                        info.setIsDir(xFile.isDirectory());
                        Pair<String, Boolean> pair = FileTypeUtil.isTextFile(xFile);
                        if (null == pair) {
                            return null;
                        }
                        info.setIsText(pair.right());
                        info.setInfo(pair.left());
                        if (info.getIsDir()) {
                            File[] files = xFile.listFiles();
                            info.setHasChildren((null != files && files.length > 0));
                        } else {
                            info.setHasChildren(false);
                        }

                        return info;
                    })
                    .filter(Objects::nonNull)
                    .filter(x -> {
                        if (!x.getIsDir()) {
                            return true;
                        }
                        if (showEmpty && !x.getHasChildren()) {
                            return true;
                        }
                        return x.getHasChildren();
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文本文件
     *
     * @param file 文本文件
     * @return 获取结果
     */
    public FileDetailInfo getTextFile(File file) {
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("filePath is not a file");
        }

        if (!file.canRead()) {
            throw new IllegalArgumentException("file can't read");
        }

        // 非文本文件,直接返回
        Pair<String, Boolean> pair = FileTypeUtil.isTextFile(file);
        if (null == pair || !pair.right()) {
            throw new IllegalArgumentException("file not a conf");
        }

        long length = file.length();
        if (length > MAX_TEXT_FILE) {
            // 文件大于 10M
            throw new IllegalArgumentException("The file size exceeds 10M");
        }

        // 读取文件
        try {
            FileDetailInfo info = new FileDetailInfo();
            info.setContent(Files.readString(file.toPath()));
            info.setMd5(MD5Util.getMD5(info.getContent()));
            info.setPath(file.getAbsolutePath());
            info.setInfo(pair.left());
            return info;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 替换文件内容
     *
     * @param fileContent 新文件内容
     * @param oldFile     原始文件
     * @return 替换后的结果
     */
    public FileDetailInfo saveFile(String fileContent, FileDetailInfo oldFile) {
        String path = oldFile.getPath();
        File file = new File(path);

        FileDetailInfo detailInfo = getTextFile(file);
        if (!detailInfo.getMd5().equalsIgnoreCase(oldFile.getMd5())) {
            throw new IllegalArgumentException("origin file has changed");
        }
        // 保存文件
        try {
            Files.writeString(file.toPath(), fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getTextFile(file);
    }


}
