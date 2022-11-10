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
      - `@RestController`：仅添加Spring MVC框架后可使用
      - `@ControllerAdvice`：仅添加Spring MVC框架后可使用
      - `@RestControllerAdvice`：仅添加Spring MVC框架后可使用
    - `@Service`：Service这种业务类的注解
    - `@Repository`：处理数据源中的数据读写的类的注解

  - 以上4种组件注解在Spring框架作用范围之内是完全等效的

  - 在Spring框架中，还有`@Configuration`注解，也是组件注解的一种，但是Spring对此注解的处理更加特殊（Spring框架对配置类使用了代理模式）

对于这2种创建对象的做法，通常：

- 如果是自定义的类，优先使用组件扫描的做法来创建对象
- 如果不是自定义的类，无法使用组件扫描的做法，只能在配置类中通过`@Bean`方法来创建对象

当Spring成功的创建了对象后，会将对象保存在Spring应用程序上下文（`ApplicationContext`）中，后续，当需要这些对象时，可以从Spring应用程序上下文中获取！

由于Spring应用程序上下文中持有大量对象的引用，所以，Spring应用程序上下文也通常被称之为“Spring容器”。

## 92.4. Spring框架管理的对象的作用域

默认情况下，Spring框架管理的对象都是单例的！

单例：在任何时间点，某个类的对象最多只有1个！

可以在类上添加`@Scope("prototype")`使得被Spring管理的对象是“非单例的”。

> 提示：`@Scope`注解还可以配置为`@Scope("singleton")`，此`singleton`表示“单例的”，是默认的。

默认情况下，Spring框架管理的**单例的**对象是“预加载的”，相当于设计模式中的单例模式的“饿汉式单例模式”。

> 提示：可以在类上添加`@Lazy`注解，使得此对象是“懒加载的”，相当于“懒汉式单例模式”，只会在第1次需要获取对象时才把对象创建出来！

注意：Spring框架并不是使用了设计模式中的“单例模式”，只是从对象的管理方面，对象的作用域表现与单例模式的极为相似而已。

## 95.2. 自动装配

自动装配：当某个量需要值时，Spring框架会自动的从容器中找到合适的值，为这个量赋值。

自动装配的典型表现是在属性上添加`@Autowired`注解，例如：

```java
@RestController
public class AlbumController {

    // ↓↓↓↓↓  自动装配的典型表现  ↓↓↓↓↓
    @Autowired
    private IAlbumService albumService;
    
}
```

或者：

```java
@RestController
public class AlbumController {

    private IAlbumService albumService;
    
    //                     ↓↓↓↓↓↓↓  自动装配  ↓↓↓↓↓↓↓
    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }
    
}
```

> 提示：Spring创建对象时需要调用构造方法，如果类中仅有1个构造方法（如上所示），Spring会自动调用，如果这唯一的构造方法是有参数的，Spring也会自动从容器中找到合适的对象来调用此构造方法，如果容器没有合适的对象，则无法创建！如果类中有多个构造方法，默认情况下，Spring会自动调用添加了`@Autowired`注解的构造方法，如果多个构造方法都没有添加此注解，则Spring会自动调用无参数的构造方法，如果也不存在无参数构造方法，则会报错！











```java
Admin admin = new Admin();
admin.setUsername("root");
```

















