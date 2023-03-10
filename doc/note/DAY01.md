# 1. 创建项目

打开IntelliJ IDEA，创建新的项目，在Spring Initializr的创建向导中：

- Server URL：https://start.spring.io 或 https://start.springboot.io（不要使用阿里云）
- Name：`jsd2207-csmall-product-teacher`（同学们的项目名称不要添加`-teacher`）
- Group：`cn.tedu`
- Artifact：`csmall-product`
- Package：`cn.tedu.csmall.product`
- Java：`8`

# 2. 创建数据库

创建`mall_pms`数据库：

```
CREATE DATABASE mall_pms;
```

并在IntelliJ IDEA中配置Database面板，可参考教程：http://doc.canglaoshi.org/doc/idea_database/index.html

# 3. 创建数据表

使用老师共享的`mall_pms_jsd2207.sql`（在老师的项目中的`/doc/sql`下），通过Database面板的Console执行SQL语句，以创建数据表。

# 4. 添加数据库编程的依赖

在`pom.xml`中添加依赖项：

```xml
<!-- Mybatis整合Spring Boot的依赖项 -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.2</version>
</dependency>
<!-- MySQL的依赖项 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

当添加以上依赖项后，如果执行测试中的`contextLoads()`（或其它任何测试方法，或启动项目），会出现错误，测试不通过时的异常信息如下：

```
Caused by: org.springframework.boot.autoconfigure.jdbc.DataSourceProperties$DataSourceBeanCreationException: Failed to determine a suitable driver class
```

之所以会出现此错误，是因为在Spring Boot项目中，当加载Spring环境时，如果项目中添加了数据库编程的依赖项（例如以上添加的`mybatis-spring-boot-starter`），则Spring Boot会自动读取连接数据库的配置信息，如果没有，就会报错！

则在`src/main/resources`下的`application.properties`文件中配置连接数据库的配置信息，此文件是Spring Boot项目默认的主配置文件，当加载Spring环境时，会自动读取此配置文件中的配置信息！

添加配置如下：

```properties
# 连接数据库的配置参数
spring.datasource.url=jdbc:mysql://localhost:3306/mall_pms?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=root
```

当添加以上配置后，再次启动项目，不会再报错，但是，即使以上配置值有误，也不会出错（除非URL格式有误），因为Spring Boot在加载Spring环境时只会读取以上配置，并不会真实的连接数据库！

为了检查以上配置值是否正确，可以在现有的测试类中添加以下代码，以真实的连接数据库，则如果以上配置值有误，就会出现错误，如果配置值正确，则可以顺利通过测试：

```java
@Autowired
DataSource dataSource;

@Test
void getConnection() throws Throwable {
    dataSource.getConnection();
}
```

# 5. 使用Mybatis实现数据库编程

在一个项目中，除了关联表，绝大部分的数据表都至少需要实现以下功能：

- 插入1条数据
- 批量插入数据
- 根据id删除某1条数据
- 根据若干个id批量删除某些数据
- 根据id修改数据
- 统计当前表中数据的数量
- 根据id查询数据详情
- 查询当前表中的数据列表

以“相册”（`pms_album`）为例，先实现“插入1条数据”的数据访问功能，需要执行的SQL语句大致是：

```mysql
INSERT INTO pms_album (除了id和2个时间以外的字段列表) VALUES (匹配的值列表);
```

要实现以上功能，需要：

- 在配置类中使用`@MapperScan`指定接口文件所在的根包
  - 一次性配置
- 在配置文件中通过`mybatis.mapper-locations`属性来指定XML文件所在的位置
  - 一次性配置
- 创建实体类
- 创建接口，并声明抽象方法
- 添加XML文件，并在此文件中配置抽象方法映射的SQL语句
- 创建测试类，编写并执行测试方法
  - 非必要，但强烈推荐

则在项目的根包下创建`config.MybatisConfiguration`配置类（添加了`@Configuration`注解的类），在此类上配置`@MapperScan`注解：

```java
package cn.tedu.csmall.product.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("cn.tedu.csmall.product.mapper")
@Configuration
public class MybatisConfiguration {
}
```

在`application.properties`中添加配置：

```properties
# Mybatis相关配置
mybatis.mapper-locations=classpath:mapper/*.xml
```

并且，在`src/main/resources`下创建名为`mapper`的文件夹。

在编写POJO类之前，先在项目中添加依赖：

```xml
<!-- Lombok的依赖项，主要用于简化POJO类的编写 -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
    <scope>provided</scope>
</dependency>
```

在任何POJO类上，可以添加`@Data`注解，则此框架（Lombok）会自动在编译期生成：

- 各属性对应的Setters & Getters
- 基于各属性的`hashCode()`与`equals()`
- 基于各属性的`toString()`

另外，所有POJO类都应该实现`Serializable`接口！

在项目的根包下创建`pojo.entity.Album`类，在类中声明与数据表对应的各属性：

```java
package cn.tedu.csmall.product.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 相册
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class Album implements Serializable {

    /**
     * 记录id
     */
    private Long id;

    /**
     * 相册名称
     */
    private String name;

    /**
     * 相册简介
     */
    private String description;

    /**
     * 自定义排序序号
     */
    private Integer sort;

    /**
     * 数据创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 数据最后修改时间
     */
    private LocalDateTime gmtModified;

}
```

在项目的根包下创建`mapper.AlbumMapper`接口，并在接口中添加“插入1条数据”的抽象方法：

```java
package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;

/**
 * 处理相册数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface AlbumMapper {

    /**
     * 插入相册数据
     *
     * @param album 相册数据
     * @return 受影响的行数
     */
    int insert(Album album);

}
```

关于抽象方法的声明：

- 返回值类型：如果执行的SQL语句是增、删、改类型的，返回值类型始终使用`int`，表示“受影响的行数”，也可以使用`void`，但不推荐
- 方法名称：自定义，但不推荐重载
  - 阿里巴巴的参考：
    - 获取单个对象的方法用 get 做前缀
    - 获取多个对象的方法用 list 做前缀
    - 获取统计值的方法用 count 做前缀
    - 插入的方法用 save/insert 做前缀
    - 删除的方法用 remove/delete 做前缀
    - 修改的方法用 update 做前缀
- 参数列表：根据需要执行的SQL语句中的参数来设计，如果参数较多，且具有相关性，则推荐封装

在`src/main/resource/mapper`下，通过粘贴得到`AlbumMapper.xml`文件，并在此文件中配置以上接口

抽象方法的对应代码：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="cn.tedu.csmall.product.mapper.AlbumMapper">

    <!-- int insert(Album album); -->
    <insert id="insert">
        INSERT INTO pms_album (
            name, description, sort
        ) VALUES (
            #{name}, #{description}, #{sort}
        )
    </insert>

</mapper>
```

最后，在`src/test/java`下的根包下，创建`mapper.AlbumMapperTests`类，在类上添加`@SpringBootTest`注解，并在类中编写测试方法：

```java
package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlbumMapperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void insert() {
        Album album = new Album();
        album.setName("测试相册002");
        album.setDescription("测试相册简介002");
        album.setSort(255);
        int rows = mapper.insert(album);
        System.out.println("插入相册数据完成，受影响的行数：" + rows);
    }

}
```









# 附录：MySQL数据类型与Java类中的数据类型的对应关系

| MySQL数据类型                   | Java类中的数据类型 |
| ------------------------------- | ------------------ |
| `tinyint` / `smallint` / `int`  | `Integer`          |
| `bigint`                        | `Long`             |
| `char` / `varchar` / `text`系列 | `String`           |
| `datetime`                      | `LocalDateTime`    |





