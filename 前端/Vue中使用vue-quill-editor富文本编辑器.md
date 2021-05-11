> !!!该组件兼容IE10+版本，由于我们只是在某些页面使用该组件，所以我们可以把该组件封装一下，在需要的地方引用使用一下便是。
>
> + 坑点：Vue2后面引入缩放组件的时候会报错，需要配置 请参照下文imports 没有定义的问题

# 安装

```bash
npm install vue-quill-editor -S
```

# 基本使用

```vue
<template>
  <div>
    <quill-editor
      class="editor"
      ref="myQuillEditor"
      v-model="content"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @ready="onEditorReady($event)"
      @change="onEditorChange($event)"
    />
  </div>
</template>

<script>
    import Quill from "quill";
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import {quillEditor} from 'vue-quill-editor'

    import ImageResize from "quill-image-resize-module"; // 引用
    Quill.register("modules/imageResize", ImageResize); // 注册
    import {ImageDrop} from "quill-image-drop-module";
    Quill.register("modules/imageDrop", ImageDrop);

    // 工具栏配置
    const toolbarOptions = [
        ["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线 -----['bold', 'italic', 'underline', 'strike']
        ["blockquote", "code-block"], // 引用  代码块-----['blockquote', 'code-block']
        [{header: 1}, {header: 2}], // 1、2 级标题-----[{ header: 1 }, { header: 2 }]
        [{list: "ordered"}, {list: "bullet"}], // 有序、无序列表-----[{ list: 'ordered' }, { list: 'bullet' }]
        [{script: "sub"}, {script: "super"}], // 上标/下标-----[{ script: 'sub' }, { script: 'super' }]
        [{indent: "-1"}, {indent: "+1"}], // 缩进-----[{ indent: '-1' }, { indent: '+1' }]
        [{direction: "rtl"}], // 文本方向-----[{'direction': 'rtl'}]
        [{size: ["small", false, "large", "huge"]}], // 字体大小-----[{ size: ['small', false, 'large', 'huge'] }]
        [{header: [1, 2, 3, 4, 5, 6, false]}], // 标题-----[{ header: [1, 2, 3, 4, 5, 6, false] }]
        [{color: []}, {background: []}], // 字体颜色、字体背景颜色-----[{ color: [] }, { background: [] }]
        [{font: []}], // 字体种类-----[{ font: [] }]
        [{align: []}], // 对齐方式-----[{ align: [] }]
        ["clean"], // 清除文本格式-----['clean']
        ["image"] // 链接、图片、视频-----['link', 'image', 'video']
    ];

    export default {
        name: "MyQuillEditor",
        components: {
            quillEditor
        },
        props:{
            placeholder:'',
            value:''
        },
        //用于双向绑定父组件值改变，子组件也要改变
        watch:{
            value(val){
                this.content = val
            }
        },
        data() {
            return {
                // 富文本编辑器默认内容
                content: '',
                //富文本编辑器配置
                editorOption: {
                    modules: {
                        //工具栏定义的
                        toolbar: {
                            container: toolbarOptions,  // 工具栏
                        }
                    },
                    //主题
                    theme: "snow",
                    placeholder: this.placeholder
                }
            }
        },
        methods: {
            //失去焦点事件
            onEditorBlur(quill) {
                // console.log('editor blur!', quill)
            },
            //获得焦点事件
            onEditorFocus(quill) {
                // console.log('editor focus!', quill)
            },
            // 准备富文本编辑器
            onEditorReady(quill) {
                // console.log('editor ready!', quill)
            },
            //内容改变事件
            onEditorChange({quill, html, text}) {
                // console.log('editor change!', quill, html, text)
                // console.log(this.content)
                this.$emit("editorChange",html)
                this.$emit('input',html)   //用于双向绑定
            },
        }
    }
</script>

<style>
   # 这是为了解决中文的问题的样式 
  .ql-editor {
    min-height: 300px;
  }

  .editor {
    line-height: normal !important;
  }

  .ql-snow .ql-tooltip[data-mode="link"]::before {
    content: "请输入链接地址:";
  }

  .ql-snow .ql-tooltip.ql-editing a.ql-action::after {
    border-right: 0px;
    content: "保存";
    padding-right: 0px;
  }

  .ql-snow .ql-tooltip[data-mode="video"]::before {
    content: "请输入视频地址:";
  }

  .ql-snow .ql-picker.ql-size .ql-picker-label::before,
  .ql-snow .ql-picker.ql-size .ql-picker-item::before {
    content: "14px";
  }

  .ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
  .ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
    content: "10px";
  }

  .ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
  .ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
    content: "18px";
  }

  .ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
  .ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
    content: "32px";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item::before {
    content: "文本";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
    content: "标题1";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
    content: "标题2";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
    content: "标题3";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
    content: "标题4";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
    content: "标题5";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
    content: "标题6";
  }

  .ql-snow .ql-picker.ql-font .ql-picker-label::before,
  .ql-snow .ql-picker.ql-font .ql-picker-item::before {
    content: "标准字体";
  }

  .ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
  .ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
    content: "衬线字体";
  }

  .ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
  .ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
    content: "等宽字体";
  }
</style>
```

# 图片上传

​	它自带了图片上传功能，但是会被Base64编码太过于麻烦。我们可以自定义文件上传让它上传到我们指定的后端然后返回url以一个img标签填充既可，这样上传到数据库也就没有那么大了

```vue
<template>
  <div>
    <quill-editor
      class="editor"
      ref="myQuillEditor"
      v-model="content"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @ready="onEditorReady($event)"
      @change="onEditorChange($event)"
    />

    <!--这个用于自定义上传文件的表单-->
    <form action method="post" enctype="multipart/form-data">
      <input
        style="display: none"
        :id="uniqueId"
        type="file"
        name="file"
        multiple
        accept="image/jpg, image/jpeg, image/png, image/gif"
        @change="uploadImg"
      />
    </form>
  </div>
</template>

<script>
    import Quill from "quill";
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import {quillEditor} from 'vue-quill-editor'

    import http from '@/utils/request'

    // 工具栏配置
  	......

    export default {
       ......
        data() {
            return {
                uniqueId: "uniqueId",   //标识自定义文件上传的那个表单
                // 富文本编辑器默认内容
                content: '',
                //富文本编辑器配置
                editorOption: {
                    modules: {
                        //工具栏定义的
                        toolbar: {
                            container: toolbarOptions,  // 工具栏
                            handlers: {  //自定义图片处理器
                                'image': function (value) {
                                    if (value) {
                                        document.getElementById("uniqueId").click();
                                    } else {
                                        this.editor.format('image', false);
                                    }
                                }
                            }
                        }
                    },
                    //主题
                    theme: "snow",
                    placeholder: this.placeholder
                }
            }
        },
        computed: {
            editor() {
                return this.$refs.myQuillEditor.quill;
            }
        },
        methods: {
           ......
            
            //图片上传及回显
            uploadImg: async function () {
                var _this = this;
                //构造formData对象
                var formData = new FormData();
                formData.append("file", document.getElementById(_this.uniqueId).files[0]);

                try {
                    //调用上传文件接口
                    http.post("/oss/uploadFile", formData).then(res => {
                        //返回上传文件的地址
                        let url = res.data;
                        if (url != null && url.length > 0) {
                            let Range = _this.editor.getSelection();
                            url = http.defaults.baseURL + url;
                            //上传文件成功之后在富文本中回显(显示)
                            _this.editor.insertEmbed(
                                Range != null ? Range.index : 0,
                                "image",
                                url
                            );
                        } else {
                            _this.$message.warning("图片上传失败");
                        }
                        //成功之后,将文件的文本框的value置空
                        document.getElementById(_this.uniqueId).value = "";
                    });
                } catch ({message: msg}) {
                    document.getElementById(_this.uniqueId).value = "";
                    _this.$message.warning(msg);
                }
            }
        }
    }
</script>

<style>
	......
</style>
```

# 图片缩放和移动

​	上面的功能以基本满足使用的需求了，但是我们发现图片不是很美观，位置也不是我们想要的。



引入相关依赖：

```bash
npm install quill-image-resize-module -S  //缩放
npm install quill-image-drop-module -S //拖动
npm install quill -S
```

上代码：

```vue
<template>
  <div>
    ......
      
    <!--这个用于自定义上传文件的表单-->
    ......
  </div>
</template>

<script>
    import Quill from "quill";
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import {quillEditor} from 'vue-quill-editor'

	// 注意这里会报错 说imports 没有定义
    import ImageResize from "quill-image-resize-module"; // 引用
    Quill.register("modules/imageResize", ImageResize); // 注册
    import {ImageDrop} from "quill-image-drop-module";
    Quill.register("modules/imageDrop", ImageDrop);

    import http from '@/utils/request'


    // 工具栏配置
   ......

    export default {
        ......
        data() {
            return {
                uniqueId: "uniqueId",   //标识自定义文件上传的那个表单
                // 富文本编辑器默认内容
                content: '',
                //富文本编辑器配置
                editorOption: {
                    modules: {
                        imageDrop: true,
                        imageResize: {
                            displayStyles: {
                                backgroundColor: "black",
                                border: "none",
                                color: "white"
                            },
                            modules: ["Resize", "DisplaySize", "Toolbar"]
                        },
                        //工具栏定义的
                        toolbar: {
                            ......
                        }
                    },
                    //主题
                    theme: "snow",
                    placeholder: this.placeholder
                }
            }
        },
        computed: {
            ......
        },
        methods: {
           ......
        }
    }
</script>

<style>
    ......
</style>

```

## imports 没有定义的问题

​	找到你的 build/webpack.base.conf.js文件，找到插件的位置，加上：

```js
plugins: [
    ...,   //这里是你之前的配置，不要改这是一个数组，在后面加个,号就行，没有配置就不加，直接写
    new webpack.ProvidePlugin({
      'window.Quill': 'quill/dist/quill.js',
      'Quill': 'quill/dist/quill.js'
    })
  ],
```

加上要重启，但是你重启会失败，注意这个文件内没有webpack，在文件上面引入就好了`const webpack = require('webpack')`

重启大功告成



# 完整代码

```vue
<template>
  <div>
    <quill-editor
      class="editor"
      ref="myQuillEditor"
      v-model="content"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @ready="onEditorReady($event)"
      @change="onEditorChange($event)"
    />

    <!--这个用于自定义上传文件的表单-->
    <form action method="post" enctype="multipart/form-data">
      <input
        style="display: none"
        :id="uniqueId"
        type="file"
        name="file"
        multiple
        accept="image/jpg, image/jpeg, image/png, image/gif"
        @change="uploadImg"
      />
    </form>
  </div>
</template>

<script>
    import Quill from "quill";
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import {quillEditor} from 'vue-quill-editor'

    import ImageResize from "quill-image-resize-module"; // 引用
    Quill.register("modules/imageResize", ImageResize); // 注册
    import {ImageDrop} from "quill-image-drop-module";
    Quill.register("modules/imageDrop", ImageDrop);

    import http from '@/utils/request'

    // 工具栏配置
    const toolbarOptions = [
        ["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线 -----['bold', 'italic', 'underline', 'strike']
        ["blockquote", "code-block"], // 引用  代码块-----['blockquote', 'code-block']
        [{header: 1}, {header: 2}], // 1、2 级标题-----[{ header: 1 }, { header: 2 }]
        [{list: "ordered"}, {list: "bullet"}], // 有序、无序列表-----[{ list: 'ordered' }, { list: 'bullet' }]
        [{script: "sub"}, {script: "super"}], // 上标/下标-----[{ script: 'sub' }, { script: 'super' }]
        [{indent: "-1"}, {indent: "+1"}], // 缩进-----[{ indent: '-1' }, { indent: '+1' }]
        [{direction: "rtl"}], // 文本方向-----[{'direction': 'rtl'}]
        [{size: ["small", false, "large", "huge"]}], // 字体大小-----[{ size: ['small', false, 'large', 'huge'] }]
        [{header: [1, 2, 3, 4, 5, 6, false]}], // 标题-----[{ header: [1, 2, 3, 4, 5, 6, false] }]
        [{color: []}, {background: []}], // 字体颜色、字体背景颜色-----[{ color: [] }, { background: [] }]
        [{font: []}], // 字体种类-----[{ font: [] }]
        [{align: []}], // 对齐方式-----[{ align: [] }]
        ["clean"], // 清除文本格式-----['clean']
        ["image"] // 链接、图片、视频-----['link', 'image', 'video']
    ];

    export default {
        name: "MyQuillEditor",
        components: {
            quillEditor
        },
        props:{
            placeholder:'',
            value:'',
        },
        //用于双向绑定父组件值改变，子组件也要改变
        watch:{
            value(val){
                this.content = val
            }
        },
        data() {
            return {
                uniqueId: "uniqueId",   //标识自定义文件上传的那个表单
                // 富文本编辑器默认内容
                content: '',
                //富文本编辑器配置
                editorOption: {
                    modules: {
                        imageDrop: true,
                        imageResize: {
                            displayStyles: {
                                backgroundColor: "black",
                                border: "none",
                                color: "white"
                            },
                            modules: ["Resize", "DisplaySize", "Toolbar"]
                        },
                        //工具栏定义的
                        toolbar: {
                            container: toolbarOptions,  // 工具栏
                            handlers: {  //自定义图片处理器
                                'image': function (value) {
                                    if (value) {
                                        document.getElementById("uniqueId").click();
                                    } else {
                                        this.editor.format('image', false);
                                    }
                                }
                            }
                        }
                    },
                    //主题
                    theme: "snow",
                    placeholder: this.placeholder
                }
            }
        },
        computed: {
            editor() {
                return this.$refs.myQuillEditor.quill;
            }
        },
        methods: {
            //失去焦点事件
            onEditorBlur(quill) {
                // console.log('editor blur!', quill)
            },
            //获得焦点事件
            onEditorFocus(quill) {
                // console.log('editor focus!', quill)
            },
            // 准备富文本编辑器
            onEditorReady(quill) {
                // console.log('editor ready!', quill)
            },
            //内容改变事件
            onEditorChange({quill, html, text}) {
                // console.log('editor change!', quill, html, text)
                // console.log(this.content)
                this.$emit("editorChange",html)
                this.$emit('input',html)   //用于双向绑定
            },
            //图片上传及回显
            uploadImg: async function () {
                var _this = this;
                //构造formData对象
                var formData = new FormData();
                formData.append("file", document.getElementById(_this.uniqueId).files[0]);

                try {
                    //调用上传文件接口
                    http.post("/oss/uploadFile", formData).then(res => {
                        //返回上传文件的地址
                        let url = res.data;
                        if (url != null && url.length > 0) {
                            let Range = _this.editor.getSelection();
                            url = http.defaults.baseURL + url;
                            //上传文件成功之后在富文本中回显(显示)
                            _this.editor.insertEmbed(
                                Range != null ? Range.index : 0,
                                "image",
                                url
                            );
                        } else {
                            _this.$message.warning("图片上传失败");
                        }
                        //成功之后,将文件的文本框的value置空
                        document.getElementById(_this.uniqueId).value = "";
                    });
                } catch ({message: msg}) {
                    document.getElementById(_this.uniqueId).value = "";
                    _this.$message.warning(msg);
                }
            }
        }
    }
</script>

<style>
  .ql-editor {
    min-height: 150px;
  }

  .editor {
    line-height: normal !important;
  }

  .ql-snow .ql-tooltip[data-mode="link"]::before {
    content: "请输入链接地址:";
  }

  .ql-snow .ql-tooltip.ql-editing a.ql-action::after {
    border-right: 0px;
    content: "保存";
    padding-right: 0px;
  }

  .ql-snow .ql-tooltip[data-mode="video"]::before {
    content: "请输入视频地址:";
  }

  .ql-snow .ql-picker.ql-size .ql-picker-label::before,
  .ql-snow .ql-picker.ql-size .ql-picker-item::before {
    content: "14px";
  }

  .ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
  .ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
    content: "10px";
  }

  .ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
  .ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
    content: "18px";
  }

  .ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
  .ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
    content: "32px";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item::before {
    content: "文本";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
    content: "标题1";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
    content: "标题2";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
    content: "标题3";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
    content: "标题4";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
    content: "标题5";
  }

  .ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
  .ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
    content: "标题6";
  }

  .ql-snow .ql-picker.ql-font .ql-picker-label::before,
  .ql-snow .ql-picker.ql-font .ql-picker-item::before {
    content: "标准字体";
  }

  .ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
  .ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
    content: "衬线字体";
  }

  .ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
  .ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
    content: "等宽字体";
  }
</style>
```

