# Mybatis的缓存机制

Mybatis框架默认是有2套缓存机制的，分别称之一级缓存和二级缓存。

Mybatis框架的一级缓存也称之为“会话（Session）缓存”，默认是开启的，且无法关闭！

一级缓存必须保证多次的查询操作满足：同一个SqlSession、同一个Mapper、执行相同的SQL查询、使用相同的参数。

关于一级缓存的典型表现可以通过测试以下代码进行观察：

```java
@Slf4j
@SpringBootTest
public class MybatisCacheTests {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Test
    void l1Cache() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        Long id = 1L;
        log.debug("开始第【1】次执行根据ID【1】查询品牌详情……");
        BrandStandardVO result1 = brandMapper.getStandardById(id);
        log.debug("第【1】查询结果的hashCode()值为：{}", result1.hashCode());
        log.debug("开始第【2】次执行根据ID【1】查询品牌详情……");
        BrandStandardVO result2 = brandMapper.getStandardById(id);
        log.debug("第【2】查询结果的hashCode()值为：{}", result2.hashCode());

        log.debug("第【1】次的查询结果与第【2】的查询结果进行对比，结果：{}", result1 == result2);

        id = 2L;
        log.debug("开始第【1】次执行根据ID【2】查询品牌详情……");
        BrandStandardVO result3 = brandMapper.getStandardById(id);
        log.debug("第【1】查询结果的hashCode()值为：{}", result3.hashCode());
        log.debug("开始第【2】次执行根据ID【2】查询品牌详情……");
        BrandStandardVO result4 = brandMapper.getStandardById(id);
        log.debug("第【2】查询结果的hashCode()值为：{}", result4.hashCode());

        id = 1L;
        log.debug("开始第【3】次执行根据ID【1】查询品牌详情……");
        BrandStandardVO result5 = brandMapper.getStandardById(id);
        log.debug("第【3】查询结果的hashCode()值为：{}", result5.hashCode());
    }

}
```

一级缓存会因为以下任意一种原因而消失：

- 调用SqlSession对象的clearCache()方法，将清除当前会话中此前产生的所有一级缓存数据
- 当前执行了任何写操作（增 / 删 / 改），无论任何数据有没有发生变化，都会清空此前产生的缓存数据

演示代码：

```java
@Slf4j
@SpringBootTest
public class MybatisCacheTests {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Test
    void l1Cache() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        Long id = 1L;
        log.debug("开始第【1】次执行根据ID【1】查询品牌详情……");
        BrandStandardVO result1 = brandMapper.getStandardById(id);
        log.debug("第【1】查询结果的hashCode()值为：{}", result1.hashCode());
        log.debug("开始第【2】次执行根据ID【1】查询品牌详情……");
        BrandStandardVO result2 = brandMapper.getStandardById(id);
        log.debug("第【2】查询结果的hashCode()值为：{}", result2.hashCode());

        log.debug("第【1】次的查询结果与第【2】的查询结果进行对比，结果：{}", result1 == result2);

        id = 2L;
        log.debug("开始第【1】次执行根据ID【2】查询品牌详情……");
        BrandStandardVO result3 = brandMapper.getStandardById(id);
        log.debug("第【1】查询结果的hashCode()值为：{}", result3.hashCode());
        log.debug("开始第【2】次执行根据ID【2】查询品牌详情……");
        BrandStandardVO result4 = brandMapper.getStandardById(id);
        log.debug("第【2】查询结果的hashCode()值为：{}", result4.hashCode());

        // log.debug("即将调用SqlSession对象的clearCache()方法清除缓存……");
        // sqlSession.clearCache();
        // log.debug("已经清除以前产生的缓存数据！");

        log.debug("即将执行写操作……");
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("华为2022");
        brandMapper.update(brand);
        log.debug("执行写操作，完成！");

        id = 1L;
        log.debug("开始第【3】次执行根据ID【1】查询品牌详情……");
        BrandStandardVO result5 = brandMapper.getStandardById(id);
        log.debug("第【3】查询结果的hashCode()值为：{}", result5.hashCode());

        id = 2L;
        log.debug("开始第【3】次执行根据ID【2】查询品牌详情……");
        BrandStandardVO result6 = brandMapper.getStandardById(id);
        log.debug("第【3】查询结果的hashCode()值为：{}", result6.hashCode());
    }

}
```

Mybatis框架的二级缓存也称之为“namespace缓存”，是作用于某个namespace的，具体 表现为：无论是否为同一个SqlSession，只要执行的是相同的Mapper的查询，且查询参数相同，就可以应用二级缓存。

在使用Spring Boot与Mybatis的项目中，二级缓存默认是全局开启的，但各namespace默认并未开启，如果需要在namespace中开启二级缓存，需要在XML文件中添加`<cache/>`标签，则表示当前XML中所有查询都开启了二级缓存！

需要注意：使用二级缓存时，需要保证查询结果的类型实现了`Serializable`接口！

另外，还可以在`<select>`标签上配置`useCache`属性，以配置“是否使用缓存”，此属性的默认值为`true`，表示“使用缓存”。

当应用二级缓存后，在日志上会提示`[Cache Hit Ratio]`，表示“当前namespace缓存命中率”。

与一级缓存相同，只需要发生任何写操作，都会自动清除缓存数据！

Mybatis在查询数据时，会优先尝试从二级缓存中查询是否存在缓存数据，如果命中，将直接返回，如果未命中，则尝试从一级缓存中查询是否存在缓存数据，如果命中，将返回，如果仍未命中，将执行数据库查询。

二级缓存的示例代码：

```java
@Autowired
BrandMapper brandMapper;

@Test
void l2Cache() {
    Long id = 1L;
    log.debug("开始第【1】次执行根据ID【1】查询品牌详情……");
    BrandStandardVO result1 = brandMapper.getStandardById(id);
    log.debug("第【1】查询结果的hashCode()值为：{}", result1.hashCode());

    log.debug("开始第【2】次执行根据ID【1】查询品牌详情……");
    BrandStandardVO result2 = brandMapper.getStandardById(id);
    log.debug("第【2】查询结果的hashCode()值为：{}", result2.hashCode());

    log.debug("即将执行写操作……");
    Brand brand = new Brand();
    brand.setId(6L);
    brand.setName("微软2022");
    brandMapper.update(brand);
    log.debug("执行写操作，完成！");

    log.debug("开始第【3】次执行根据ID【1】查询品牌详情……");
    BrandStandardVO result3 = brandMapper.getStandardById(id);
    log.debug("第【3】查询结果的hashCode()值为：{}", result3.hashCode());

    log.debug("开始第【4】次执行根据ID【1】查询品牌详情……");
    BrandStandardVO result4 = brandMapper.getStandardById(id);
    log.debug("第【4】查询结果的hashCode()值为：{}", result4.hashCode());

    log.debug("开始第【5】次执行根据ID【1】查询品牌详情……");
    BrandStandardVO result5 = brandMapper.getStandardById(id);
    log.debug("第【5】查询结果的hashCode()值为：{}", result5.hashCode());
}
```

提示：一定要在XML中添加`<cache>`才能够使用二级缓存。





