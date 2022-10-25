# 6. 关于Lombok框架

Lombok框架的主要作用是通过注解可以**在编译期生成**某些代码，例如Setters & Getters、`hashCode()`与`equals()`、`toString()`方法等，可以简化开发。

由于源代码（`.java`）文件中并没有相关代码，所以，默认情况下，开发工具无法智能提示，直接写出相关代码也会提示错误，则需要在开发工具中安装Lombok插件，安装方式：打开IntelliJ IDEA的设置，在**Plugins**一栏，在`Marketplace`中找到`Lombok`并安装即可。

Lombok的常用注解有：

- `@Data`：添加在类上，可在编译期生成全部属性对应的Setters & Getters、`hashCode()`与`equals()`、`toString()`，使用此注解时，必须保证当前类的父类存在无参数构造方法
- `@Setter`：可以添加在属性上，将仅作用于当前属性，也可以添加在类上，将作用于类中所有属性，用于生成对应的Setter方法
- `@Getter`：同上，用于生成对应的Getter方法
- `@EqualsAndHashCode`：添加在类上，用于生成规范的`equals()`和`hashCode()`，关于`equals()`方法，如果2个对象的所有属性的值完全相同，则返回`true`，否则返回`false`，关于`hashCode()`也是如此，如果2个对象的所有属性的值完全相同，则生成的HashCode值相同，否则，不应该相同
- `@ToString`：添加在类上，用于生成全属性对应的`toString()`方法
- `@Slf4j`：添加在类上，？？？

# 7. 关于自动装配Mapper对象时报错

当自动装配Mapper接口的对象时，IntelliJ IDEA可能会报错，提示无法装配此对象，但是，并不影响运行！

如果要解决IntelliJ IDEA错误的提示，可以：

- 使用`@Resource`注解替换`@Autowired`注解
  - 不推荐，只要是使用自动装配，都应该使用`@Autowired`注解
- 不再使用`@MapperScan`来指定Mapper接口的包，而改为在各Mapper接口上添加`@Mapper`注解
  - 不推荐，使用`@MapperScan`是一劳永逸的做法，更加省事
- 在各Mapper接口上添加`@Repository`注解
  - 推荐
  - 与添加`@Mapper`注解的本质不同，添加`@Mapper`注解是为了标识此接口是Mybatis框架应该处理的接口，添加`@Repository`注解是为了引导IntelliJ IDEA作出正确的判断

# 8. 关于Slf4j日志

在Spring Boot项目中，基础依赖项（`spring-boot-starter`）中已经包含了日志的相关依赖项。

在添加了`Lombok`依赖项后，可以在类上添加`@Slf4j`注解，则Lombok框架会在编译期生成名为`log`的变量，可调用此变量的方法来输出日志。

日志是有显示级别的，根据日志内容的重要程度，从不重要到重要，依次为：

- `trace`：跟踪信息，可能包含不一定关注，但是包含了程序执行流程的信息
- `debug`：调试信息，可能包含一些敏感内容，比如关键数据的值
- `info`：一般信息
- `warn`：警告信息
- `error`：错误信息

使用Slf4j时，可以使用`log`变量调用以上5个级别对应的方法，来输出不同级别的日志！

在Spring Boot项目中，默认的日志显示级别为【info】，将只会显示此级别及更重要级别的日志！可以在`application.properties`中配置`logging.level.根包=日志显示级别`来设置当前显示级别，例如：

```properties
# 日志的显示级别
logging.level.cn.tedu.csmall=error
```

在开发实践中，应该使用`trace`或`debug`级别的日志来输出与流程相关的、涉及敏感数据的日志，使用`info`输出一般的、被显示在控制台也无所谓的信息，使用`warn`和`error`输出更加重要的、需要关注的日志。

输出日志时，通常建议使用`void trace(String message, Object... args)`方法（也有其它级别日志的同样参数列表的方法），在第1个参数`String message`中，可以使用`{}`作为占位符，表示某变量的值，然后，通过第2个参数`Object... args`来表示各占位符对应的值，例如：

```java
int x = 1;
int y = 2;
log.info("{}+{}={}", x, y, x + y);
```

使用以上做法，可以避免字符串的拼接，提高了代码的可阅读性，也提高了程序的执行效率！并且，在Slf4j日志中，以上第1个参数由于是字符串常量，将被缓存，如果反复执行此日志输出，执行效率也会更高！

另外，SLF4j是一个日志标准，并不是具体的实现，通常，是通过`logback`或`log4j`等日志框架实现的，当前主流的Spring Boot版本中，都是使用`logback`来实现的。

# 9. 关于Profile配置

同一个项目，在不同的环境中（例如开发环境、测试环境、生产环境），需要的配置值可能是不同的，例如日志的显示级别、连接数据库的配置参数等，如果把同一个配置文件的多个属性的值反复修改是不现实的！

Spring框架提供了Profile配置机制，在Spring Boot中更是简化了此项操作，它允许使用`application-自定义名称.properties`作为Profile配置文件的文件名，这类配置文件默认是不加载的！

例如，在`application.properties`的同级路径下创建`application-dev.properties`，添加配置：

```properties
# ######################### #
# 此配置文件是【开发环境】的配置 #
#  此配置文件需要被激活才会生效 #
# ######################## #

# 连接数据库的配置参数
spring.datasource.url=jdbc:mysql://localhost:3306/mall_pms?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=root

# 日志的显示级别
logging.level.cn.tedu.csmall=trace
```

然后，在`application.properties`中，根据以上配置文件的名称（`application-dev.properties`）来激活以上配置文件：

```properties
# 激活Profile配置
spring.profiles.active=dev
```

所以，`application.properties`是始终加载的配置，而`application-自定义名称.properties`是必须激活才会加载的配置！

如果`application.properties`与被激活的Profile配置中存在同名的属性，配置值却不相同，在执行时，将以Profile配置为准！

# 10. 关于YAML配置

YAML是一种编写配置文件的语法，表现为使用`.yml`作为扩展名的配置文件，Spring框架默认并不支持此类配置文件，而Spring Boot的基础依赖项中已经包含解析此类文件的依赖项，所以，在Spring Boot项目可以直接使用此类配置文件。

在Spring Boot项目中，使用`.properties`和`.yml`配置是等效的，均可以正常识别并使用！

在YAML语法中，其典型特征是：

- 如果属性名中有小数点，则可以改为冒号，并且，冒号的右侧应该换行且缩进**2个空格**
  - 在IntelliJ IDEA中编辑YAML语法的配置时，会自动将按下的TAB键的内容转换成2个空格
- 如果多个属性名称中有相同的部分，不必（也不可）重复配置，只需要保持正确的缩进即可
- 属性名与属性值之间使用1个冒号和1个空格进行分隔
- 对于纯数值类型的属性值，可能需要使用双引号框住
- 也能识别例如`xx.xx.xx`这类属性名

例如：在`.properties`中的配置为：

```properties
spring.datasource.username=root
spring.datasource.password=root
```

在`.yml`中则配置为：

```yaml
spring:
  datasource:
    username: root
    password: root
```

**注意：YAML的解析相对更加严格，如果在此类配置文件中出现了错误的语法，甚至只是一些不应该出现的字符，都会导致解析失败！并且，如果直接复制粘贴整个文件，还可能出现乱码问题！**

# 11. 插入数据时获取自动编号的id

如果表中的id是自动编号的，在`<insert>`标签上，可以配置`useGeneratedKeys="true"`和`keyProperty="属性名"`，将可以获取自动编号的id值，并由Mybatis自动赋值到参数对象的属性（`keyProperty`配置的值）上，例如：

```xml
<!-- int insert(Album album); -->
<insert id="insert" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO pms_album (
        name, description, sort
    ) VALUES (
        #{name}, #{description}, #{sort}
    )
</insert>
```

> 提示：如果表的id不是自动编号的，则插入数据时必须由方法的调用者给出id值，所以，对于方法的调用者而言，id值是已知的，则不需要配置这2个属性。

# 12. 关于BindingException

当调用的方法找不到绑定的SQL语句时，将出现错误，例如：

```
org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): cn.tedu.csmall.product.mapper.AlbumMapper.insert
```

出现此错误的原因可能是：

- 在XML文件中，`<mapper>`标签的`namespace`值有误
- 在XML文件中，`<insert>`或类似标签的`id`值有误
- 在配置文件（`application.properties` / `application.yml`）中，配置的`mybatis.mapper-locations`属性有误，可能属性名写错，或属性值写错

注意：以上异常信息中已经明确表示了哪个接口的哪个方法缺少对应的SQL语句，可以以此为线索来排查错误。

# 13. 批量插入相册数据

首先，应该在`AlbumMapper`接口中添加新的抽象方法：

```java
int insertBatch(List<Album> albums);
```

然后，在`AlbumMapper.xml`中配置以上抽象方法映射的SQL语句：

```xml
<!-- int insertBatch(List<Album> albums); -->
<insert id="insertBatch" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO pms_album (
    	name, description, sort
    ) VALUES
    <foreach collection="list" item="album" separator=",">
        (#{album.name}, #{album.description}, #{album.sort})
    </foreach>
</insert>
```

最后，在`AlbumMapperTests`中编写并执行测试：

```java
@Test
void insertBatch() {
    List<Album> albums = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
        Album album = new Album();
        album.setName("批量插入测试相册" + i);
        album.setDescription("批量插入测试相册的简介" + i);
        album.setSort(200);
        albums.add(album);
    }
    
    int rows = mapper.insertBatch(albums);
    log.debug("批量插入完成，受影响的行数：{}", rows);
}
```

# 14. 根据id删除相册数据

首先，应该在`AlbumMapper`接口中添加新的抽象方法：

```java
int deleteById(Long id);
```

然后，在`AlbumMapper.xml`中配置以上抽象方法映射的SQL语句：

```xml
<!-- int deleteById(Long id); -->
<delete id="deleteById">
    DELETE FROM pms_album WHERE id=#{id}
</delete>
```

最后，在`AlbumMapperTests`中编写并执行测试：

```java
@Test
void deleteById() {
    Long id = 1L;
    int rows = mapper.deleteById(id);
    log.debug("删除完成，受影响的行数：{}", rows);
}
```

# 15. 根据若干个id批量删除相册数据

首先，应该在`AlbumMapper`接口中添加新的抽象方法：

```java
int deleteByIds(Long[] ids);
```

然后，在`AlbumMapper.xml`中配置以上抽象方法映射的SQL语句：

```xml
<!-- int deleteByIds(Long ids); -->
<delete id="deleteByIds">
    DELETE FROM pms_album WHERE id IN (
    	<foreach collection="array" item="id" separator=",">
    		#{id}
    	</foreach>
    )
</delete>
```

最后，在`AlbumMapperTests`中编写并执行测试：

```java
@Test
void deleteByIds() {
    Long[] ids = {1L, 3L, 5L};
    int rows = mapper.deleteByIds(ids);
    log.debug("批量删除完成，受影响的行数：{}", rows);
}
```

# 16. 修改相册数据

首先，应该在`AlbumMapper`接口中添加新的抽象方法：

```java
int update(Album album);
```

然后，在`AlbumMapper.xml`中配置以上抽象方法映射的SQL语句：

```xml
<!-- int update(Album album); -->
<update id="update">
    UPDATE pms_album
    <set>
    	<if test="name != null">
    		name=#{name},
        </if>
    	<if test="description != null">
            description=#{description},
    	</if>
    	<if test="sort != null">
            sort=#{sort},
    	</if>
    </set>
    WHERE id=#{id}
</update>
```

最后，在`AlbumMapperTests`中编写并执行测试：

```java
@Test
void update() {
    Album album = new Album();
    album.setName("新-测试相册010");
    album.setDescription("新-测试相册简介010");
    album.setSort(166);
    
    int rows = mapper.update(album);
    log.debug("更新完成，受影响的行数：{}", rows);
}
```

# 17. 统计相册数据的数量

首先，应该在`AlbumMapper`接口中添加新的抽象方法：

```java
int count();
```

在设计“查询”的抽象方法时，关于返回值类型，只需要保证所设计的返回值类型足够“装得下”所需的查询结果即可。

然后，在`AlbumMapper.xml`中配置以上抽象方法映射的SQL语句：

```xml
<!-- int count(); -->
<select id="count" resultType="int">
    SELECT count(*) FROM pms_album
</select>
```

**注意：每个`<select>`标签必须配置`resultType`或`resultMap`这2个属性中的其中1个。**

最后，在`AlbumMapperTests`中编写并执行测试：

```java
@Test
void count() {
    int count = mapper.count();
    log.debug("统计完成，表中的数据的数量：{}", count);
}
```

# 







