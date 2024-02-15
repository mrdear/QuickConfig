package cn.mrdear.config.model;

/**
 * @author quding
 * @since 2024/2/15
 */
public class FileInfo {

    private String path;

    private String content;

    private String md5;

    public String getPath() {
        return path;
    }

    public FileInfo setPath(String path) {
        this.path = path;
        return this;
    }

    public String getContent() {
        return content;
    }

    public FileInfo setContent(String content) {
        this.content = content;
        return this;
    }

    public String getMd5() {
        return md5;
    }

    public FileInfo setMd5(String md5) {
        this.md5 = md5;
        return this;
    }
}
