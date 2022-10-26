# 20. 关于`<resultMap>`与`<sql>`标签

Mybatis框架在处理查询时，会自动的将**列名**与**属性名**完全一致的数据进行封装（例如查询结果集中列名为`name`的值会自动封装到返回值对象的`name`属性中），如果名称不一致，则不会自动封装！

通常，建议通过`<resultMap>`标签来配置列名与属性名的对应关系，以指导Mybatis如何处理结果集。

另外，还建议使用`<sql>`标签来封装查询的字段列表，并通过`<include>`标签来引用封装的查询字段列表，例如：

```xml
<!-- CategoryStandardVO getStandardById(Long id); -->
<select id="getStandardById" resultMap="StandardResultMap">
    SELECT
        <include refid="StandardQueryFields"/>
    FROM
        pms_category
    WHERE
        id=#{id}
</select>

<sql id="StandardQueryFields">
    <if test="true">
        id, name, parent_id, depth, keywords, sort, icon, enable, is_parent, is_display
    </if>
</sql>

<resultMap id="StandardResultMap" type="cn.tedu.csmall.product.pojo.vo.CategoryStandardVO">
    <id column="id" property="id"/>
    <result column="name" property="name"/>
    <result column="parent_id" property="parentId"/>
    <result column="depth" property="depth"/>
    <result column="keywords" property="keywords"/>
    <result column="sort" property="sort"/>
    <result column="icon" property="icon"/>
    <result column="enable" property="enable"/>
    <result column="is_parent" property="isParent"/>
    <result column="is_display" property="isDisplay"/>
</resultMap>
```

注意：配置`<select>`标签时，如果使用`resultMap`属性，则此属性的值必须是`<resultMap>`的`id`值！如果使用`resultType`属性，则此属性的值必须是返回结果类型的全限定名！

如果在`<select>`配置的`resultMap`的值有误，则会出现如下错误（错误示例）：

```
java.lang.IllegalArgumentException: Result Maps collection does not contain value for cn.tedu.csmall.product.pojo.vo.CategoryStandardVO
```

如果在`<select>`配置的`resultType`的值有误，则会出现如下错误（错误示例）：

```
Caused by: org.apache.ibatis.builder.BuilderException: Error parsing Mapper XML. The XML location is 'file [D:\IdeaProjects\jsd2207-csmall-product-teacher\target\classes\mapper\CategoryMapper.xml]'. Cause: org.apache.ibatis.builder.BuilderException: Error resolving class. Cause: org.apache.ibatis.type.TypeException: Could not resolve type alias 'StandardResultMap'.  Cause: java.lang.ClassNotFoundException: Cannot find class: StandardResultMap

Caused by: org.apache.ibatis.builder.BuilderException: Error resolving class. Cause: org.apache.ibatis.type.TypeException: Could not resolve type alias 'StandardResultMap'.  Cause: java.lang.ClassNotFoundException: Cannot find class: StandardResultMap

Caused by: org.apache.ibatis.type.TypeException: Could not resolve type alias 'StandardResultMap'.  Cause: java.lang.ClassNotFoundException: Cannot find class: StandardResultMap

Caused by: java.lang.ClassNotFoundException: Cannot find class: StandardResultMap
```

如果同一个XML文件中有2个`<resultMap>`的`id`完全相同，则会出现以下错误（错误示例），或者，如果存在多个XML文件，但`<mapper>`标签的`namespace`值相同，且存在相同`id`的`<resultMap>`，也会出现此错误：

```
Caused by: org.apache.ibatis.builder.BuilderException: Error parsing Mapper XML. The XML location is 'file [D:\IdeaProjects\jsd2207-csmall-product-teacher\target\classes\mapper\CategoryMapper.xml]'. Cause: java.lang.IllegalArgumentException: Result Maps collection already contains value for cn.tedu.csmall.product.mapper.CategoryMapper.StandardResultMap

Caused by: java.lang.IllegalArgumentException: Result Maps collection already contains value for cn.tedu.csmall.product.mapper.CategoryMapper.StandardResultMap
```

# 21. 关于Service

在项目中，应该使用Service组件来处理**业务**相关的代码，以此来设计业务流程、业务逻辑，以保证数据的完整性、有效性。

通常，Service应该由接口和实现类来组成，这2种代码都是由开发者自行编写的。

# 22. 实现“添加相册”的业务

在设计业务时，需要考虑业务规则（不考虑数据格式的相关问题），以“添加相册”为例，可以制定规则：

- 相册名称必须是唯一的

则在项目的根包下创建`service.IAlbumService`接口

```java
public interface IAlbumService {
}
```

然后，在项目的根包下创建`service.impl.AlbumServiceImpl`类，实现以上接口，并在类上添加`@Service`注解：

```java
@Slf4j
@Service
public class AlbumServiceImpl implements IAlbumService {

    public AlbumServiceImpl() {
        log.debug("创建业务对象：AlbumServiceImpl");
    }

}
```

接下来，在项目的根包下创建`pojo.dto.AlbumAddNewDTO`类，用于封装客户端将提交的请求参数：

```java
package cn.tedu.csmall.product.pojo.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 添加相册的DTO类
 * 
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class AlbumAddNewDTO implements Serializable {

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
    
}
```

并在`IAlbumService`中设计“添加相册”的抽象方法：

```java
void addNew(AlbumAddNewDTO albumAddNewDTO);
```

关于Service的抽象方法的声明原则：

- 返回值类型：仅以操作成功为前提来设计返回值类型
  - 操作失败将通过抛出异常来表示
- 方法名称：自定义的、规范的，无其它约束
- 参数列表：根据客户端将提交的请求参数来设计，如果参数数量较多，且具有相关性，则应该封装

关于此业务方法的具体实现，大致步骤为：

```
// 从参数对象中获取相册名称
// 检查相册名称是否已经被占用（相册表中是否已经存在此名称的数据）
// 是：相册名称已经被占用，添加相册失败，抛出异常
// 否：相册名称没有被占用，则向相册表中插入数据
```

在以上步骤中，需要“检查相册名称是否已经被占用”，可以通过以下SQL查询来实现：

```mysql
select * from pms_album where name=?
```

```mysql
select count(*) from pms_album where name=?
```

如果采取以上的第2种做法，则需要在`AlbumMapper.java`接口中添加抽象方法：

```java
int countByName(String name);
```

并在`AlbumMapper.xml`中配置以上抽象方法映射的SQL语句：

```xml
<!-- int countByName(String name); -->
<select id="countByName" resultType="int">
    SELECT count(*) FROM pms_album WHERE name=#{name}
</select>
```

完成后，还需要在`AlbumMapperTests.java`中编写并执行测试：

```java
@Test
void countByName() {
    String name = "测试数据";
    int count = mapper.countByName(name);
    log.debug("根据名称【{}】统计数据的数量，结果：{}", name, count);
}
```

至此，在`AlbumMapper`中已实现了“根据相册名称统计数据的数量”功能，在Service中，可通过调用此功能来检查“相册名称是否已经被占用”。

接下来，在`AlbumServiceImpl`类中自动装配`AlbumMapper`对象：

```java
@Autowired
private AlbumMapper albumMapper;
```

并实现接口中的抽象方法：

```java
@Override
public void addNew(AlbumAddNewDTO albumAddNewDTO) {
    log.debug("开始处理【添加相册】的业务，参数：{}", albumAddNewDTO);
    // 从参数对象中获取相册名称
    String albumName = albumAddNewDTO.getName();
    // 检查相册名称是否已经被占用（相册表中是否已经存在此名称的数据）
    log.debug("检查相册名称是否已经被占用");
    int count = albumMapper.countByName(albumName);
    if (count > 0) {
        // 是：相册名称已经被占用，添加相册失败，抛出异常
        log.debug("相册名称已经被占用，添加相册失败，将抛出异常");
        throw new RuntimeException();
    }

    // 否：相册名称没有被占用，则向相册表中插入数据
    log.debug("相册名称没有被占用，将向相册表中插入数据");
    Album album = new Album();
    BeanUtils.copyProperties(albumAddNewDTO, album);
    log.debug("即将插入相册数据：{}", album);
    albumMapper.insert(album);
    log.debug("插入相册数据完成");
}
```

完成后，在`src/test/java`下的根包下，创建`service.AlbumServiceTests`测试类，在此类中自动装配`IAlbumService`对象，并编写、执行测试：

```java
package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AlbumServiceTests {

    @Autowired
    IAlbumService service;

    @Test
    void addNew() {
        AlbumAddNewDTO albumAddNewDTO = new AlbumAddNewDTO();
        albumAddNewDTO.setName("测试数据1");
        albumAddNewDTO.setDescription("测试数据的简介1");
        albumAddNewDTO.setSort(100);

        try {
            service.addNew(albumAddNewDTO);
            log.debug("测试添加数据成功！");
        } catch (RuntimeException e) {
            log.debug("测试添加数据失败！");
        }
    }

}
```

# 23. 处理“添加相册”的请求

在服务器端项目中，需要使用“控制器（Controller）”来接收来自客户端（例如网页、手机APP等）的请求，并响应结果到客户端。

当需要开发控制器相关代码时，需要项目中添加`spring-boot-starter-web`依赖项。

> 提示：`spring-boot-starter-web`包含了`spring-boot-starter`，所以，并不需要添加新的依赖，只需要将原有的`spring-boot-starter`改成`spring-boot-starter-web`即可。

当添加了`spring-boot-starter-web`依赖项之后，在项目的根包下创建`controller.AlbumController`类，在此类中编写接收请求、响应结果的方法：

```java
package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.csmall.product.service.IAlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理相册相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    public AlbumController() {
        log.debug("创建控制器对象：AlbumController");
    }

    // http://localhost:8080/add-new?name=相册001&description=相册001的简介&sort=199
    @RequestMapping("/add-new")
    public String addNew(AlbumAddNewDTO albumAddNewDTO) {
        log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewDTO);
        try {
            albumService.addNew(albumAddNewDTO);
            log.debug("添加数据成功！");
            return "添加相册成功！";
        } catch (RuntimeException e) {
            log.debug("添加数据失败！");
            return "添加相册失败！";
        }
    }

}
```

完成后，重启项目，打开浏览器，通过 `http://localhost:8080/add-new?name=相册001&description=相册001的简介&sort=199 可以测试访问`。

# 24. 关于自定义异常

在Service中处理业务逻辑时，当视为“操作失败”时，应该抛出异常，且，抛出的异常应该是自定义的异常，以避免与原有的其它异常在同一个业务中出现而导致无法区分失败原因的问题！

通常，自定义异常应该继承自`RuntimeException`，其原因主要有：

- Xxxxx
- Xxxxx

则在项目的根包下创建`ex.ServiceException`类，继承自`RuntimeException`：

```java
/**
 * 业务异常类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public class ServiceException extends RuntimeException {
}
```

在开发实践中，同一个业务可能存在多种“失败”的可能，以“登录”为例，导致“失败”的原因可能有：

- 用户名不存在
- 密码错误
- 账号已经被封号
- 其它

为了区分这些不同的“失败”，了解失败的原因，可以：

- 为每一种“失败”都创建一种异常类
- 使用同一个异常类，对不同的“失败”使用携带了不同信息的对象

如果采取以上第2种方案，可以在自定义异常类中添加带`String message`参数的构造方法，并在此构造方法中调用父类的同参数的构造方法：

```java
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

}
```

则抛出异常时，必须封装异常信息的描述文本，例如，在`AlbumServiceImpl`中抛出异常时的代码需要调整为：

```java
if (count > 0) {
    // 是：相册名称已经被占用，添加相册失败，抛出异常
    String message = "添加相册失败，相册名称已经被占用！";
    log.debug(message);
    throw new ServiceException(message);
}
```

后续，在`AlbumController`中，调用Service方法时，当捕获到`ServiceException`后可以调用异常对象的`getMessage()`方法得到抛出时封装的异常信息。













```java
User login(String username, String password) throws 用户名不存在的异常, 密码错误的异常, 账号被封号的异常;
```



```java
try {
    User user = service.login("xx", "xx");
    log.debug("登录成功，用户：{}", user);
} catch (用户名不存在的异常 e) {
    log.debug("登录失败，用户名不存在");
} catch (密码错误的异常 e) {
    log.debug("登录失败，密码错误");
} catch (账号被封号的异常 e) {
    log.debug("登录失败，账号被封号");
}
```

# 25. 关于处理异常

在服务器端项目中，如果某个抛出的异常始终没有被处理，则默认会向客户端响应`500`错误（HTTP状态码为`500`）。

在服务器端项目中，必须对异常进行处理，因为，如果不处理，软件的使用者可能不清楚出现异常的原因（默认情况下，响应的`500`错误普通用户看不懂），也不知道如何调整请求参数来解决此问题，甚至可能反复尝试提交错误的请求（例如反复刷新页面），对于服务器端而言，也是无谓的浪费了一些性能。

所以，处理异常的根本在于：明确的向软件的使用者表现错误信息，并给予必要的提示，使得软件的使用者能明确的知道错误的原因，则软件的使用者可能会调整请求参数，从而后续的请求是可能成功的！



