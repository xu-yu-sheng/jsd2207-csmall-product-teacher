# 43. 关于服务器端响应的结果（续）

为了更加规范管理响应结果中的“状态码”，在根包下创建`web.ServiceCode`枚举类型，此类型用于穷举可能使用到的业务状态码，并为每个业务状态码设置一个对应的数值结果：

```java
package cn.tedu.csmall.product.web;

/**
 * 业务状态码
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public enum ServiceCode {

    /**
     * 成功
     */
    OK(20000),
    /**
     * 错误：数据不存在
     */
    ERR_NOT_FOUND(40400),
    /**
     * 错误：数据冲突
     */
    ERR_CONFLICT(40900);

    private Integer value;

    private ServiceCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
```

然后，在`JsonResult`类中，添加自定义的静态方法，用于快速创建类的对象，并且，通过静态方式的名称（例如`ok`、`fail`等）更好的表现语义，必要的情况下，还可以对这些方法重载，使得方法的调用更加灵活：

```java
package cn.tedu.csmall.product.web;

import cn.tedu.csmall.product.ex.ServiceException;
import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {

    /**
     * 状态码
     */
    private Integer state;
    /**
     * 操作失败时的描述文本
     */
    private String message;

    public static JsonResult ok() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.state = ServiceCode.OK.getValue();
        return jsonResult;
    }

    public static JsonResult fail(ServiceException e) {
        return fail(e.getServiceCode(), e.getMessage());
    }

    public static JsonResult fail(ServiceCode serviceCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.state = serviceCode.getValue();
        jsonResult.message = message;
        return jsonResult;
    }

}
```

并且，需要调整`ServiceException`类型的构造方法，应该在抛出异常时，除了封装异常的描述文本（`message`），还应该定义此异常对应的业务状态码：

```java
public class ServiceException extends RuntimeException {

    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }
}
```

并且，在业务层中，抛出异常时，要封装以上数据（业务状态码和描述文本），例如：

```java
if (count > 0) {
    // 是：相册名称已经被占用，添加相册失败，抛出异常
    String message = "添加相册失败，相册名称已经被占用！";
    log.debug(message);
    throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
}
```

后续，在处理异常时，将返回`JsonResult`类型的对象，例如：

```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.debug("创建全局异常处理器对象：GlobalExceptionHandler");
    }

    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
        log.debug("处理请求的方法抛出了ServiceException，将统一处理");
        return JsonResult.fail(e);
    }

}
```

当然，处理请求的方法也使用`JsonResult`作为方法的返回值类型，例如：

```java
@ApiOperation("添加相册")
@ApiOperationSupport(order = 100)
@PostMapping("/add-new")
public JsonResult addNew(AlbumAddNewDTO albumAddNewDTO) {
    log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewDTO);
    albumService.addNew(albumAddNewDTO);
    log.debug("添加相册成功！");
    return JsonResult.ok();
}
```

# 44. 关于检查请求参数

所有请求参数都应该对数据的基本格式进行检查，例如相册的名称至少由4个字符组成等等。

此类检查可以在客户端直接进行，但是，对于服务器端而言，客户端的检查结果应该视为“不信任的”！因为：

- 可能某些请求是绕过了客户端的检查的
- 客户端软件可能存在被篡改
- 客户端软件可能不是最新版本，检查数据的规则与服务器端期望的并不一致
- 其它原因

所以，**服务器端必须在接收到请求参数的第一时间就对请求参数的基本格式进行检查！**

注意：既然服务器端对所有请求参数都做了检查，客户端仍需要检查请求参数的基本格式！因为：

- 以上分析的“不信任”客户端检查结果的原因，大多是小概率事件，如果客户端能执行检查，将可以“过滤”掉绝大部分请求参数格式错误的请求，以减轻服务器端的压力，提升用户体验（因为检查结果能迅速体现出来）

# 45. 使用Validation框架检查请求参数的基本格式

## 45.1. 添加依赖

在`pom.xml`中添加`spring-boot-starter-validation`依赖项：

```xml
<!-- Spring Boot Validation的依赖项，用于检查请求参数的基本格式 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

## 45.2. 检查封装的请求参数

在控制器中，对于封装类型的请求参数，应该先在请求参数之前添加`@Valid`或`@Validated`注解，表示将需要对此请求参数的格式进行检查，例如：

```java
@ApiOperation("添加相册")
@ApiOperationSupport(order = 100)
@PostMapping("/add-new")
//                       ↓↓↓↓↓↓ 以下是新添加的注解
public JsonResult addNew(@Valid AlbumAddNewDTO albumAddNewDTO) {
    log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewDTO);
    albumService.addNew(albumAddNewDTO);
    log.debug("添加相册成功！");
    return JsonResult.ok();
}
```

然后，在此封装类型中，在需要检查的属性上，添加检查注解，例如可以添加`@NotNull`注解，此注解表示“不允许为`null`值”，例如：

```java
@Data
public class AlbumAddNewDTO implements Serializable {

    /**
     * 相册名称
     */
    @ApiModelProperty(value = "相册名称", required = true)
    @NotNull // 新添加的注解
    private String name;
    
	// 暂不关心其它代码   
}
```

重启项目，如果客户端提交请求时，未提交`name`请求参数，就会响应`400`错误，并且，在服务器端的控制台会提示错误：

```
2022-11-01 11:27:45.398  WARN 15104 --- [nio-9080-exec-3] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult: 1 errors<EOL>Field error in object 'albumAddNewDTO' on field 'name': rejected value [null]; codes [NotNull.albumAddNewDTO.name,NotNull.name,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [albumAddNewDTO.name,name]; arguments []; default message [name]]; default message [不能为null]]
```

## 45.3. 处理检查不通过时的异常

由于检查未通过时会抛出`org.springframework.validation.BindException`异常，则可以在全局异常处理器中，添加对此异常的处理，以避免响应`400`错误到客户端，而是改为响应一段JSON数据：

```java
@ExceptionHandler
public JsonResult handleBindException(BindException e) {
    log.debug("开始处理BindException");

    StringJoiner stringJoiner = new StringJoiner("，", "请求参数格式错误，", "！！！");
    List<FieldError> fieldErrors = e.getFieldErrors();
    for (FieldError fieldError : fieldErrors) {
        String defaultMessage = fieldError.getDefaultMessage();
        stringJoiner.add(defaultMessage);
    }

    return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, stringJoiner.toString());
}
```

当请求参数可能出现多种错误时，也可以选择“快速失败”的机制，它会使得框架只要发现错误，就停止检查其它规则，这需要在配置类中进行配置，则在项目的根包下创建`config.ValidationConfiguration`类并配置：

```java
package cn.tedu.csmall.product.config;

import cn.tedu.csmall.product.controller.AlbumController;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;

/**
 * Validation配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
public class ValidationConfiguration {

    public ValidationConfiguration() {
        log.debug("创建配置类对象：ValidationConfiguration");
    }

    @Bean
    public javax.validation.Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure() // 开始配置
                .failFast(true) // 快速失败，即检查请求参数时，一旦发现某个参数不符合规则，则视为失败，并停止检查（剩余未检查的部分将不会被检查）
                .buildValidatorFactory()
                .getValidator();
    }

}
```

使用这种做法，当客户端提交的请求参数格式错误时，最多只会发现1种错误，则处理异常的代码可以调整为：

```java
@ExceptionHandler
public JsonResult handleBindException(BindException e) {
    log.debug("开始处理BindException");
    String defaultMessage = e.getFieldError().getDefaultMessage();
    return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, defaultMessage);
}
```

## 45.4. 检查未封装的请求参数

当处理请求的方法的参数是未封装的（例如`Long id`等），检查时，需要：

- 在当前控制器类上添加`@Validated`注解
- 在需要检查的请求参数上添加检查注解

例如：

```java
@Slf4j
@Validated // 新添加的注解
@RestController
@RequestMapping("/albums")
@Api(tags = "04. 相册管理模块")
public class AlbumController {

    // 暂不关心其它代码
  
    // http://localhost:8080/albums/9527/delete
    @ApiOperation("根据id删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "相册id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/delete")
    //        新添加的注解 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    public String delete(@Range(min = 1, message = "删除相册失败，尝试删除的相册的ID无效！")
                             @PathVariable Long id) {
        String message = "尝试删除id值为【" + id + "】的相册";
        log.debug(message);
        return message;
    }

}
```

当请求参数不符合以上`@Range(min =1)`的规则时（例如请求参数值为`0`或负数），默认情况下会出现`500`错误，在服务器端控制台可以看到以下异常信息：

```
javax.validation.ConstraintViolationException: delete.id: 删除相册失败，尝试删除的相册的ID无效！
```

则需要在全局异常处理器中，添加新的处理异常的方法，用于处理以上异常：

```java
@ExceptionHandler
public JsonResult handleConstraintViolationException(ConstraintViolationException e) {
    log.debug("开始处理ConstraintViolationException");
    StringJoiner stringJoiner = new StringJoiner("，");
    Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
    for (ConstraintViolation<?> constraintViolation : constraintViolations) {
        stringJoiner.add(constraintViolation.getMessage());
    }
    return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, stringJoiner.toString());
}
```

## 45.5. 关于检查注解

在`javax.validation.constraints`和`org.hibernate.validator.constraints`都有大量的检查注解，常用的检查注解有：

- `@NotNull`：不允许为`null`
- `@Range`：此注解有`min`和`max`属性，分别通过`@Min`和`@Max`实现，且`min`的默认值为`0`，`max`的默认值为`long`类型的最大值，此注解只能添加在整型的数值类型上，用于设置取值区间
- `@NotEmpty`：不允许为空字符串，即长度为0的字符串，此注解只能添加在字符串类型的参数上
- `@NotBlank`：不允许为空白，即不允许是仅由空格、TAB制表位、换行符等空白字符组成的值，此注解只能添加在字符串类型的参数上
- `@Pattern`：此注解有`regexp`属性，可通过此属性配置正则表达式，在检查时将根据正则表达式所配置的规则进行检查，此注解只能添加在字符串类型的参数上

**注意：除了`@NotNull`注解以外，其它注解均不检查请求参数为`null`的情况，例如在某个请求参数上配置了`@NotEmpty`，当提交的请求参数为`null`时将通过检查（视为正确），所以，当某个请求参数需要配置为不允许为`null`时，必须使用`@NotNull`，且以上不冲突的多个注解可以同时添加在同一个请求参数上！**

# 46. 根据id删除相册

此前已经完成Mapper层和Controller层的部分代码，此次主要实现Service层代码。

先在`IAlbumService`中添加抽象方法：

```java
void delete(Long id);
```

然后，在`AlbumServiceImpl`中实现以上方法：

```java
public void delete(Long id) {
    // 调用Mapper对象的getStandardById()执行查询
    // 判断查询结果是否为null
    // 是：无此id对应的数据，将不允许执行删除操作，则抛出异常
    
    // 调用Mapper对象的deleteById()方法执行删除
}
```

具体实现为：

```java
@Override
public void delete(Long id) {
    // 调用Mapper对象的getStandardById()执行查询
    AlbumStandardVO queryResult = albumMapper.getStandardById(id);
    // 判断查询结果是否为null
    if (queryResult == null) {
        // 是：无此id对应的数据，将不允许执行删除操作，则抛出异常
        String message = "删除相册失败，尝试访问的数据不存在！";
        throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
    }

    // 调用Mapper对象的deleteById()方法执行删除
    albumMapper.deleteById(id);
}
```

完成后，在`AlbumServiceTests`中编写并执行测试：

```java
@Test
void delete() {
    Long id = 1L;

    try {
        service.delete(id);
        log.debug("测试删除数据成功！");
    } catch (ServiceException e) {
        log.debug(e.getMessage());
    }
}
```











