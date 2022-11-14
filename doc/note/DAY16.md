# 98. Redis编程（续）

![image-20221114093420529](images/image-20221114093420529.png)

关于Redis读写数据，常用API大致如下：

```java
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
    void rightPush() {
        String key = "stringList";
        List<String> stringList = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            stringList.add("string-" + i);
        }

        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        for (String s : stringList) {
            ops.rightPush(key, s);
        }
    }

    @Test
    void size() {
        String key = "stringList";
        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        Long size = ops.size(key);
        log.debug("根据Key【{}】读取列表的长度，结果：{}", key, size);
    }

    @Test
    void range() {
        String key = "stringList";
        long start = 0;
        long end = -1;

        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        List<Serializable> list = ops.range(key, start, end);
        log.debug("根据Key【{}】从【{}】到【{}】读取列表，结果长度：{}", key, start, end, list.size());
        for (Serializable item : list) {
            log.debug("列表项：{}", item);
        }
    }

}
```

# 99. 关于数据一致性问题的思考

当使用Redis缓存数据后，如果数据库中的数据发生了变化，此时，Redis中的数据会暂时与MySQL数据库中的数据并不相同，通常称之为“数据一致性”问题！对于此问题，只可能：

- 及时更新Redis缓存数据，使得Redis中的数据与MySQL数据库中的数据是一致的
- 放任数据不一致的表现，即Redis中的数据在接下来的一段时间里是不准确的，直至更新Redis中的数据，才会是准确的数据

如果及时更新Redis缓存数据，其优点是Redis缓存中的数据基本上是准确的，其缺点在于可能需要频繁的更新Redis缓存数据，本质上反复读MySQL、反复写Redis的操作，如果读取Redis数据的频率根本不高，则会形成浪费，并且，更新缓存的频率太高，也会增加服务器的压力，所以，对于增、删、改频率非常高的数据，可能不太适用此规则！

放任数据不一致的表现，其缺点很显然就是数据可能不准确，但是，其优点是没有给服务器端增加任何压力，需要注意：其实，并不是所有数据都必须时时刻刻都要求准确性的，某些数据即使不准确，也不会产生恶劣后果（例如热门话题排行榜，定期更新即可），或者，某些数据即使准确，也没有太多实际意义（例如热门时段的火车票、飞机票等，即使在列表中显示了正确的余量，也不一定能够成功购买）。

基于使用Redis缓存数据可能存在数据一致性问题，通常，使用Redis缓存的数据可能：

- 数据的增、删、改的频率非常低，查询频率相对更高（甚至非常高）
  - 例如电商平台中的商品类别、品牌
  - 无论采取即时更新Redis的策略，还是定期更新Redis的策略，都是可行的解决方案
- 对数据的准确性要求不高的数据
  - 例如某些列表或榜单
  - 通常使用定期更新Redis的策略，更新周期应该根据数据变化的频率及其价值来决定

# 100. 缓存品牌数据

通常，推荐将Redis的读写数据操作进行封装，则先在根包下创建`repo.IBrandRedisRepository`接口：

```java
public interface IBrandRedisRepository {}
```

然后，在根包下`repo.impl.BrandRedisRepositoryImpl`创建以上接口的实现类：

```java
@Slf4j
@Repository
public class BrandRedisRepositoryImpl implements IBrandRedisRepository {
    public BrandRedisRepositoryImpl() {
        log.debug("创建处理缓存的数据访问对象：BrandRedisRepositoryImpl");
    }
}
```

接下来，应该在接口中添加读写数据的抽象方法，并在实现类中实现这些方法！

先实现“写入某个品牌数据”的功能，则在`IBrandRedisRepository`接口中添加抽象方法：

```java
void save(BrandStandardVO brandStandardVO);
```

并在`BrandRedisRepositoryImpl`中实现此方法：

```java

```





























