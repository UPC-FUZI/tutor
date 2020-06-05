###### 配置规范要求：
> 1、禁止将所有配置写在nginx.conf</br>
> 2、虚拟主机按项目+环境+代理模式三个维度，分别配置到不同http-\*.conf或stream-\*.conf文件，放到conf.d目录下</br>
> 3、文件nginx.conf的http块引入http-\*.conf和stream-\*.conf配置

###### 配置文件命名规则：（[]内可省略）
> http类：http-{项目简称}[-{环境}].conf</br>
> stream类：stream-{协议类型}-{项目简称}[-{环境}].conf

###### 配置文件nginx.conf结构
```
#全局块
... 
#events块
events {         
   ...
}
#stream块
stream {
    include /app/xsbank/nginx/app/nginx/conf/conf.d/stream-*.conf;
}
#http块
http {
    #http全局块
    ... 
    include /app/xsbank/nginx/app/nginx/conf/conf.d/http-*.conf;
}
```

###### 配置文件stream-*.conf结构
```
#upstream块
upstream [ServerGroupName] {
    server srv1.example.com;
    server srv2.example.com;
    server srv3.example.com;
}
#server块
server { 
    ...
    proxy_pass http://[ServerGroupName];
}
```

###### 配置文件http-*.conf结构
```
#upstream块
upstream [ServerGroupName] {
    server srv1.example.com;
    server srv2.example.com;
    server srv3.example.com;
}
#server块
server { 
    #server全局块
    ...
    #location块
    location [PATTERN] {
        ...
    }
    location / {
        ...
        proxy_pass http://[ServerGroupName];
    }
}
server {
  ...
}
```

###### 1、全局块
> **配置影响nginx全局的指令**</br>
> 一般有运行nginx服务器的用户组，nginx进程pid存放路径，日志存放路径，允许生成worker process数等。</br>

###### 2、event块
> **配置影响nginx服务器或与用户的网络连接**</br>
> 有每个进程的最大连接数，选取哪种事件驱动模型处理连接请求，是否允许同时接受多个网路连接，开启多个网络连接序列化等。</br>

###### 3、stream块
> **实现四层协议的转发、代理或者负载均衡**</br>
> stream块的配置与http块同级，配置也类似

###### 4、http块
> **包括http全局块和多个server块，配置代理、缓存、日志定义等绝大多数功能和第三方模块的配置**</br>
> **http全局块**：如文件引入，mime-type定义，日志自定义，是否使用sendfile传输文件，连接超时时间，单连接请求数等。</br>

###### 5、server块
> **包括server全局块、location块**</br>
> **server全局块**：配置虚拟主机的相关参数，一个http中可以有多个server。</br>
> **location块**：配置请求的路由，以及各种页面的处理情况。


###### nginx.conf配置样例
```
#配置用户或者组，默认为nobody nobody。
user nginx;  
#允许生成的进程数，默认为1
worker_processes 2;  
#指定nginx进程运行文件存放地址
pid /app/xsbank/nginx/data/nginx.pid;   
#指定日志路径和级别，级别为：debug|info|notice|warn|error|crit|alert|emerg
error_log  /app/xsbank/nginx/log/error.log debug;  

events {
    #设置网路连接序列化，防止惊群现象发生，默认为on
    accept_mutex on;   
    #设置一个进程是否同时接受多个网络连接，默认为off
    multi_accept on;
    #事件驱动模型，select|poll|kqueue|epoll|resig|/dev/poll|eventport
    use epoll;
    #最大连接数，默认为512
    worker_connections  1024;    
}

stream {
    include /app/xsbank/nginx/app/nginx/conf/conf.d/stream-*.conf;
}

http {
    #文件扩展名与文件类型映射表
    include mime.types;  
    #默认文件类型
    default_type application/octet-stream; 
    #自定义格式
    log_format myFormat '$remote_addr–$remote_user [$time_local] $request $status $body_bytes_sent $http_referer $http_user_agent $http_x_forwarded_for - [request_time:$request_time s] - [upstream_response_time:$upstream_response_time s]'; 
    access_log /app/xsbank/nginx/log/access.log myFormat;  

    #允许sendfile方式传输文件
    sendfile on;  
    #每个进程每次调用传输数量不能大于设定的值
    sendfile_max_chunk 100k;  
    #连接超时时间
    keepalive_timeout 65;  
    #引入虚拟主机配置
    include /app/xsbank/nginx/app/nginx/conf/conf.d/http-*.conf;
}	
```

###### conf.d/stream-*.conf配置样例
```
upstream myapp1 {
    hash $remote_addr consistent;
    server srv1.example.com;
    server srv2.example.com;
    server srv3.example.com;
    
}
server {
    listen 12345;
    proxy_connect_timeout 1s;
    proxy_timeout 3s;
    proxy_pass myapp1;
}
```

###### conf.d/http-*.conf配置样例
```
upstream myapp2 {
    server srv1.example.com;
    server srv2.example.com;
    server srv3.example.com;
}
server {
    #监听端口
    listen 3129;
    #监听主机名称
    server_name  localhost;
    location / {
        proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for; 
        proxy_pass myapp2;
    }
}
#配置ssl证书
server {
    listen              443 ssl;
    server_name         localhost;
    #ssl证书文件位置(常见证书文件格式为：crt/pem)
    ssl_certificate     {crt目录}/cert.crt;
    #ssl证书key位置
    ssl_certificate_key {key目录}/cert.key;
    location / {
        proxy_pass https://www.example.com;
    }
}
```

> **注：上文叙述中，默认nginx按照生产目录规范安装**

