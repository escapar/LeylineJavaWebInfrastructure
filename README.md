# Leyline 灵脉
A Web Development Workflow , Gendai Mahou made Simple.

The concept of Leyline comes from stories in Ancient English ~~, TYPE-MOON and various GALGAMES~~.


# For what?

We build no more wheels.

Meanwhile , we provide a better integration for starting Web App development smoothly with most fresh and reliable techs.

Smooth = Prototype in a flash , further implement elegantly and achieve final solution in a good pace.

# Usage

Launching
``````
mvn clean install

mvn spring-boot:run
``````

Development

Framework should not be touched fluently.

Try to implement in interfaces and business packages.

# Environment

Spring Boot / Spring Security / Spring Data JPA + QueryDSL + Hibernate 5

Thymeleaf

Angular 1.x

Whalin Memcached Client / Redisson

MySQL


# Fundamental GOALS & SPECS

Automatic Model(ModelMapper)->DTO->Restful API CRUD [OK]

ORM in Spring Data JPA + QueryDSL [Test Needed]

Smooth Integration of JsonView、Spring Security、Pagination/Sort、 Rest And Thymeleaf [Test Needed]

> JsonView + Rest

> Spring Security + Thymeleaf(including Controller) + Rest

> Pagination / Sort + Rest 

> Pagination / Sort + Thymeleaf(including Controller) 


Smooth Integration of Spring Framework @Cachable and Whalin Memcached  [OK] (Check whalin_memcached Branch for usage example)

---------------------------------

Planning :

Basic Styles 

An Admin Panel in Angular Material 

Use Cases And Examples 

A CLI For both specs mentioned!! 


# Long Term GOALS

i18n

A Restful API 2 ExpressJS Model Proxy Codegen

Integration of Other Components in Spring Eco-system (ex. Spring XD)

A CI Including Testing and Automatic Deployment

etc...

# Contributors
POJO http://src.moe/    

# License
MIT

# Donate
PyTrade
