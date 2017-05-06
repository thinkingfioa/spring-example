# 第2章 装配Bean
```
Spring中,对象无需自己查找或创建与其所关联的其他对象.
```
```
装配: 创建应用对象之间协作关系的行为, 这也是依赖注入(DI)的本质.
```
### 2.1 Spring配置的可选方案
##### 三种主要的装配机制
```
- 在XML中进行显示配置;
- 在Java中进行显示配置;
- 隐式的bean发现机制和自动装配;(推荐使用)
```
### 2.2 自动化装配bean(组件扫描+自动装配)
```
- 组件扫描:Spring会自动发现应用上下文中所创建的bean.
- 自动装配:Spring自动满足bean之间的依赖.
```
##### @Component
```
@Component注解表明该类会作为组件类,并告知Spring要为这个类创建bean.
```
##### @ComponentScan注解启动组件扫描
```
组件扫描默认不启动,需要使用@ComponentScan注解启动Spring自动扫描,创建bean.
```
```
@ComponentScan默认会扫描与配置类相同的包和子包.
```
####### XML配置,扫描包
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xmlns:c="http://www.springframework.org/schema/c"
  xmlns:p="http://www.springframework.org/schema/p"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
      
      <context:component-scan base-package="org.thinking.autoconfig.soundsystem" />
</beans>
```
##### 2.2.2为组件扫描的bean命名
```
Spring应用上下文中所有的bean都会给一个ID.如果没有指定,则会将类的第一个字母变成小写的.
比如: SgtPeppers类,那么其id是: sgtPeppers.
```
####### 为id命名
```java

@Component("lonelyHeartsClud")
public class SgtPeppers implements CompactDisc {
	///...
}
```
##### 2.2.3 设置组件扫描的基础包
####### 单个包和子包都会有
```java
@Configuration
@ComponentScan("org.thinking.autoconfig.soundsystem")
public class CDPlayerConfig{}
```
####### 多个指定特定基础包
```Java
@Conguration
@ComponentScan(basePackages="org.thinking.autoconfig.soundsystem","...")
public class CDPlayerConfig{}
```
####### 类型安全的指定特定基础包,扫描类下的包.
```Java
@Conguration
@ComponentScan(basePackageClasses=CDPlayerConfig.class)
public class CDPlayerConfig{}
```
##### 2.2.4 通过为bean添加注解实现自动装配(Autowired)
```
自动装配就是让Spring自动满足bean依赖的一种方法.
```
```
@Autowired注解 可以用于构造器上,属性的Setter方法上,任何方法上; Spring初始化bean之后,会尽可能去满足bean的依赖.
```
```
Spring初始化时候,会尽可能的去满足bean的依赖,如果没有匹配的bean,Spring会抛出一个异常.
避免异常方式: @Autowired(required=false).但使用的时候,请注意空指针.
```
```
@Autowired注解,等同于 @Inject注解
```

### 2.3 通过Java代码来装配Bean

























