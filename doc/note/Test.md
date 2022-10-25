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

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是插入数据操作，建议使用的方法的名称或方法名称的前缀是？

根据阿里巴巴的Java开发手册，应该使用`save`或`insert`作为方法名或方法名称的前缀。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是删除数据操作，建议使用的方法的名称或方法名称的前缀是？

根据阿里巴巴的Java开发手册，应该使用`remove`或`delete`作为方法名或方法名称的前缀。

#### 【Mybatis】在声明抽象方法时，如果要执行的数据访问是修改数据操作，建议使用的方法的名称或方法名称的前缀是？

根据阿里巴巴的Java开发手册，应该使用`update`作为方法名或方法名称的前缀。

#### 【Mybatis】在配置XML文件时，mapper标签上的namespace属性的值应该是？

对应的接口的全限定名，即完整的包名和类名。

#### 【Lombok】使用Lombok的优点和缺点分别是？

使用Lombok可以在简化编码过程，提高编码效率，并且，在类中的属性需要增减时，原代码需要做的调整也更少，提高了代码的可维护性。

使用Lombok需要在开发工具中安装Lombok插件，在团队协作或更换开发时使用的电脑时，都需要确保编写项目的开发工具都安装了此插件，否则，开发工具将无法智能提示，且对已有的相关代码报错。

#### 【Lombok】Lombok的`@Data`注解的作用是？

在编译期生成Setters & Getters、`hashCode()`与`equals()`、`toString()`方法，使用此注解时，必须保证当前类的父类存在无参数构造方法。

#### 【Spring Boot】在编写测试时，测试类上是否添加`@SpringBootTest`的区别在于？

如果添加了`@SpringBootTest`注解，则在执行测试之前会加载Spring环境，至少包括：读取配置文件、创建必要的对象、完成自动装配等，如果没有添加`@SpringBootTest`注解，则在执行测试之前不会加载Spring环境。

#### 【Spring Boot】在编写测试时，测试类应该创建在哪个位置？

如果执行的测试需要加载Spring环境，测试类必须创建在`src/test/java`下的根包或其子孙包下，并且测试类必须添加`@SpringBootTest`注解，如果不需要加载Spring环境，则测试类可以创建在`src/test/java`下的任意位置，当然，仍推荐创建在根包或其子孙包下。

#### 【Spring Boot】









