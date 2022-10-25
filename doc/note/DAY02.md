# 6. 关于Lombok框架

Lombok框架的主要作用是通过注解可以**在编译期生成**某些代码，例如Setters & Getters、`hashCode()`与`equals()`、`toString()`方法等，可以简化开发。

由于源代码（`.java`）文件中并没有相关代码，所以，默认情况下，开发工具无法智能提示，直接写出相关代码也会提示错误，则需要在开发工具中安装Lombok插件，安装方式：打开IntelliJ IDEA的设置，在**Plugins**一栏，在`Marketplace`中找到`Lombok`并安装即可。

Lombok的常用注解有：

- `@Data`：添加在类上，可在编译期生成全部属性对应的Setters & Getters、`hashCode()`与`equals()`、`toString()`，使用此注解时，必须保证当前类的父类存在无参数构造方法
- `@Setter`：可以添加在属性上，将仅作用于当前属性，也可以添加在类上，将作用于类中所有属性，用于生成对应的Setter方法
- `@Getter`：同上，用于生成对应的Getter方法
- `@EqualsAndHashCode`：添加在类上，用于生成规范的`equals()`和`hashCode()`，关于`equals()`方法，如果2个对象的所有属性的值完全相同，则返回`true`，否则返回`false`，关于`hashCode()`也是如此，如果2个对象的所有属性的值完全相同，则生成的HashCode值相同，否则，不应该相同
- `@ToString`：添加在类上，用于生成全属性对应的`toString()`方法
- `@Slf4j`：添加在类上，？？？

# 10. 关于自动装配Mapper对象时报错

当自动装配Mapper接口的对象时，IntelliJ IDEA可能会报错，提示无法装配此对象，但是，并不影响运行！

如果要解决IntelliJ IDEA错误的提示，可以：

- 使用`@Resource`注解替换`@Autowired`注解
  - 不推荐，只要是使用自动装配，都应该使用`@Autowired`注解
- 不再使用`@MapperScan`来指定Mapper接口的包，而改为在各Mapper接口上添加`@Mapper`注解
  - 不推荐，使用`@MapperScan`是一劳永逸的做法，更加省事
- 在各Mapper接口上添加`@Repository`注解
  - 推荐
  - 与添加`@Mapper`注解的本质不同，添加`@Mapper`注解是为了标识此接口是Mybatis框架应该处理的接口，添加`@Repository`注解是为了引导IntelliJ IDEA作出正确的判断

# 11. 关于Slf4j日志

在Spring Boot项目中，基础依赖项（`spring-boot-starter`）中已经包含了日志的相关依赖项。

在添加了`Lombok`依赖项后，可以在类上添加`@Slf4j`注解，则Lombok框架会在编译期生成名为`log`的变量，可调用此变量的方法来输出日志。

日志是有显示级别的，根据日志内容的重要程度，从不重要到重要，依次为：

- `trace`：跟踪信息，可能包含不一定关注，但是包含了程序执行流程的信息
- `debug`：调试信息，可能包含一些敏感内容，比如关键数据的值
- `info`：一般信息
- `warn`：警告信息
- `error`：错误信息

使用Slf4j时，可以使用`log`变量调用以上5个级别对应的方法，来输出不同级别的日志！

在Spring Boot项目中，默认的日志显示级别为【info】，将只会显示此级别及更重要级别的日志！可以在`application.properties`中配置`logging.level.根包=日志显示级别`来设置当前显示级别，例如：

```properties
# 日志的显示级别
logging.level.cn.tedu.csmall=error
```























