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
```
自动化装配bean是最完美的Spring依赖注入方式.但是,有些情况下,可能会失效:
比如:使用第三方库组件,你无法再类中加上@Component注解和@Autowired注解
```
```
编写配置代码,但请不要和业务逻辑放在一起.
```
##### 2.3.1 创建配置类
```
@Configuration注解表明这个类是Spring配置类.该类包含在Spring应用上下文中如何创建bean细节.
```
##### 2.3.2 声明简单的bean
```java 
@Configuration
public class JavaConfig{
	@Bean
	public CompactDisc sgtPeppers(){
		return new SgtPeppers();
	}
}
```
Note:
```
上面的方法对应一个Bean,缺省bean的id名是:sgtPeppers;
```
```java 
@Configuration
public class JavaConfig{
	@Bean(name="lonelyHeartsClubBand")
	public CompactDisc sgtPeppers(){
		return new SgtPeppers();
	}
}
```
##### 2.3.3 借助JavaConfig实现注入
```Java
@Configuration
public class CDPlayerConfig {
	
	@Bean
	public CompactDisc sgtPeppers(){
		// 这里其实可以返回任何实例.
		return new SgtPeppers();
	}
	
	@Bean
	public CDPlayer cdPlayer(){
		return new CDPlayer(sgtPeppers());
	}
    
    @Bean
	public CDPlayer anotherCDPlayer(){
		return new CDPlayer(sgtPeppers());
	}
}
```
Note:
```
Spring会拦截所有获取Bean方法,所以该容器中只有一个sgtPeppers();
也就是说默认情况下,Spring中的bean都是单例的.
比如: anotherCDPlayer, cdPlayer Bean会得到相同SgtPeppers实例.
```
####### 推荐JavaConfig注入
```java
@Configuration
public class CDPlayerConfig {
  
  @Bean
  public CompactDisc compactDisc() {
    return new SgtPeppers();
  }
  
  @Bean
  public CDPlayer cdPlayer(CompactDisc compactDisc) {
    return new CDPlayer(compactDisc);
  }

}
```
Note:
```
这种依赖依赖注入,非常优秀.因为CompactDisc用什么形式创建的Bean都可以.
```

### 2.4 通过XML装配bean
##### 2.4.2 声明一个简单的bean
```xml
//bean缺省的ID是:org.thinking.xmlconfig.soundsystem.SgtPeppers#0
//建议,都填上ID
<bean class="org.thinking.xmlconfig.soundsystem.SgtPeppers" />
```
####### 简单bean声明的特征
```
1. 不再需要直接负责创建SgtPeppers的实例.Spring发现这个<bean>会调用默认构造函数创建bean
```
```
2. 要非常留心:这里的bean的类型是以字符串形式设置在class属性中.不会进行编译的类型检测.
```
##### 2.4.3 借助构造器注入初始化bean
```
Spring提供XML两种声明DI的方式:
1. <contructor-arg> 元素
2. 使用Spring3.0 所引入的c-命名空间
```
####### 构造器注入bean引用
1. contructor-arg元素(推荐)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="soundsystem.SgtPeppers" />
  
  <bean id="cdPlayer" class="soundsystem.properties.CDPlayer">
    <constructor-arg ref="compactDisc" />
  </bean>
  
</beans>
```

2. c-命名空间

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:c="http://www.springframework.org/schema/c"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="soundsystem.SgtPeppers" />
        
  <bean id="cdPlayer" class="soundsystem.CDPlayer"
        c:cd-ref="compactDisc" />

</beans>
```
Note:解释
![](/home/thinking/Pictures/c-ref.png)
```
这里的cd是构造函数的参数名,这样非常不好.假如有人改了参数名,岂不是gg了.
```
3. c-命名空间(使用参数的引用)

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:c="http://www.springframework.org/schema/c"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="soundsystem.SgtPeppers" />
        
  <bean id="cdPlayer" class="soundsystem.CDPlayer"
        c:_0-ref="compactDisc" /> //第0个位置的参数

</beans>
```
####### 将字面量注入到构造器中(常量,比如:String或数字)
1. contructor-arg元素(推荐)

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="org.thinking.xmlconfig.soundsystem.BlankDisc">
    <constructor-arg value="Little Luck" />
    <constructor-arg value="Tian" />
  </bean>
</beans>
```

2. c-命名空间

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:c="http://www.springframework.org/schema/c"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="org.thinking.xmlconfig.soundsystem.SgtPeppers" >
  	c:_title="Little Luck" //去掉了-ref后缀
    c:_artist="Tian"
  />

</beans>
```

3. c-命名空间(使用参数的引用)

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:c="http://www.springframework.org/schema/c"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="org.thinking.xmlconfig.soundsystem.BlankDisc"
        c:_0="Little Luck"
        c:_1="Tian" />
        
  <bean id="cdPlayer" class="org.thinking.xmlconfig.soundsystem.CDPlayer"
        c:_-ref="compactDisc" />

</beans>
```

####### 装配集合
1. 装配List<字面量>引用

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="org.thinking.xmlconfig.soundsystem.collections.BlankDisc">
    <constructor-arg value="Little Luck" />
    <constructor-arg value="Tian" />
    <constructor-arg>
      <list> //<set>
        <value>String_1</value>
        <value>String_2</value>
        <value>String_3</value>
      </list> //</set>
    </constructor-arg>
  </bean>
        
  <bean id="cdPlayer" class="org.thinking.xmlconfig.soundsystem.CDPlayer">
    <constructor-arg ref="compactDisc" />
  </bean>

</beans>
```
2. 装配List<Bean>引用

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="discography" class="org.thinking.xmlconfig.soundsystem.collections.Discography">
    <constructor-arg value="TripleP" />
    <constructor-arg>
      <list>
        <ref bean="bean_1" />
        <ref bean="bean_2" />
        <ref bean="bean_3" />
      </list>
    </constructor-arg>
  </bean>
</beans>
```

##### 2.4.4  设置属性
```
XML除了利用构造函数注入,还可以使用属性的Setter方法.
```
####### 引用Bean注入到属性中
1. 利用property

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="org.thinking.xmlconfig.soundsystem.BlankDisc">
    <constructor-arg value="Little Luck" />
    <constructor-arg value="Tian" />
  </bean>
        
  <bean id="cdPlayer" class="org.thinking.xmlconfig.soundsystem.properties.CDPlayer">
    <property name="compactDisc"(属性名) ref="compactDisc" />
  </bean>

</beans>
```
2. 利用p-命名空间

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc" class="org.thinking.xmlconfig.soundsystem.BlankDisc">
    <constructor-arg value="Little Luck" />
    <constructor-arg value="Tian" />
  </bean>
        
  <bean id="cdPlayer" class="org.thinking.xmlconfig.soundsystem.properties.CDPlayer">
    <p:compactDisc-ref="compactDisc" />
  </bean>
</beans>
```
Note:
![](/home/thinking/Pictures/p-ref.png)

###### 将字面量注入到属性中
1. 利用property

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc"
        class="soundsystem.properties.BlankDisc">
    <property name="title" value="Little Luck" />
    <property name="artist" value="Tian" />
    <property name="tracks">
      <list>
        <value>String_1</value>
        <value>String_2</value>
        <value>String_3</value>
      </list>
    </property>
  </bean>
        
  <bean id="cdPlayer"
        class="soundsystem.properties.CDPlayer"
        p:compactDisc-ref="compactDisc" />

</beans>

```
2. 利用p-命名空间

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:p="http://www.springframework.org/schema/p"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="compactDisc"
        class="org.thinking.xmlconfig.soundsystem.collections.BlankDisc"
        p:title="Little Luck"
        p:artist="Tian">
    <property name="tracks">
      <list>
        <value>String_1</value>
        <value>String_2</value>
        <value>String_3</value>
      </list>
    </property>
  </bean>
        
  <bean id="cdPlayer"
        class="soundsystem.properties.CDPlayer"
        p:compactDisc-ref="compactDisc" />

</beans>
```
3. 利用util:list来装配集合

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:p="http://www.springframework.org/schema/p"
  xmlns:util="http://www.springframework.org/schema/util"
  xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/util 
    http://www.springframework.org/schema/util/spring-util.xsd">

  <bean id="compactDisc"
        class="org.thinking.xmlconfig.soundsystem.properties.BlankDisc"
        p:title="Little Luck"
        p:artist="Tian"
        p:tracks-ref="trackList" />

  <util:list id="trackList">  
    <value>String_1</value>
    <value>String_2</value>
    <value>String_3</value>
  </util:list>

  <bean id="cdPlayer"
        class="soundsystem.properties.CDPlayer"
        p:compactDisc-ref="compactDisc" />

</beans>
```

### 2.5 导入和混合配置







