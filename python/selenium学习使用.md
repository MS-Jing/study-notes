# 准备

+ Chrome浏览器
+ chromedriver驱动器，需要和Chrome浏览器版本相匹配
+ python环境下载selenium



# 测试案例：

```python
import selenium
from selenium import webdriver

if __name__ == '__main__':
    # 打开浏览器
    browser = webdriver.Chrome(executable_path="D:\environment\chromedriver\chromedriver.exe")
    # 浏览器最大化
    browser.maximize_window()
    # 请求地址
    url = "https://index.baidu.com/v2/main/index.html#/trend/%E5%A4%A7%E4%BC%97?words=%E5%A4%A7%E4%BC%97"
    browser.get(url)
    print("===============")
    # 浏览器等待操作 等待5秒
    # browser.implicitly_wait(5)

    # 通过xpath寻找表单输入框
    username_input = browser.find_element_by_xpath(
        "//form[@id='TANGRAM__PSP_4__form']/p[@id='TANGRAM__PSP_4__userNameWrapper']/input[@id='TANGRAM__PSP_4__userName']")
    # 表单输入账号
    username_input.send_keys("18728735871")
    # 输入密码
    password_input = browser.find_element_by_xpath(
        "//form[@id='TANGRAM__PSP_4__form']/p[@id='TANGRAM__PSP_4__passwordWrapper']/input[@id='TANGRAM__PSP_4__password']")
    password_input.send_keys("luojing612612")

    # 点击登录按钮
    submit_button = browser.find_element_by_xpath(
        "//form[@id='TANGRAM__PSP_4__form']/p[@id='TANGRAM__PSP_4__submitWrapper']/input[@id='TANGRAM__PSP_4__submit']")
    browser.execute_script("arguments[0].click();", submit_button)



```



# 等待查找某个节点

```python
from selenium.webdriver.support import ui

# ============查找某个节点，直到某个节点查找到再继续====================
wait = ui.WebDriverWait(browser, 10)  # 最长等待10秒
try:
    wait.until(lambda browser: browser.find_element_by_xpath("//dev[@class='aaa']")) # 最多等待10秒，直到找到这个节点
    aaa = browser.find_element_by_xpath("//dev[@class='aaa']") # 找到这个节点再进行后续的操作
except Exception as e:
    print(e)
# 找到某个节点后继续坐操作，或者直接抛出异常
```



# 不弹出浏览器

```python
from selenium.webdriver.chrome.options import Options

chrome_options = Options()
# 增加无头
chrome_options.add_argument('--headless')
chrome_options.add_argument('--disable-gpu')
# 防止被网站识别
chrome_options.add_experimental_option('excludeSwitches', ['enable-automation'])

# 打开浏览器
browser = webdriver.Chrome(executable_path="D:\environment\chromedriver\chromedriver.exe",
                           chrome_options=chrome_options)
```



