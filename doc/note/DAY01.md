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











