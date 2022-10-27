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



```java
@RequestMapping(value = {"a"})
@RequestMapping(value = "a")
@RequestMapping({"a"})
@RequestMapping("a")
```









