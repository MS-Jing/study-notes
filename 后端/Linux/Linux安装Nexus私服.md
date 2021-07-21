+ 解压` tar -zvxf latest-unix.tar.gz `
+ 启动` nexus-3.19.1-01/bin/nexus start `

开放端口：

 	在`/etc/sysconfig/iptables`文件中加入 ` -A INPUT -p tcp -m state --state NEW -m tcp --dport 8081 -j ACCEPT `

配置生效：` service iptables restart `

访问8081端口

密码：` /opt/nexus/sonatype-work/nexus3/admin.password `

