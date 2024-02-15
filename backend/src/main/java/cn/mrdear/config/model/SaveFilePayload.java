package cn.mrdear.config.model;

/**
 * @author quding
 * @since 2024/2/15
 */
public class SaveFilePayload {

    private String filePath;

    private String fileContent;

    private String prevMd5;


    public String getFilePath() {
        return filePath;
    }

    public SaveFilePayload setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFileContent() {
        return fileContent;
    }

    public SaveFilePayload setFileContent(String fileContent) {
        this.fileContent = fileContent;
        return this;
    }

    public String getPrevMd5() {
        return prevMd5;
    }

    public SaveFilePayload setPrevMd5(String prevMd5) {
        this.prevMd5 = prevMd5;
        return this;
    }
}
