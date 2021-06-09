# Nginx

## nginx简介

> nginx 是一个高性能的http和反向代理Web服务器。具有内存少，并发强等特点
>
> nginx能经受高负载



## 反向代理

> 后端有多台服务器，我们只用访问nginx服务器，由nginx帮我们反向代理

```conf
server{
        listen       9001;
        server_name  localhost;

        location ~ /eduservice/ {
            proxy_pass http://localhost:8001;
        }
        location ~ /mock/ {
            proxy_pass http://localhost:8001;
        }
        location ~ /oss/ {
            proxy_pass http://localhost:8002;
        }
        location ~ /vod/ {
            proxy_pass http://localhost:8003;
        }
    }
```



## 负载均衡

> 负载均衡策略：
>
> + 内置策略：
>   + 轮询
>   + 加权
>   + iphash
>   + 扩展策略

```conf
   upstream study {
     server 127.0.0.1:8080 weight=1;
     server 127.0.0.1:8080 weight=1;
    }

   server{
       listen       8080;
       server_name  localhost;

       location / {
            proxy_pass http://study;
        }
   }

```



## 动静分离

