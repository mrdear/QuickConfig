package cn.mrdear.config.model;

import lombok.Data;

/**
 * @author quding
 * @since 2024/2/15
 */
@Data
public class FileInfo {

    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件名
     */
    private String name;

    /**
     * 是否为目录
     */
    private Boolean isDir;
    /**
     * 是否为文本文件
     */
    private Boolean isText;
    /**
     * 是否有子项
     */
    private Boolean hasChildren;
    /**
     * file命令的详细信息
     */
    private String info;

}
