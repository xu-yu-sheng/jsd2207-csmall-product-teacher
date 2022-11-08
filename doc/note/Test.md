# Tedu JSD S4 Q&A

#### 【Mybatis】Mybatis框架的主要作用是？

简化数据库编程。

#### 【Mybatis】在Spring Boot项目中使用Mybatis编程时，需要添加的Mybatis依赖项的Artifact是？

`mybatis-spring-boot-starter`

#### 【Mybatis】使用Mybatis框架实现数据访问时，主要编写哪些代码？

在接口中添加抽象方法，在XML中配置映射的SQL语句，并创建可能需要的POJO类，如果是第1次使用，还应该通过`@MapperScan`指定接口所在的根包，并通过`mybatis.mapper-locations`指定XML文件的位置。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是插入数据操作，方法的返回值类型应该是？

`int`，表示受影响的行数。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是删除数据操作，方法的返回值类型应该是？

`int`，表示受影响的行数。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是修改数据操作，方法的返回值类型应该是？

`int`，表示受影响的行数。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是查询数据操作，方法的返回值类型应该是？

原则上只需要保证返回值类型可以“放得下”所需的查询结果即可，通常，统计查询的返回值类型使用`int`，查询某1个数据的返回值类型使用自定义的POJO类型，查询多个数据的返回值类型使用`List`集合，且集合中的元素类型是自定义的POJO类型。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是插入数据操作，建议使用的方法的名称或方法名称的前缀是？

根据阿里巴巴的Java开发手册，应该使用`save`或`insert`作为方法名或方法名称的前缀。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是删除数据操作，建议使用的方法的名称或方法名称的前缀是？

根据阿里巴巴的Java开发手册，应该使用`remove`或`delete`作为方法名或方法名称的前缀。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是修改数据操作，建议使用的方法的名称或方法名称的前缀是？

根据阿里巴巴的Java开发手册，应该使用`update`作为方法名或方法名称的前缀。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是查询数据操作，建议使用的方法的名称或方法名称的前缀是？

根据阿里巴巴的Java开发手册，统计查询使用`count`作为方法名或方法名称的前缀，查询某1个数据使用`get`作为方法名或方法名称的前缀，查询列表使用`list`作为方法名或方法名称的前缀。

#### 【Mybatis】在配置XML文件时，mapper标签上的namespace属性的值应该是？

对应的接口的全限定名，即完整的包名和类名。

#### 【Mybatis】在配置XML文件时，在不考虑代码规范性的情况下，插入数据的SQL语句应该使用什么标签？

可以使用`<insert>`、`<delete>`、`<update>`标签来配置插入数据的SQL语句。

#### 【Mybatis】在配置XML文件时，在不考虑代码规范性的情况下，删除数据的SQL语句应该使用什么标签？

可以使用`<insert>`、`<delete>`、`<update>`标签来配置删除数据的SQL语句。

#### 【Mybatis】在配置XML文件时，在不考虑代码规范性的情况下，修改数据的SQL语句应该使用什么标签？

可以使用`<insert>`、`<delete>`、`<update>`标签来配置修改数据的SQL语句。

#### 【Mybatis】在配置XML文件时，在不考虑代码规范性的情况下，查询数据的SQL语句应该使用什么标签？

只能使用`<select>`标签来配置查询数据的SQL语句。

#### 【Mybatis】在配置XML文件时，如何配置插入数据时获取自动编号的id值？

在`<insert>`标签上配置`useGeneratedKeys="true"`和`keyProperty="存放id值的属性名"`这2个属性，当成功插入数据后，可以通过参数对象来获取自动编号的id值。

#### 【Mybatis】在配置XML文件中的`<select>`标签时，如何选取`resultType`和`resultMap`属性？

如果查询结果是简单数据类型，例如基础数据类型（`int`、`long`等）、`String`等，使用`resultType`即可，否则，使用`resultMap`。

#### 【Mybatis】在配置XML文件时，`<foreach>`标签的`collection`属性值如何配置？

`<foreach>`标签是用于对参数进行遍历的，如果抽象方法的参数只有1个，当被遍历对象是`List`类型时，`collection`属性值为`list`，当被遍历对象是数组类型（含可变参数）时，`collection`属性值为`array`，如果抽象方法的参数超过1个，则抽象方法的参数应该通过`@Param`注解来指定参数名称，且`collection`属性值为`@Param`注解指定的参数名称。

#### 【Mybatis】在配置XML文件时，如何封装SQL语句片段并引用？

可以通过`<sql>`标签来封装SQL语句片段，并通过`<include>`标签中的`refid`属性来引用。

#### 【Lombok】使用Lombok的优点和缺点分别是？

使用Lombok可以在简化编码过程，提高编码效率，并且，在类中的属性需要增减时，原代码需要做的调整也更少，提高了代码的可维护性。

使用Lombok需要在开发工具中安装Lombok插件，在团队协作或更换开发时使用的电脑时，都需要确保编写项目的开发工具都安装了此插件，否则，开发工具将无法智能提示，且对已有的相关代码报错。

#### 【Lombok】Lombok的`@Data`注解的作用是？

在编译期生成Setters & Getters、`hashCode()`与`equals()`、`toString()`方法，使用此注解时，必须保证当前类的父类存在无参数构造方法。

#### 【Spring Boot】在编写测试时，测试类上是否添加`@SpringBootTest`的区别在于？

如果添加了`@SpringBootTest`注解，则在执行测试之前会加载Spring环境，至少包括：读取配置文件、创建必要的对象、完成自动装配等，如果没有添加`@SpringBootTest`注解，则在执行测试之前不会加载Spring环境。

#### 【Spring Boot】在编写测试时，测试类应该创建在哪个位置？

如果执行的测试需要加载Spring环境，测试类必须创建在`src/test/java`下的根包或其子孙包下，并且测试类必须添加`@SpringBootTest`注解，如果不需要加载Spring环境，则测试类可以创建在`src/test/java`下的任意位置，当然，仍推荐创建在根包或其子孙包下。

#### 【Spring Boot】Spring Boot项目的日志默认显示级别是？

info

#### 【Spring Boot】Spring Boot项目如何配置日志的显示级别？

在配置文件中通过`logging.level.根包`属性进行配置，此“根包”至少需要配置1级包名，也可以精确到某个类名，通常配置为项目的根包，此属性的值可以是`trace` / `debug` / `info` / `warn` / `error`其中的某1个。

#### 【Spring Boot】如何在Spring Boot项目使用Profile配置？

使用`application-自定义名称.properties`作为配置文件的名称，此类Profile配置文件默认并不会加载，需要在主配置文件`application.properties`中通过`spring.profiles.active`属性来激活，此属性的值是Profile配置文件的文件名中自定义的部分。

#### 【Spring Boot】在Spring Boot项目如何配置服务端口？

使用`server.port`属性指定服务端口，通常建议使用4位数或5位数的未常用端口，避免与已有服务的端口冲突。



