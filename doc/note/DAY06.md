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









