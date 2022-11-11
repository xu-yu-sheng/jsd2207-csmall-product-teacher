package cn.tedu.csmall.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;

@Slf4j
@SpringBootTest
public class RedisTests {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    // Redis编程相关API
    // =====================
    // RedisTemplate类
    // opsForValue() >>> 获取ValueOperations对象，操作Redis中的string类型时需要此对象
    // ValueOperations类
    // void set(String key, Serializable value) >>> 向Redis中写入数据
    // Serializable get(String key) >>> 读取Redis中的数据

    @Test
    void valueSet() {
        String key = "username1";
        String value = "wangkejing";

        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        ops.set(key, value);
    }

    @Test
    void valueGet() {
        String key = "username1";

        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        Serializable value = ops.get(key);
        log.debug("从Redis中取出Key值为【{}】的数据，结果：{}", key, value);
    }


}
