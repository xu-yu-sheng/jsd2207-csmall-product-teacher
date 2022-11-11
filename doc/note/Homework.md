# DAY01（10.24）

在`product`项目中实现以下表的“插入1条数据”的功能：

- 品牌表（`pms_brand`）
- 类别表（`pms_category`）

提示，可通过 https://gitee.com/chengheng2022/jsd2206-csmall-product-teacher 参考。

# DAY02（10.25）

在`product`项目中实现品牌表（`pms_brand`）、类别表（`pms_category`）、属性模板表（`pms_attribute_template`）的功能：

- 插入1条数据
  - 已经完成的不必重复编写
- 批量插入数据
- 根据id删除某1条数据
- 根据若干个id批量删除某些数据
- 根据id修改数据
- 统计当前表中数据的数量
- 根据id查询数据详情
- 查询当前表中的数据列表

# DAY03（10.26）

在`product`项目中实现：

- 添加品牌的业务，业务规则：品牌名称必须唯一
- 添加品牌的控制器，要求可以通过某个URL进行测试访问
- 添加类别的业务，业务规则：类别名称必须唯一
- 添加类别的控制器，要求可以通过某个URL进行测试访问

# DAY04（10.27）

安装Node.js，老师的项目中的教程。

在`product`项目中实现：

- 添加属性模板的业务，业务规则：品牌名称必须唯一
- 添加属性模板的控制器，要求可以通过某个URL进行测试访问
- 配置品牌的控制器、类别的控制器、属性模板的控制器的API文档

# DAY05（10.28）

在`product`项目中实现：

- 补全：共计12张数据表，每张表的Mapper层，即完成8个基础的数据访问功能

在`web-client`项目中实现：

- 补全：在`HomeView`中补全菜单项，不要求修改外观，也不要求菜单项可以点击打开页面，只需要显示出各菜单项即可，如下图所示：

  ![image-20221028180122543](images/image-20221028180122543.png)

![image-20221028180139110](images/image-20221028180139110.png)

![image-20221028180155610](images/image-20221028180155610.png)

提示：其它菜单项暂未设计子级。

# DAY06（10.31）

在`csmall-web-client`（前端项目）中设计以下页面：

- 添加品牌（BrandAddNewView.vue）

![image-20221031173024336](images/image-20221031173024336.png)

提示：以上“是否启用”是通过“开关”控件实现的，可参考：https://element.eleme.cn/#/zh-CN/component/switch

- 添加类别（CategoryAddNewView.vue）

  ![image-20221031173157386](images/image-20221031173157386.png)

- 添加属性模板（AttributeTemplateAddNewView.vue）

![image-20221031173246534](images/image-20221031173246534.png)

- 添加属性（AttributeAddNewView.vue）

  ![image-20221031173330215](images/image-20221031173330215.png)

  提示：以上表单中，“属性模板”的控件是“Select选择器”，可参考：https://element.eleme.cn/#/zh-CN/component/select

  提示：以上表单中，“类型”和“输入类型”的控件是“Radio单选框”，可参考：https://element.eleme.cn/#/zh-CN/component/radio

  提示：以上表单中，“类型”的值的右侧的“i”是通过图标显示的（可参考菜单项的图标），并且，鼠标移上去可以显示一段文本，显示文本可参考：https://element.eleme.cn/#/zh-CN/component/tooltip

- 添加管理员（AdminAddNewView.vue）

![image-20221031173820915](images/image-20221031173820915.png)

# DAY07（11.01）

在`csmall-product`项目中实现：

- 补充业务规则：根据id删除相册时，如果存在关联的SPU数据，则不允许删除，将抛出异常，此关联数据表现在`pms_spu`表的`album_id`字段
- 根据id删除品牌（Brand）数据--业务层
  - 业务规则：数据必须存在，否则抛出异常（ERR_NOT_FOUND）
  - 业务规则：不允许存在关联的品牌数据，否则抛出异常（ERR_CONFLICT），此关联数据表现在`pms_brand_category`表的`brand_id`字段
  - 业务规则：不允许存在关联的SPU数据，否则抛出异常（ERR_CONFLICT），此关联数据表现在`pms_spu`表的`brand_id`字段
- 根据id删除品牌（Brand）数据--控制器层
  - URL格式参考：`/brands/9527/delete`
  - 要求配置API文档的显示文本
  - 可不配置检查请求参数的基本格式
- 根据id删除属性模板（AttributeTemplate）数据--业务层
  - 业务规则：数据必须存在，否则抛出异常（ERR_NOT_FOUND）
  - 业务规则：不允许存在关联的属性数据，否则抛出异常（ERR_CONFLICT），此关联数据表现在`pms_attribute`表的`template_id`字段
  - 业务规则：不允许存在关联的类别数据，否则抛出异常（ERR_CONFLICT），此关联数据表现在`pms_category_attribute_template`表的`attribute_template_id`字段
  - 业务规则：不允许存在关联的SPU数据，否则抛出异常（ERR_CONFLICT），此关联数据表现在`pms_spu`表的`attribute_template_id`字段
- 根据id删除属性模板（AttributeTemplate）数据--控制器层
  - URL格式参考：`/attribute-templates/9527/delete`
  - 要求配置API文档的显示文本
  - 可不配置检查请求参数的基本格式

# DAY08（11.02）

在`csmall-web-client`项目中实现：

- 显示品牌列表（在`mounted`时加载列表并显示）
  - 补充必要的服务器端代码
- 删除品牌（点击列表中的某品牌，经过再次确认后，执行删除）
- 显示属性模板列表（同上）
  - 补充必要的服务器端代码
- 删除属性模板（同上）

# DAY09（11.03）

在`csmall-passport`项目中实现：

- 补全Mapper层对`ams_admin`表的8个基础数据访问功能
  - 8个功能，可参考DAY02作业

在`csmall-product`项目中实现：

- 添加属性--Service层

  - 业务逻辑：同一个属性模板下，属性名称必须唯一

    - 提示：`pms_attribute`表的`template_id`字段表示属性归属于哪个属性模板

    - 提示：在补充抽象方法时，此抽象方法需要2个参数，则各参数之前应该添加`@Param`注解

    - ```java
      int countByNameAndTemplateId(@Param("name") String name, @Param("templateId") Long templateId);
      ```

    - ```mysql
      select count(*) from pms_attribute where name=? and template_id?
      ```

- 添加属性--Controller层

- 添加属性--前端页面

  - 提示：此页面应该在DAY06已完成
  - 在此页面的`mounted`生命周期中，需加载属性模板列表，以显示下拉列表
  - 参考：https://gitee.com/chengheng2022/jsd2206-csmall-web-client-teacher/blob/master/src/views/sys-admin/temp/AttributeAddNewView.vue

# DAY10（11.04）

在`csmall-web-client`和`csmall-product`项目中实现：

- 通过页面设置数据状态：启用品牌、禁用品牌
- 通过页面设置数据状态：启用类别、禁用类别
- 通过页面设置数据状态：将类别显示在导航栏、将类别隐藏（不显示在导航栏）
  - “显示”：`display`
  - “隐藏”：`hidden`

提示：关于显示类别列表，按照显示品牌列表的模式操作即可，也不必考虑分页问题，后续会优化

# DAY11（11.07）

无

# DAY12（11.08）

请思考以下问题，并通过文本文档提交答案：

- Spring Security框架主要解决了什么问题？
  - Spring Security框架主要解决了认证、授权的问题
- BCrypt算法的主要特点是什么？
  - 使用了随机的盐值，所以，即使密码原文相同，每次的编码结果都不同，并且，盐值是编码结果的一部分，所以，也不影响验证密码
  - 被设计为运算非常慢，所以，可以非常有效的避免穷举式的暴力破解
- UUID的主要特点是什么？
  - 随机，唯一
- 在继承了`WebSecurityConfigurerAdapter`的配置类中，重写`void configurer(HttpSecurity http)`方法
  - `http.formLogin()`方法的作用是什么？
    - 开启表单验证！如果已开启，当请求被视为未认证时，将重定向到登录表单页面，如果未开启，当请求被视为未认证时，将响应`403`
  - 配置请求认证的过程中，调用的`mvcMatchers()`方法的作用是什么？
    - 匹配某些路径，需要配置后续的某个方法一起使用，例如`permitAll()`或`authenticated()`等
  - 配置请求认证的过程中，调用的`permitAll()`方法的作用是什么？
    - 允许访问，即不需要通过认证即可访问
  - 配置请求认证的过程中，如果某个路径被多次匹配，最终此路径的规则是什么？
    - 以第1次的配置为准
  - `http.csrf().disable()`的作用是什么？
    - 禁用防伪造的跨域攻击机制
- `UserDetailsService`的作用是什么？
  - 此接口中定义了`UserDetails loadUserByUsername(String username)`的方法，Spring Security处理认证时，会自动根据用户名调用此方法，得到返回的`UserDetails`后，会基于此返回值自动完成后续的判断，例如密码是否匹配、账号是否被禁用等，所以，`UserDetailsService`的作用就是要实现根据用户名返回匹配的用户详情，以至于Spring Security能实现认证
- 如何得到`AuthenticationManager`对象？
  - 在继承了`WebSecurityConfigurerAdapter`的配置类中，重写`authenticationManagerBean()`方法，在此方法上添加`@Bean`注解即可，后续，当需要使用此对象时，使用自动装配机制即可
- 当调用`AuthenticationManager`对象的`authenticate()`方法后，会发生什么？此方法的返回结果是什么？
  - 调用了`authenticate()`方法后，Spring Security会开始执行认证，会通过参数中的用户名来调用`UserDetailsService`接口类型的对象的`loadUserByUsername()`方法，当得到此方法的返回结果后，自动执行后续的判断，例如密码是否匹配、账号是否被禁用等
  - 如果认证通过，将返回`Authentication`接口类型的对象，此对象通常有3大组成部分，分别是Principal（当事人）、Credentials（凭证）、Authorities（权限清单），其中，Principal就是`loadUserByUsername()`方法返回的结果
- Spring Security如何判定某个请求是否已经通过认证？
  - 取决于Security上下文（`SecurityContext`）中是否存在有效的认证信息（`Authentication`）

# DAY13（11.09）

请思考以下问题，并通过文本文档提交答案：

- 相比Session机制，JWT最大的优点是什么？
  - 更适合长时间保存用户状态
- 在Spring Security的配置类的`void configurer(HttpSecurity http)`方法中，`http.cors()`的作用是什么？
  - 启用Spring Security的`CorsFilter`过滤器，此过滤器可以对复杂请求的预检放行
- 根据业内惯用的作法，客户端应该如何携带JWT数据向服务器提交请求？
  - 应该使用请求头中的`Authorization`属性表示JWT数据
- 在服务器端，为什么要使用过滤器而不是其它组件来解析JWT？
  - 过滤器是Java EE体系中最早接收到请求的组件
  - Spring Security内置了一些过滤器，用于处理认证、授权的相关问题，包含检查Security上下文中是否存在认证信息
  - 解析JWT必须执行在Spring Security的相关过滤器之前，否则没有意义
- 在服务器端，JWT过滤器的主要作用是？
  - 尝试解析JWT，如果是有效的，则应该从中获取用户信息，用于创建认证信息（`Authentication`），并将认证信息存入到Security上下文中
- 如果客户端提交的请求没有携带JWT，服务器端的JWT过滤器应该如何处理？
  - 直接放行
- 在Spring Security的配置类的`void configurer(HttpSecurity http)`方法中，为什么要通过`http.addFilter()`系列方法添加JWT过滤器？
  - 如果没有通过`http.addFilter()`系列方法添加JWT过滤器，则JWT过滤器会在Spring Security内置的过滤器链之后执行，则没有意义
- 在服务器端，控制器处理请求时，如何获取当事人信息？
  - 在处理请求的方法的参数列表中，使用`@AuthenticationPrincipal`注解，添加在当事人类型的参数上
  - 当事人的类型，就是Secuirity上下文中`Authentication`对象的当事人类型
- 在服务器端，如何配置方法级别的权限？
  - 在配置类上添加`@EnableGlobalMethodSecurity(prePostEnabled = true)`注解，这是一次性配置
  - 在方法上（通常是处理请求的方法上）使用`@PreAuthorize`注解来配置权限规则

# DAY14（11.10）

在`csmall-product`项目中实现：

- 根据id查询类别详情 / 根据id查询品牌详情 / 根据id查询相册详情 / 根据id查询属性模板详情 / 根据id查询属性详情
  - 开发至控制器层，即：通过API文档可以访问
  - 业务规则：如果没有匹配的数据，抛出异常
- 根据id修改类别详情
  - 开发至控制器层，即：通过API文档可以访问
  - 允许修改的属性：参考`CategoryAddNewDTO`，不包含`parentId`、`enable`、`isDisplay`
  - 业务规则：新的`name`不允许与数据库已有的重复
- 根据id修改品牌详情
  - 开发至控制器层，即：通过API文档可以访问
  - 允许修改的属性：参考`BrandAddNewDTO`，不包含`enable`
  - 业务规则：新的`name`不允许与数据库已有的重复
- 根据id修改相册详情
  - 开发至控制器层，即：通过API文档可以访问
  - 允许修改的属性：参考`AlbumAddNewDTO`
  - 业务规则：新的`name`不允许与数据库已有的重复
- 根据id修改属性模板详情
  - 开发至控制器层，即：通过API文档可以访问
  - 允许修改的属性：参考`AttributeTemplateAddNewDTO`
  - 业务规则：新的`name`不允许与数据库已有的重复
- 根据id修改属性详情
  - 开发至控制器层，即：通过API文档可以访问
  - 允许修改的属性：参考`AttributeAddNewDTO`，不包含`templateId`
  - 业务规则：新的`name`不允许与数据库已有的、相同`templateId`的重复

本次作业的提交截止时间下周一上午9:00。