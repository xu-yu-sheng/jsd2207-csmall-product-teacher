# 单点登录（SSO）

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





