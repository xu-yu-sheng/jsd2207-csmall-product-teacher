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







