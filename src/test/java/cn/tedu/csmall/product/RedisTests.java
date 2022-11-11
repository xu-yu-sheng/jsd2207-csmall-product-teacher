package cn.tedu.csmall.product;

import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest
public class RedisTests {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    // Redis编程相关API
    // =====================
    // 【RedisTemplate类】
    // ValueOperations opsForValue() >>> 获取ValueOperations对象，操作Redis中的string类型时需要此对象
    // ListOperations opsForList() >>> 获取ListOperations对象，操作Redis中的list类型时需要此对象
    // Set<String> keys(String pattern) >>> 根据模式pattern搜索Key
    // Boolean delete(String key) >>> 根据Key删除数据，返回成功与否
    // Long delete(Collection<String> keys) >>> 根据Key的集合批量删除数据，返回成功删除的数据的数量
    //
    // 【ValueOperations类】
    // void set(String key, Serializable value) >>> 向Redis中写入数据
    // Serializable get(String key) >>> 读取Redis中的数据

    @Test
    void valueSet() {
        String key = "username1";
        String value = "王克晶";

        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        ops.set(key, value);
    }

    @Test
    void valueSetObject() {
        String key = "album2022";
        AlbumStandardVO album = new AlbumStandardVO();
        album.setId(2022L);
        album.setName("测试相册00001");
        album.setDescription("测试简介00001");
        album.setSort(998);

        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        ops.set(key, album);
    }

    @Test
    void valueGet() {
        String key = "username1";

        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        Serializable value = ops.get(key);
        log.debug("从Redis中取出Key值为【{}】的数据，结果：{}", key, value);
    }

    @Test
    void valueGetObject() {
        String key = "album2022";

        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        Serializable value = ops.get(key);
        log.debug("从Redis中取出Key值为【{}】的数据", key);
        log.debug("结果：{}", value);
        log.debug("结果的数据类型：{}", value.getClass().getName());
    }

    @Test
    void keys() {
        String pattern = "*";

        Set<String> keys = redisTemplate.keys(pattern);
        log.debug("根据模式【{}】搜索Key，结果：{}", pattern, keys);
    }

    @Test
    void delete() {
        String key = "username1";

        Boolean result = redisTemplate.delete(key);
        log.debug("根据Key【{}】删除数据完成，结果：{}", key, result);
    }

    @Test
    void deleteX() {
        Set<String> keys = new HashSet<>();
        keys.add("username2");
        keys.add("username3");
        keys.add("username4");

        Long count = redisTemplate.delete(keys);
        log.debug("根据Key集合【{}】删除数据完成，成功删除的数据的数量：{}", keys, count);
    }

    @Test
    public void rightPush() {
        String key = "stringList";
        List<String> stringList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            stringList.add("string-" + i);
        }

        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        for (String s : stringList) {
            ops.rightPush(key, s);
        }
    }

}
