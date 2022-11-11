# 93. Spring MVC框架（续）

## 93.6. 响应结果

默认情况下，处理请求的方法的返回值将表示“处理响应结果的视图组件的名称，及相关的数据”，在Spring MVC中，有一种内置的返回值类型是`ModelAndView`，不是前后端分离的做法！

在处理请求的方法上，可以添加`@ResponseBody`注解，当添加此注解后，处理请求的方法的返回值将表示“响应的数据”，不再由服务器端决定视图组件，这种做法也叫做“响应正文”！这是前后端分离的做法！

`@ResponseBody`注解可以添加在处理请求的方法上，将作用于当前方法，也可以添加在控制器类上，将作用于控制器类中所有处理请求的方法！

控制器类需要添加`@Controller`注解，才是控制器类，或者，也可以改为添加`@RestController`，此注解是由`@Controller`和`@ResponseBody`组合而成的！所以，添加`@RestController`后，当前控制器类中所有处理请求的方法都是“响应正文”的！

当控制器处理请求需要响应正文时，Spring MVC框架会根据处理请求的方法的返回值类型，来决定使用某个`MessageConverter`（消息转换器），来将返回值转换为响应到客户端的数据，不同的返回值类型对应不同的消息转换器，例如，返回值类型是`String`时，Spring MVC框架将使用`StringHttpMessageConverter`，如果某个返回值类型是Spring MVC框架没有对应的消息转换器的，且当前项目添加了`jackson-databind`依赖项后，会自动使用此依赖项中的消息转换器，而`jackson-databind`中的消息转换器会将方法返回的结果转换为JSON格式的字符串！另外，如果当前项目是使用XML来配置Spring MVC框架的，还需要添加`<annotation-driven/>`标签以开启“注解驱动”，如果是使用注解进行配置的，则需要在配置类上添加`@EnableWebMvc`注解，如果是在Spring Boot中应用Spring MVC，不需要此配置！

## 93.7. 处理异常

添加了`@ExceptionHandler`注解的方法，就是处理异常的方法。

处理异常的方法到底处理哪种异常，由`@ExceptionHandler`注解参数或方法的参数中的异常类型来决定！如果`@ExceptionHandler`注解没有配置参数，由方法的参数中的异常类型决定，如果`@ExceptionHandler`注解配置了参数，由以注解参数中配置的类型为准！

处理异常的方法可以声明在控制器类，将只作用于当前控制器类中的方法抛出的异常！

通常，建议将处理异常的方法声明在专门的类中，并在此类上添加`@ControllerAdvice`注解，当添加此注解后，此类中特定的方法（例如处理异常的方法）将作用于每次处理请求的过程中！如果处理异常后的将“响应正文”，也可以在处理异常的方法上添加`@ResponseBody`注解，或在当前类上添加`@ResponseBody`，或使用`@RestControllerAdvice`取代`@ControllerAdvice`和`@ResponseBody`。

## 93.8. Spring MVC框架的核心执行流程

![image-20221019114229471](images/image-20221019114229471.png)

强烈建议抽时间看扩展视频教程！

# 









