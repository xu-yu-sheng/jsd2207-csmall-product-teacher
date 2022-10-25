package cn.tedu.csmall.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class Slf4jTests {

    @Test
    public void log() {
        log.trace("这是一条【trace】级别的日志");
        log.debug("这是一条【debug】级别的日志");
        log.info("这是一条【info】级别的日志");
        log.warn("这是一条【warn】级别的日志");
        log.error("这是一条【error】级别的日志");

        int x = 1;
        int y = 2;
        log.info("{}+{}={}", x, y, x + y);
    }

}
