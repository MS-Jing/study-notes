# 安装

+ 将Tesseract-OCR.zip压缩包解压到指定目录既可

## 配置环境变量

如果你只想在Tesseract-OCR的目录下使用，那你不配置也是可以的

+ 配置根目录，在Path目录中添加Tesseract-OCR的根目录，例如：`D:\Program Files (x86)\Tesseract-OCR`

+ 配置语言包的目录，tesseract会根据这个环境变量来查找语言包的目录
  + 在系统环境变量中新建然后变量名一定要为:`TESSDATA_PREFIX`
  + 变量的值是你Tesseract-OCR目录下的tessdata目录的绝对路径，例如: `D:\Program Files (x86)\Tesseract-OCR\tessdata\`
  + 配置好了一定要层层点确定退出来，在桌面打开cmd输入`tesseract -v`出现了版本号说明配置成功

# 使用

你可以准备一张英文的图片或者中文的图片，然后使用命令：

```bash
tesseract 目标图片的路径 输出文本的路径(会在后面添加txt后缀的) -l 使用的语言库(默认使用eng英文语言包，如果全是英文可以不使用该参数)
```

例如英文：

```bash
tesseract en_test.png test
```

例如中文:

```bash
tesseract test.png test -l chi_sim
```

如果要使用多个语言包，例如识别中文和英文，多个语言库直接用+号连接:

```bash
tesseract test.png test -l chi_sim+eng
```



识别的图片尽量不要太花哨了色彩越丰富，误差就越大

# Python使用

安装依赖：

```bash
pip install Image
pip install pytesseract
```

代码：

```python
import pytesseract
from PIL import Image

if __name__ == '__main__':
    img = Image.open("test.png")
    pytesseract.pytesseract.tesseract_cmd="D:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe"
    text = ""
    try:
        text = pytesseract.image_to_string(img,lang="chi_sim+eng")
    except TypeError as e:
        print(e.args)
    print(text)
```









