# spring-template

#### 介绍
springboot项目模版-ORM(JPA,QueryDSL)-JWT(redis)

#### 软件架构
软件架构说明


## RBAC概念
#### 基于 RBAC 概念， 就会存在3 张基础表： 用户，角色，权限， 以及 2 张中间表来建立 用户与角色的多对多关系，角色与权限的多对多关系。 用户与权限之间也是多对多关系，但是是通过 角色间接建立的。
#### 这里给出了表结构，导入数据库即可。
#### 注： 补充多对多概念： 用户和角色是多对多，即表示：
#### 一个用户可以有多种角色，一个角色也可以赋予多个用户。
#### 一个角色可以包含多种权限，一种权限也可以赋予多个角色。 


## DDD 领域设计
#### 结合现实(业务)，脱离数据库(不先思考数据库如何设计)？