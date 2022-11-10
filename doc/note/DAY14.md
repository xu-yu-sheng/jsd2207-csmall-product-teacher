# 91. 单点登录（SSO）

**SSO**：**S**ingle **S**ign **O**n，单点登录，表现为客户端只需要在某1个服务器上通过认证，其它服务器也可以识别此客户端的身份！

单点登录的实现手段主要有2种：

- 使用Session机制，并共享Session
  - `spring-boot-starter-data-redis`结合`spring-session-data-redis`

- 使用Token机制
  - 各服务器需要有同样的解析JWT的代码

当前，`csmall-passport`中已经使用JWT，则可以在`csmall-product`项目中也添加Spring Security框架和解析JWT的代码，则`csmall-product`项目也可以识别用户的身份、检查权限。

需要做的事：

- 添加依赖
  - `spring-boot-starter-security`
  - `jjwt`
  - `fastjson`

- 复制`ServiceCode`覆盖`csmall-product`原本的文件
- 复制`GlobalExceptionHandler`覆盖`csmall-product`原本的文件
- 复制`application-dev.yml`中关于JWT的secretKey的配置
  - 关于JWT有效时长的配置，可以复制，但暂时用不上
- 复制`LoginPrincipal`到`csmall-product`中，与`csmall-passport`相同的位置
- 复制`JwtAuthorizationFilter`
- 复制`SecurityConfiguration`
  - 删除`PasswordEncoder`的`@Bean`方法
  - 删除`AuthenticationManager`的`@Bean`方法
  - 应该删除白名单中的 `/admins/login`

# 92. Spring框架

## 92.1. Spring框架的作用

Spring框架主要解决了创建对象、管理对象的问题。

## 92.2. Spring框架的依赖项

当项目中需要使用Spring框架时，需要添加的依赖项是：`spring-context`

## 92.3. Spring框架创建对象的做法

Spring框架创建对象有2种做法：

- 在任何配置类（添加了`@Configuration`）中，自定义方法，返回某种类型的（你需要的）对象，并在方法上添加`@Bean`注解

  - 此方法应该是`public`
  - 此方法的返回值类型，是你期望Spring框架管理的数据的类型
  - 此方法的参数列表，应该为空
  - 此方法的方法体，应该是自行设计的，没有要求

- 配置组件扫描，并在组件类上添加组件注解

  - 在任何配置类上，添加`@ComponentScan`，当加载此配置类时，就会激活组件扫描

  - 可以配置`@ComponentScan`的参数，此参数应该是需要被扫描的根包（会扫描所配置的包，及其所有子孙包），且此注解参数的值是数组类型的

    - 例如：

    - ```java
      @ComponentScan("cn.tedu")
      ```

  - 如果没有配置`@ComponentScan`的参数中的根包，则组件扫描的范围就是当前类的包及其子孙包

  - 需要在各组件类上添加组件注解，才会被创建对象，常见的组件注解有：

    - `@Component`：通用注解
    - `@Controller`：控制器类的注解
    - `@Service`：Service这种业务类的注解
    - `@Repository`：处理数据源中的数据读写的类的注解

## 92.4. Spring框架管理的对象的作用域

默认情况下，Spring框架管理的对象都是单例的！

单例：在任何时间点，某个类的对象最多只有1个！

可以在类上添加`@Scope("prototype")`使得被Spring管理的对象是“非单例的”。

> 提示：`@Scope`注解还可以配置为`@Scope("singleton")`，此`singleton`表示“单例的”，是默认的。

默认情况下，Spring框架管理的**单例的**对象是“预加载的”，相当于设计模式中的单例模式的“饿汉式单例模式”。

> 提示：可以在类上添加`@Lazy`注解，使得此对象是“懒加载的”，相当于“懒汉式单例模式”，只会在第1次需要获取对象时才把对象创建出来！

注意：Spring框架并不是使用了设计模式中的“单例模式”，只是从对象的管理方面，对象的作用域表现与单例模式的极为相似而已。





```java
Admin admin = new Admin();
admin.setUsername("root");
```

















