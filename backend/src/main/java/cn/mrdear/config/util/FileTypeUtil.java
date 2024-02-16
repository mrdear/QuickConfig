package cn.mrdear.config.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author quding
 * @since 2024/2/16
 */
public class FileTypeUtil {

    private static final Pattern FILE_PATTERN = Pattern.compile(".+\\s+text");

    /**
     * 是否为文本文件
     *
     * @param file 文件
     * @return true 文本
     */
    public static Pair<String, Boolean> isTextFile(File file) {
        try {
            // 调用 `file` 命令
            String[] command = {"file", file.getAbsolutePath()};
            Process process = Runtime.getRuntime().exec(command);

            // 读取 `file` 命令的输出
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                byte[] output = new byte[process.getInputStream().available()];
                process.getInputStream().read(output);
                String outputStr = new String(output);

                // 空文件
                if (outputStr.endsWith("empty\n")) {
                    return new Pair<>(outputStr, true);
                }
                // 结构化文件
                if (outputStr.endsWith(" data\n")) {
                    return new Pair<>(outputStr, true);
                }

                // 输出文件类型
                Matcher matcher = FILE_PATTERN.matcher(outputStr);
                return new Pair<>(outputStr, matcher.find());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
