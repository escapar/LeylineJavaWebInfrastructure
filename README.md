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

## 1.注册登录流程

全程所用的json如下,其中domain(域名)是注册的时候必须的。
```
{
  "username":xxx,
  "password":xxxxx,
  "domain":xxxx
 }
```

```
POST api/user/reg

POST api/user/login
```

登录或注册后会返回一个token的json。

随后在返回的token头部append `JUICE `字符串,将它作为名为`Authorization`的`Request Header`。

如下

```
JUICE eyJhbGciOiJIUzI1NiJ9.eyJzcm9sZSI6MSwibmFtZSI6InRlc3QiLCJpZCI6MTIsImV4cCI6MTQ2Njk0MzMzNn0.yP
```

用户注册时应指定一个自己的域名。

## 2.获得网站信息

```
GET api/website/owned 获得用户名下的所有网站(推荐)
GET api/website/{id} 获得单个网站信息
```

你能得到
```
[
  {
    "id": "1",
    "createdAt": 1466333430164,
    "description": null,
    "domain": "src.moe",
    "modifiedAt": null,
    "screenshot": null,
    "title": "s",
    "username": null,
    "verifyKey": null
  }
]
```
其中的verifyKey属性就是下文的认证Key


## 3.验证网站属于用户

### 在域下的任意网页<head>标签内放入认证key
```
POST api/website/{id}/verify/url
```
`requestbody`中要写url

### 在域下的任意页面Header放入key同名的txt文件,同时文件内容也为认证key
```
POST api/website/{id}/verify/file
```
`requestbody`中要写url

### 请求两个本站好友帮忙认证
```
POST api/website/verify/friend
```
`requestbody`中要写网站的认证Key


## 4.换友链流程

master邀请 servant接受

```
GET api/website/{masterId}/{servantId}/link
```
这里的Id都是网站的Id

API会根据登录的用户状态来判断


## 5.查看自己的友链
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
提供HTML的嵌入式代码和JSON的数据接口方便用户拿到自己的数据插入blog。

## NewsFeed
用户可以提供网站的RssFeed

我们在网站显眼位置显示最新文章的标题和简介。

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