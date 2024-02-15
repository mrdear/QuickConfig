package cn.mrdear.config.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author quding
 * @since 2024/2/15
 */
class MD5UtilTest {

    @Test
    void getMD5() {
        String md5 = MD5Util.getMD5("123456");

        Assertions.assertEquals("e10adc3949ba59abbe56e057f20f883e", md5);
    }
}