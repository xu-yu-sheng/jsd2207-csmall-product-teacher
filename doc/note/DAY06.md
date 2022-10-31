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

