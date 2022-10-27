# 26. Spring MVC框架的统一处理异常的机制

Spring MVC框架提供了统一处理异常的机制，对于每种类型的异常，只需要编写1段相关的处理代码即可！

当使用这种机制时，在控制器类的各处理请求的方法中，将不再使用`try...catch`语句来包裹可能抛出异常的方法并处理，则控制器类中各处理请求的方法都是将是抛出异常的（虽然你不必在代码中显示的使用`throws`声明抛出），这些异常会被Spring MVC框架捕获并尝试调用统一处理异常的方法！

可以编写统一处理异常的方法，对控制器类中处理请求的方法抛出的异常进行处理，关于统一处理异常的方法：

- 注解：必须添加`@ExceptionHandler`注解
- 访问权限：应该使用`public`
- 返回值类型：参考处理请求的方法
- 方法名称：自定义
- 参数列表：必须有1个异常类型的参数，表示Spring MVC框架调用处理请求的方法时捕获到的异常，并且，可以按需添加`HttpServletRequest`、`HttpServletResponse`等少量特定类型的参数，不可以随意添加其它参数

如果将统一处理异常的方法定义在某个控制器类中，将只能作用于当前控制器类中各处理请求的方法！

通常，会将统一异常的代码定义在专门的类中，并在此类上添加`@RestControllerAdvice`注解，添加了此注解的类中的特定方法将作用于整个项目任何处理请求的方法的过程！

则在项目的根包下创建`ex.handler.GlobalExceptionHandler`类，在此类上添加`@RestControllerAdvice`注解，并在类中添加处理异常的方法：

```java
package cn.tedu.csmall.product.ex.handler;

import cn.tedu.csmall.product.ex.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.debug("创建全局异常处理器对象：GlobalExceptionHandler");
    }

    @ExceptionHandler
    public String handleServiceException(ServiceException e) {
        log.debug("处理请求的方法抛出了ServiceException，将统一处理");
        return e.getMessage();
    }

}
```

有了以上类，则当前项目中，任何处理请求的方法对于`ServiceException`都应该是**抛出**，且各控制器类中都不必关心如何处理`ServiceException`，会由以上方法进行处理！

# 27. 关于`@RequestMapping`

在Spring MVC框架中，可以在处理请求的方法上添加`@RequestMapping`注解，以配置**请求路径**与**处理请求的方法**的映射关系。

此注解还可以添加在控制器类上，作为当前类中每个请求路径的统一前缀！

在开发实践中，强烈建议在类上配置`@RequestMapping`！

当在类上和方法上都使用`@RequestMapping`配置了路径后，实践使用的路径应该是这2个路径值结合起来的路径值，而`@RequestMapping`在处理时，会自动处理两端必要的、多余的`/`符号。

例如：

| 类上的配置值 | 方法上的配置值 |
| ------------ | -------------- |
| /album       | /add-new       |
| /album       | add-new        |
| album        | /add-new       |
| album        | add-new        |
| album/       | /add-new       |
| album/       | add-new        |
| /album/      | /add-new       |
| /album/      | add-new        |

以上8种配置的组合是等效的！通常，建议在同一个项目中使用统一的风格，例如使用第1种，或使用第4种。

**注意：`@RequestMapping("/")`和`@RequestMapping("")`不是等效的！**

关于`@RequestMapping`注解的源代码，声明部分为：

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {
    // 暂不关心内部代码
}
```

以上源代码中，`@Target({ElementType.TYPE, ElementType.METHOD})`表示当前注解可以添加在哪些位置，`ElementType.TYPE`表示可以添加在“类型”上，`ElementType.METHOD`表示可以添加在“方法”上。

在`@RequestMapping`注解源代码的内部，还有：

```java
@AliasFor("path")
String[] value() default {};
```

以上代码中，`value()`表示此注解可以配置名为`value`的属性，`String[]`表示此`value`属性的值是`String[]`类型的，`default {}`表示此属性的默认值是`{}`，即空数组，则可以配置为：

```java
@RequestMapping(value = {"a", "b", "c"})
```

而`value`的意义需要通过学习来了解。

另外，`@AliasFor("path")`表示当前`value`属性**等效于**`path`属性。

在Java语言中，如果某个注解属性的值是某种数组类型，但是，需要配置的值只有1个（数组中只有1个元素），可以不必使用一对大括号将其框住！

例如，以下2种配置是完全等效的：

```java
@RequestMapping(value = {"a"})
```

```java
@RequestMapping(value = "a")
```

在Java语言中，`value`是各注解默认的属性名，如果注解只需要配置这1个属性，可以不必显式指定属性名！

例如，以下2种配置是完全等效的：

```java
@RequestMapping(value = "a")
```

```java
@RequestMapping("a")
```

另外，在`@RequestMapping`的源代码中，还包含：

```java
RequestMethod[] method() default {};
```

此属性的作用的是限制请求方式，在默认情况下，客户端提交请求时，所有请求方式都是允许的！如果配置此属性，则只有与配置值匹配的请求方式才是允许的，如果客户端提交请求的方式有误，则会导致`405`错误！

> 提示：目前，提交POST请求的方式有：使用HTML的`<form>`标签的`method="post"`、使用`axios`的`post()`函数，或其它通过JavaScript程序发出POST请求，除此以外，都是GET请求，例如：直接在浏览器的地址栏中输入URL并访问、点击页面上的超链接、打开浏览器收藏夹中的收藏地址等。

通常，建议：**以获取数据为主要目的的请求，应该限制为GET方式，除此以外，都应该限制为POST方式。**

Spring MVC框架还定义了已经限制了请求方式的、与`@RequestMapping`类似的注解，包括：

- `@GetMapping`
- `@PostMapping`
- `@PutMapping`
- `@DeleteMapping`
- `@PatchMapping`

以`@GetMapping`为例，其源代码片段：

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.GET)
public @interface GetMapping {

	/**
	 * Alias for {@link RequestMapping#name}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String name() default "";

	/**
	 * Alias for {@link RequestMapping#value}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] value() default {};

	/**
	 * Alias for {@link RequestMapping#path}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] path() default {};

	/**
	 * Alias for {@link RequestMapping#params}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] params() default {};

	/**
	 * Alias for {@link RequestMapping#headers}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] headers() default {};

	/**
	 * Alias for {@link RequestMapping#consumes}.
	 * @since 4.3.5
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] consumes() default {};

	/**
	 * Alias for {@link RequestMapping#produces}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] produces() default {};

}
```

# 28. 关于RESTful

RESTful是一种软件的设计风格（不是规定，也不是规范）。

> RESTFUL是一种网络应用程序的设计风格和开发方式，基于HTTP，可以使用XML格式定义或JSON格式定义。RESTFUL适用于移动互联网厂商作为业务接口的场景，实现第三方OTT调用移动网络资源的功能，动作类型为新增、变更、删除所调用资源。

RESTful的典型表现有：

- 一定是响应正文的
  - 服务器端处理完请求后将响应数据，不会由服务器响应页面到客户端
- 通常会将具有唯一性的请求参数设计到URL中，成为URL的一部分
  - `https://blog.csdn.net/gghbf45219/article/details/1045245854`
  - `https://blog.csdn.net/m4465sfd46/article/details/1042276671`
- 严格区分4种请求方式，**在许多业务系统中并不这样设计**
  - 尝试增加数据，使用`POST`请求方式
  - 尝试删除数据，使用`DELETE`请求方式
  - 尝试修改数据，使用`PUT`请求方式
  - 尝试查询数据，使用`GET`请求方式

Spring MVC框架很好的支持了RESTful风格的设计，当需要在URL中使用变量值时，可以使用`{自定义名称}`作为占位符，并且，在方法的参数列表中，自定义参数接收此变量值，在参数前还需要添加`@PathVariable`注解，例如：

```java
// http://localhost:8080/album/9527/delete
@RequestMapping("/{id}/delete")
public String delete(@PathVariable String id) {
    String message = "尝试删除id值为【" + id + "】的相册";
    log.debug(message);
    return message;
}
```

注意：通常会将占位符中的名称和方法的参数名称保持一致，例如以上的`{id}`和`String id`都使用`id`作为名字，如果因为某些原因无法保持一致，则需要配置`@PathVariable`注解的参数，此注解参数值与占位符中的名称一致即可，方法的参数名称就不重要了。

在开发实践中，可以将处理请求的方法的参数类型设计为期望的类型，例如将`id`设计为`Long`类型的，但是，如果这样设计，必须保证请求中的参数值是可以被正确转换为`Long`类型的，否则会出现`400`错误！

为了尽量保证匹配的准确性、保证参数值可以正常转换，在设计占位符时，可以在占位符名称右侧添加冒号，并在冒号右侧使用正则表达式来限制占位符的值的格式，例如：

```java

```













