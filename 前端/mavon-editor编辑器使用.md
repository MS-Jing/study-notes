# mavon-editor编辑器使用

基于Vue的Markdown编辑器

```bash
cnpm install mavon-editor --save
```



全局注册

```javascript
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)
```

页面使用：

```html
<mavon-editor v-model="ruleForm.content" placeholder="内容"></mavon-editor>
```



解析md文档

```bash
# 解析md文档
cnpm install markdown-it --save

#md样式
cnpm install github-markdown-css
```



```javascript
var MardownIt = require("markdown-it")
var md = new MardownIt()
md.render(content) #将md文档解析成html再渲染到页面
```

渲染页面主题样式

```javascript
import 'github-markdown-css'
# 在使用内容显示的标签加上class="markdown-body"
```

