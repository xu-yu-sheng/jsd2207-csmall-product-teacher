# 50. 基于Spring JDBC的事务管理（续）

在**编程式事务管理**过程中，需要先**开启事务（BEGIN）**，然后执行数据操作，当全部完成，需要**提交事务（COMMIT）**，如果失败，则**回滚事务（ROLLBACK）**。

在基于Spring JDBC的项目中，只需要使用**声明式事务**即可，也就是只需要在方法上添加`@Transactional`注解即可。

Spring JDBC实现事务管理大致是：

```
开启事务：Begin
try {
	执行事务方法，即数据访问操作
	提交事务：Commit
} catch (RuntimeException e) {
	回滚事务：Rollback
}
```

所以，Spring JDBC在事务管理中，默认将基于`RuntimeException`进行回滚，可以通过`@Transactional`的`rollbackFor`或`rollbackForClassName`属性来修改，例如：

```java
@Transactional(rollbackFor = {NullPointerException.class, IndexOutOfBoundsException.class})
```

```java
@Transactional(rollbackForClassName = {"java.lang.NullPointerException", "java.lang.IndexOutOfBoundsException"})
```

还可以通过`noRollbackFor`或`noRollbackForClassName`属性来配置对于哪些异常不回滚。

其实，`@Transactional`注解可以添加在：

- 接口上
  - 将作用于实现了此接口的类中的所有业务方法
- 接口中的业务方法上
  - 将作用于实现了此接口的类中的重写的当前业务方法
- 实现类上
  - 将作用于当前类中所有业务方法
- 实现类中的业务方法上
  - 将仅作用于添加了注解的业务方法

提示：如果在业务类和业务方法上都添加了`@Transactional`，却配置了相同名称但不同值的注解参数，将以业务方法上的配置为准。

在应用此注解时，由于这是一种声明式事务管理，推荐添加在接口上，或接口中的业务方法上。

理论上，如果将`@Transactional`添加在接口上，可能有点浪费，毕竟并不是每个业务方法都需要是事务性的。

**注意：由于Spring JDBC在处理事务管理时，使用了基于接口的代理模式，所以，业务方法的内部调用时（同一个业务类对象的A方法调用了B方法），被调用方法相当于是“无事务的”，另外，如果某方法不是接口中声明的业务方法，只是实现类自行添加的方法，无论将`@Transactional`添加在哪里，都是无效的！**







