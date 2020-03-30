### 远程仓库(版本库)

+ 国内 [Coding](<https://coding.net/>)

+ 国内 [码云](<https://gitee.com/>)

+ 国外(全球) [Github](<https://github.com/>)




### 工作流程

![](https://images2017.cnblogs.com/blog/63651/201709/63651-20170904202237913-177051853.png)

![](https://images2017.cnblogs.com/blog/63651/201709/63651-20170905212837976-775285128.png)



### 基本命令

+ 初始化项目文件夹: `git init`
+ 添加文件到**暂存区**: `git add filename`
+ 提交**暂存区**文件到本地**版本库**: `git commit -m "备注信息"`
+ 本地版本库同步到**远程版本库**: `git push`
+ 强制**push**: `git push -f`
+ **远程版本库**同步到**本地版本库**: `git pull`
+ 项目回滚: `git reset --hard commidid`



### 团队操作

+ 创建分支: `git branch 分支名`
+ 切换分支: `git checkout 分支名`
+ 创建并切换分支: `git checkout -b 分支名`
+ 合并分支: `git merge 分支名`
+ 删除分支: `git branch -d 分支名`
+ 删除远程分支: `git push origin --delete 分支名`




### 进阶命令

+ 仅删除暂存区的文件(不影响工作区文件): `git rm --cache 文件名`
+ 删除错误提交的**commit**
  - 仅撤销已提交的版本库: `git reset --soft commitid`
  - 仅撤销已提交的版本库和暂存区: `git reset --mixed commitid`
+ 在`.s中可添加忽略(操作)文件,没次进行`git`操作的时候将不会被使用


### 其他知识

![](https://git-scm.com/book/en/v2/book/02-git-basics/images/lifecycle.png)

![](https://images2017.cnblogs.com/blog/63651/201709/63651-20170909091456335-1787774607.jpg)