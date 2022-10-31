# 38. 【前端】嵌套路由

在设计前端的视图组件时，如果根级视图（通过是`App.vue`）使用了`<router-view/>`，则表示相关区域将由另一个视图组件来显示，而“另一个视图组件”中也使用了`<router-view/>`，就会出现`<router-view>`的嵌套，则需要在`src/router/index.js`中配置嵌套路由。

在`src/router/index.js`中配置路由对象时，如果对应的视图包含`<router-view/>`，则此路由对象应该配置`children`属性，此属性的配置方式与`routes`常量完全相同，即`children`属性的值类型也是数组类型，且数组元素都是一个个的路由对象，需要配置`path`和`component`属性，例如：

```javascript
const routes = [
  {
    path: '/',
    component: HomeView,
    children: [
      {
        path: '/about',
        component: () => import('../views/AboutView.vue')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('../views/LoginView.vue')
  }
]
```

# 39. 【前端】使用axios

网页客户端向服务器端提交请求，并获取响应结果，可以先在客户端项目中安装axios：

```
npm i axios -S
```

然后，在`main.js`中添加配置：

```javascript
import axios from 'axios';

Vue.prototype.axios = axios;
```

至此，当前项目中任何视图组件都可以通过`this.axios`来使用axios。

# 40. 关于跨域访问

跨域访问：客户端与服务器端不在同一台服务器上。

在默认情况下，不允许发送跨域访问请求，如果发送，在浏览器的控制台中则会提示如下错误：

```
Access to XMLHttpRequest at 'http://localhost:9080/albums/add-new' from origin 'http://localhost:9000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

如果使用的是FireFox浏览器，提示信息如下所示：

```
已拦截跨源请求：同源策略禁止读取位于 http://localhost:9080/albums/add-new 的远程资源。（原因：CORS 头缺少 'Access-Control-Allow-Origin'）。状态码：200。
```

当服务器端允许来自跨域的客户端发送请求时，在Spring Boot项目中，需要使用配置类实现`WebMvcConfigurer`接口，并重写`addCorsMappings()`方法进行配置。

则在`csmall-product`项目中，在根包下创建`config.WebMvcConfiguration`类，在类上添加`@Configuration`注解，并实现`WebMvcConfigurer`接口，重写`addCorsMappings()`方法：

```java
package cn.tedu.csmall.product.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    public WebMvcConfiguration() {
        log.debug("创建配置类对象：WebMvcConfiguration");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
    
}
```

# 41. 关于`@RequestBody`

在Spring MVC项目中（包括添加了`spring-boot-starter-web`依赖项的Spring Boot项目），可以在处理请求的方法的参数列表中，在某参数上添加`@RequestBody`注解。

当请求参数添加了`@RequestBody`注解时，则客户端提供的请求参数必须是**对象格式**的，例如：

```json
{
    'name': '测试相册名称',
    'description': '测试相册简介',
    'sort': 188
}
```

如果客户端提交的请求参数不是对象格式时，将出现以下错误：

```
Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported]
```

当请求参数没有添加`@RequestBody`注解时，则客户端提供的请求参数必须是**FormData格式**的，例如：

```
name=测试相册名称&description=测试相册简介&sort=188
```

如果请求不是FormData格式的，则服务器端将接收不到请求参数，各参数值将为`null`。

# 42. 【前端】使用qs

`qs`是一款可以将对象格式的数据转换成FormData格式的框架。

首先，需要安装`qs`：

```
npm i qs -S
```

然后，在`main.js`中添加配置：

```javascript
import qs from 'qs';

Vue.prototype.qs = qs;
```

在项目中，当需要将对象转换为FormData格式时，例如：

```javascript
let formData = this.qs.stringify(this.ruleForm);
```

# 43. 关于服务器端响应的结果

目前，在服务器端项目中，无论是控制器中的方法正确的处理了请求，或是因为出现了异常而全局异常处理器进行处理再响应，最终响应的结果都是`String`类型的，以“添加相册”为例，服务器端响应的结果可能是：

```
添加相册成功！
```

或：

```
添加相册失败，相册名称已经被占用！
```

当客户端提交请求并获取响应结果后，得到的是以上结果中的某1种，是不便于判断“对”或“错”的，不利于客户端的程序走向不同的分支！

目前，主流的解决方案是：服务器端将向客户端响应JSON格式的结果，此JSON数据中通常包含1个数值，此数值是由服务器端与客户端约定好的一种状态码，另外，此JSON数据中还可以包含其它信息，例如提示文本（例如以上原本响应的字符串），例如：

```json
{
    "state": 1,
    "message": "添加相册成功！"
}
```

或：

```json
{
    "state": 0,
    "message": "添加相册失败，相册名称已经被占用！"
}
```

在Spring Boot项目中，当需要响应JSON格式的字符串时，只需自定义类型，在此类型中设计响应结果中包含的数据属性，并在处理请求、处理异常的方法上使用此类型作为返回值类型即可！

则在根包下创建`xx.xx`类：

``` java
@Data
public class Xxxxx implements Serializable {
    private Integer state;
    private String message;
}
```









