package cn.mrdear.config.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author quding
 * @since 2024/2/16
 */
class FileTypeUtilTest {

    @Test
    public void test_isTextFile() {
        File textFile = new File("src/test/resources/files/test.txt");

        Pair<String, Boolean> pair = FileTypeUtil.isTextFile(textFile);
        assertNotNull(pair);
        assertTrue(pair.right());
    }


}