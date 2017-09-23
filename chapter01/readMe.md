# Chapter 01

### 1.1 简化Java开发
##### 1.1.2 依赖注入(DI)
```
(DI)让相互协作的软件组件保持松散耦合.
```
```
通过DI,对象的依赖关系将由系统中负责协调各对象的第三方组件在创建对象时候设定.
```
```
DamselRescuingKnight是一个紧耦合
BraveKnight是一个松耦合
```

##### 1.1.3 AOP
```
(AOP)允许把遍布应用各处的功能分离出来形成可重用的组件.
```
```
Spring提供的日志,事务管理,安全就是采用Aop技术.
```

##### 1.1.4 使用模板消除样板式代码.

### 1.2 容纳你的Bean
```
基于Spring的应用中,你的应用对象生存于Spring容器(Container)中.
```
```
Spring提供两种不同类型的容器.bean工厂+应用上下文(推荐使用).
```
##### 1.2.1 使用应用上下文(推荐)
```
- AnnotationConigApplicationContext: 从一个或多个基于Java的配置类中加载Spring应用上下文
- AnnotationConfigWebApplicationContext: 从一个或多个基于Java的配置类中加载Spring Web 应用上下文
- ClassPathXmlApplicationContext: 从类路径下的一个或多个XML配置文件中加载上下文定义,把应用上下文的定义文件作为类资源.
- FileSystemXmlapplicationcontext: 从文件系统下的一个或多个XML配置文件中加载上下文定义.
- XmlWebApplicationContext: 从Web应用下的一个或多个XML配置文件中加载上下文定义
```
####### 区别
```
- FileSystemXmlapplicationcontext从指定的文件系统路径中查找*.xml文件.
- ClassPathXmlApplicationContext从所有的类路径(包含JAR文件)下查找*.xml文件.
```
##### 1.2.2 bean的生命周期
```
bean是随着容器一起启动的.
```