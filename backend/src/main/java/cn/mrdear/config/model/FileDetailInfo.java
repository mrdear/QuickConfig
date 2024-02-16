package cn.mrdear.config.model;

import lombok.Data;

/**
 * @author quding
 * @since 2024/2/16
 */
@Data
public class FileDetailInfo {

    private String path;

    private String content;

    private String md5;

    private String info;

}
