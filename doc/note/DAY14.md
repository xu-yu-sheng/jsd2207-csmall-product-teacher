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

  - 此方式创建出来的对象，在Spring容器中的名称就是方法名称
  - 此方法应该是`public`
  - 此方法的返回值类型，是你期望Spring框架管理的数据的类型
  - 此方法的参数列表，应该为空
  - 此方法的方法体，应该是自行设计的，没有要求

- 配置组件扫描，并在组件类上添加组件注解

  - 此方式创建出来的对象，在Spring容器中的名称默认是将类名首字母改为小写

    - 例如：类名是`AdminController`，则对象在Spring容器中的名称为`adminController`
    - 此规则仅适用于类名的第1个字母大写，且第2个字母小写的情况，如果不符合此规则，则对象在Spring容器中的名称就是类名
    - 可以通过组件注解的参数来指定名称

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

## 92.5. 自动装配

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

或者：

```java
@RestController
public class AlbumController {

    private IAlbumService albumService;
    
    @Autowired
    //                          ↓↓↓↓↓↓↓  自动装配  ↓↓↓↓↓↓↓
    public void setAlbumService(IAlbumService albumService) {
        this.albumService = albumService;
    }
    
}
```

另外，在配置类中的`@Bean`方法也可以在需要的时候自行添加参数，如果Spring容器中有合适的值，Spring也会从容器中找到值来调用方法。

关于“合适的值”，Spring对于`@Autowired`的处理机制是：查找在Spring容器中匹配类型的对象的数量：

- 1个：直接装配，且装配成功
- 0个：取决于`@Autowired`注解的`required`属性
  - `true`（默认值）：装配失败，在加载时即报错
  - `false`：放弃装配，则此量的值为`null`，在接下来的使用过程中可能导致NPE（`NullPointerException`）
- 超过1个：取决于是否存在某个Spring Bean（Spring容器中的对象）的名称与当前量的名称匹配
  - 存在：成功装配
  - 不存在：装配失败，在加载时即报错

关于通过名称匹配：

- 默认情况下，要求量（全局变量、方法参数等）的名称与对象在Spring容器中名称完全相同，视为匹配
- 可以在量（全局变量、方法参数等）的声明之前添加`@Qualifier`注解，通过此注解参数来指定名称，以匹配某个Spring容器的对象 
  - `@Qualifier`注解是用于配合自动装配机制的，单独使用没有意义

其实，还可以使用`@Resource`注解实现自动装配，但不推荐！

Spring框架对`@Resource`注解的自动装配机制是：先根据名称再根据类型来实现自动装配。

`@Resource`是`javax`包中的注解，根据此注解的声明，此注解只能添加在类上、属性上、方法上，不可以添加在构造方法上、方法的参数上。

## 92.6. 关于IoC与DI

**IoC**：**I**nversion **o**f **C**ontrol，控制反转，即将对象的创建、管理的权力（控制能力）交给框架

**DI**：**D**ependency **I**njection，依赖注入，即为依赖项注入值

Spring框架通过 DI 实现/完善 了IoC。

## 92.7. 关于Spring AOP

下周再讲

# 93. Spring MVC框架

## 93.1. Spring MVC框架的作用

Spring MVC框架主要解决了接收请求、响应结果的相关问题。

## 93.2. Spring MVC框架的依赖项

当项目中需要使用Spring MVC框架时，需要添加的依赖项是：`spring-webmvc`

## 93.3. 配置请求路径

通过`@RequestMapping`系列注解可以配置请求路径

## 93.4. 限制请求方式

通过`@RequestMapping`注解的`method`属性限制请求方式，例如：

```java
@RequestMapping(value = "/login", method = RequestMethod.POST)
```

或者，直接使用衍生的注解，例如`@GetMapping`、`@PostMapping`。

## 93.5. 接收请求参数

可以在处理请求的方法的参数列表中自由设计请求参数，可以：

- 将各请求参数逐一列举出来，表现为方法的多个参数，例如：

  ```java
  public JsonResult<Void> login(String username, String password) { ... }
  ```

- 将各请求参数封装到自定义的类型中，使用自定义的类型作为方法的参数

关于请求参数的注解：

- `@RequestParam`：此注解可以用于：修改请求参数的名称（没有太多实用价值），强制要求必须提交此参数（可以通过Validation框架的`@NotNull`实现同样效果），设置请求参数的默认值（适用于允许客户端不提交此请求参数时）
- `@PathVariable`：当URL中设计了占位符参数时，必须在对应的方法参数上添加此注解，以表示此参数的值来自URL中的占位符位置的值，如果占位符中的名称与方法参数名称不匹配，可以通过此注解的参数来配置
- `@RequestBody`：当方法的参数添加了此注解时，客户端提交的请求参数必须是对象格式的，当方法的参数没有添加此注解时，客户端提交的请求参数必须是FormData格式的
















