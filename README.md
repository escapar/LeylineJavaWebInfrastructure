# src.moe
用于实现萌豚友链互换和轻量级讨论版的Py交易网站。
基于master分支的Leyline。

# Usage

Launching
``````
mvn clean install

mvn spring-boot:run
``````

Development

Framework should not be touched fluently.

Try to implement in interfaces and business packages.

# 开发进度

完成了注册登录流程的简单开发。    

流程所用的json如下,其中domain是注册的时候必须的。
```
{
  "username":xxx,
  "password":xxxxx,
  "domain":xxxx
 }
```

POST: api/user/reg    

POST: api/user/login    

登录或注册后会返回一个token的json,随后在token头部加入"JUICE "字符串,将它作为名为Authorization的Request Header的值。    

用户注册时至少指定一个自己的域名,接着有两种方式完成注册。    

> 在域下的任意网页放入key    

api/website/{id}/verify/url

> 请求两个本站好友帮忙认证    

api/website/verify/friend

> 管理员黑箱

# Environment

Spring Boot / Spring Security / Spring Data JPA + QueryDSL + Hibernate 5

Angular 1.x(可能)

Redisson

MySQL


# Fundamental GOALS & SPECS [Done]


# Contributors
POJO http://src.moe/    

# License
MIT

# Donate
PyTrade
