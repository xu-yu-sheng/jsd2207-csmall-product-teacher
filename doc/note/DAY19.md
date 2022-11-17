# 在前端项目中使用富文本编辑器

本次使用的富文本编辑器是`wangeditor`，需要先在项目中安装此富文本编辑器：

```
npm i wangeditor -S
```

然后，需要在`main.js`中配置：

```javascript
import wangEditor from 'wangeditor';
Vue.prototype.wangEditor = wangEditor;
```

在需要使用富文本编辑器的视图中，先在视图的设计中，使用某个标签来表示需要显示富文本编辑器的区域，通常，使用`<div>`标签即可，后续，富文本编辑器将会显示在这个`<div>`内部，例如：

```html
<el-form-item label="商品详情">
	<div id="wangEditor"></div>
</el-form-item>
```

然后，在`data`中声明对应的属性：

```javascript
export default {
  data() {
    return {
      editor: {}, // 富文本编辑器
      // 省略不相关代码
```

并且，准备一个用于初始化富文本编辑器的函数：

```javascript
initWangEditor() {
  this.editor = new this.wangEditor('#wangEditor');
  this.editor.create();
},
```

最后，在视图刚刚加载时，就调用此初始化函数：

```javascript
mounted() {
    this.initWangEditor(); // 初始化富文本编辑器
    // 省略不相关代码
}
```

