package edu.bistu.copyright.protect.service.impl;

import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.service.ICopyrightService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Chanvo
 * @date 2023/5/14 12:30
 * @description
 */

@Slf4j
@SpringBootTest
class CopyrightServiceTest {

    @Resource
    ICopyrightService copyrightService;

    @Test
    public void testMP() {
        Copyright copyright = Copyright.builder()
                .content("content")
                .description("description")
                .originFileUrl("url1")
                .watermarkFileUrl("url2")
                .md5Hash("1")
                .sha1Hash("2")
                .sha256Hash("3")
                .chainAddress("4")
                .userId(5)
                .build();
        copyrightService.save(copyright);
        log.info("New One: {}", copyright);
        copyright.setContent(null);
        copyright.setDescription(null);
        log.info("Update One: {}", copyright);
        copyrightService.removeById(copyright.getId());
    }

}