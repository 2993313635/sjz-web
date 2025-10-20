package com.boshuo.sjzgl;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class ResourceTest {
    @Test
    public void testResourceLoading() throws IOException {
        // 尝试加载XML资源
        InputStream is = getClass().getResourceAsStream("/mapper/GameGuideMapper.xml");
        assertNotNull("XML文件未找到", is);

        // 验证内容类型
        String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        assertTrue("文件内容不是XML", content.contains("<?xml"));
    }
}
