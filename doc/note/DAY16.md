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

在`IBrandRedisRepository`接口中添加抽象方法：

```java
package cn.tedu.csmall.product.repo;

import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;

import java.util.List;

/**
 * 处理品牌缓存的数据访问接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IBrandRedisRepository {
    
    /**
     * 品牌数据项在Redis中的Key前缀
     */
    String BRAND_ITEM_KEY_PREFIX = "brand:item:";
    /**
     * 品牌列表在Redis中的Key
     */
    String BRAND_LIST_KEY = "brand:list";
    /**
     * 所有品牌数据项的Key
     */
    String BRAND_ITEM_KEYS_KEY = "brand:item-keys";

    /**
     * 向Redis中写入品牌数据
     *
     * @param brandStandardVO 品牌数据
     */
    void save(BrandStandardVO brandStandardVO);

    /**
     * 向Redis中写入品牌列表
     *
     * @param brands 品牌列表
     */
    void save(List<BrandListItemVO> brands);

    /**
     * 删除Redis中全部品牌数据，包括各品牌详情数据和品牌列表等
     *
     * @return 成功删除的数据的数量
     */
    Long deleteAll();

    /**
     * 从Redis中读取品牌数据
     *
     * @param id 品牌id
     * @return 匹配的品牌数据，如果没有匹配的数据，则返回null
     */
    BrandStandardVO get(Long id);

    /**
     * 从Redis中读取品牌列表
     *
     * @return 品牌列表
     */
    List<BrandListItemVO> list();

    /**
     * 从Redis中读取品牌列表
     *
     * @param start 读取数据的起始下标
     * @param end   读取数据的截止下标
     * @return 品牌列表
     */
    List<BrandListItemVO> list(long start, long end);

}
```

并在`BrandRedisRepositoryImpl`中实现此方法：

```java
package cn.tedu.csmall.product.repo.impl;

import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.repo.IBrandRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 处理品牌缓存的数据访问实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Repository
public class BrandRedisRepositoryImpl implements IBrandRedisRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public BrandRedisRepositoryImpl() {
        log.debug("创建处理缓存的数据访问对象：BrandRedisRepositoryImpl");
    }

    @Override
    public void save(BrandStandardVO brandStandardVO) {
        String key = BRAND_ITEM_KEY_PREFIX + brandStandardVO.getId();
        redisTemplate.opsForSet().add(BRAND_ITEM_KEYS_KEY, key);
        redisTemplate.opsForValue().set(key, brandStandardVO);
    }

    @Override
    public void save(List<BrandListItemVO> brands) {
        String key = BRAND_LIST_KEY;
        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        for (BrandListItemVO brand : brands) {
            ops.rightPush(key, brand);
        }
    }

    @Override
    public Long deleteAll() {
        // 获取到所有item的key
        Set<Serializable> members = redisTemplate
                .opsForSet().members(BRAND_ITEM_KEYS_KEY);
        Set<String> keys = new HashSet<>();
        for (Serializable member : members) {
            keys.add((String) member);
        }
        // 将List和保存Key的Set的Key也添加到集合中
        keys.add(BRAND_LIST_KEY);
        keys.add(BRAND_ITEM_KEYS_KEY);
        return redisTemplate.delete(keys);
    }

    @Override
    public BrandStandardVO get(Long id) {
        Serializable serializable = redisTemplate
                .opsForValue().get(BRAND_ITEM_KEY_PREFIX + id);
        BrandStandardVO brandStandardVO = null;
        if (serializable != null) {
            if (serializable instanceof BrandStandardVO) {
                brandStandardVO = (BrandStandardVO) serializable;
            }
        }
        return brandStandardVO;
    }

    @Override
    public List<BrandListItemVO> list() {
        long start = 0;
        long end = -1;
        return list(start, end);
    }

    @Override
    public List<BrandListItemVO> list(long start, long end) {
        String key = BRAND_LIST_KEY;
        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        List<Serializable> list = ops.range(key, start, end);
        List<BrandListItemVO> brands = new ArrayList<>();
        for (Serializable item : list) {
            brands.add((BrandListItemVO) item);
        }
        return brands;
    }

}
```

完成后，可以调整“查询品牌列表”的业务，将原本从数据库中查询数据改为从Redis缓存中查询数据，则修改`BrandServiceImpl`中的`list()`方法：

```java
@Override
public List<BrandListItemVO> list() {
    log.debug("开始处理【查询品牌列表】的业务，无参数");
    // return brandMapper.list();
    return brandRedisRepository.list();
}
```

至于，查询品牌列表时将从Redis中查询数据。

# 101. 缓存预热

当启用项目时就将缓存数据加载到Redis缓存中，这种做法通常称之为“缓存预热”。

在Spring Boot项目中，自定义组件类，实现`ApplicationRunner`接口，此接口中的`run()`方法将在启动项目之后自动执行，可以通过此机制实现缓存预热。

在根包下创建`preload.CachePreload`类并实现缓存预热：

```java
package cn.tedu.csmall.product.preload;

import cn.tedu.csmall.product.mapper.BrandMapper;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.repo.IBrandRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CachePreload implements ApplicationRunner {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private IBrandRedisRepository brandRedisRepository;

    public CachePreload() {
        log.debug("创建开机自动执行的组件对象：CachePreload");
    }

    // ApplicationRunner中的run()方法会在项目启动成功之后自动执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("CachePreload.run()");

        log.debug("准备删除Redis缓存中的品牌数据……");
        brandRedisRepository.deleteAll();
        log.debug("删除Redis缓存中的品牌数据，完成！");

        log.debug("准备从数据库中读取品牌列表……");
        List<BrandListItemVO> list = brandMapper.list();
        log.debug("从数据库中读取品牌列表，完成！");

        log.debug("准备将品牌列表写入到Redis缓存……");
        brandRedisRepository.save(list);
        log.debug("将品牌列表写入到Redis缓存，完成！");
    }

}
```

# 102. 计划任务

计划任务：设定某种规则（通常是与时间相关的规则），当满足规则时，自动执行任务，并且，此规则可能是周期性的满足，则任务也会周期性的执行。

在Spring Boot项目中，需要在配置类上添加`@EnableScheduling`注解，以开启计划任务，否则，当前项目中所有计划任务都是不允许执行的！

在任何组件类中，自定义方法，在方法上添加`@Scheduled`注解，则此方法就是计划任务，通过此注解的参数可以配置计划任务的执行规律。

在根包下创建`config.ScheduleConfiguration`类，在此类上添加`@EnableScheduling`注解，以开启计划任务：

```java
package cn.tedu.csmall.product.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 计划任务配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfiguration {

    public ScheduleConfiguration() {
        log.debug("创建配置类对象：ScheduleConfiguration");
    }

}
```

然后，在根包下创建`schedule.CacheSchedule`类，在类上添加`@Component`注解，并在类中自定义计划任务方法：

```java

```





















