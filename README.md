# src.moe
为加速萌豚友链互换和Py交易而设计的网站,包含一个轻量的讨论版。

A place to share minds and make friend links for dev weblog owners , especially for 萌豚.

# Usage

Launching
``````
mvn clean install

mvn spring-boot:run
``````

Development

Framework should not be touched fluently.

Try to implement in interfaces and business packages.

If you have no idea about what's Leyline...check the master branch.


# 食用方法

##注册登录流程

全程所用的json如下,其中domain是注册的时候必须的。
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

如下

```
JUICE eyJhbGciOiJIUzI1NiJ9.eyJzcm9sZSI6MSwibmFtZSI6InRlc3QiLCJpZCI6MTIsImV4cCI6MTQ2Njk0MzMzNn0.yP
```

用户注册时至少指定一个自己的域名,接着有两种方式完成注册。

### 获得用户拥有的网站

```
GET api/website/{owned}
```

### 获得网站信息

```
GET api/website/{id}
```
其中的verifyKey属性就是下文的认证Key


### 在域下的任意网页<head>标签内放入认证key
```
POST api/website/{id}/verify/url
```
requestbody中必须包含url

### 在域下的任意页面Header放入key同名的txt文件,同时文件内容也为认证key
```
POST api/website/{id}/verify/file
```
requestbody中必须包含url

### 请求两个本站好友帮忙认证
```
POST api/website/verify/friend
```
requestbody中必须包含网站的key

## 换友链流程

master邀请 servant接受

API就是
```
GET api/website/{masterId}/{servantId}/link
```
这里的Id都是网站的Id

API会根据登录的用户状态来判断


## 查看自己的友链
```
GET api/website/links
```

# 有待实现的内容
## 自动截图
用户输入域名后尝试后端自动截图。

## 属性和Tags
属性有主语言和地域。

Tags可以肆意生长。

## 公开的API
提供HTML的嵌入式和JSO的数据接口方便用户拿到自己的数据


## 最简易的讨论版
有帖子、活动、投票三种功能,板块按照语言和地域划分。

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