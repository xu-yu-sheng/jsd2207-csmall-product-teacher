# Spring MVC框架的统一处理异常的机制

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











