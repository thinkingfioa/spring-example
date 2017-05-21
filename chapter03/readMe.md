# 第3章 高级装配

### 3.1 环境与profile
```
不同的运行环境,可能需要不同的Bean.比如: 开发需要嵌入式数据库, 测试环境需要JDBC数据库, 生产环境需要c3p0数据库.
profile允许根据环境不同,获取不同的bean.
```

##### 3.1.1 @profile注解配置 bean
```
@profile指定创建哪个bean
```

####### JavaConfig
```java
@Configuration
public class DataSourceConfig {
	@Bean(destroyMethod = "shutdown")
	  @Profile("dev")
	  public DataSource embeddedDataSource() {
	    return new EmbeddedDatabaseBuilder()
	        .setType(EmbeddedDatabaseType.H2)
	        .addScript("classpath:schema.sql")
	        .addScript("classpath:test-data.sql")
	        .build();
	  }
	
	@Bean
	  @Profile("on-line")
	  public DataSource jndiDataSource() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
	    jndiObjectFactoryBean.setJndiName("jdbc/myDS");
	    jndiObjectFactoryBean.setResourceRef(true);
	    jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
	    return (DataSource) jndiObjectFactoryBean.getObject();
	  }
}
```

####### XML
```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:jdbc="http://www.springframework.org/schema/jdbc"
  xmlns:jee="http://www.springframework.org/schema/jee" xmlns:p="http://www.springframework.org/schema/p"
  xsi:schemaLocation="
    http://www.springframework.org/schema/jee
    http://www.springframework.org/schema/jee/spring-jee.xsd
    http://www.springframework.org/schema/jdbc
    http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">

  <beans profile="dev">
    <jdbc:embedded-database id="dataSource" type="H2">
      <jdbc:script location="classpath:schema.sql" />
      <jdbc:script location="classpath:test-data.sql" />
    </jdbc:embedded-database>
  </beans>
  
  <beans profile="on-line">
    <jee:jndi-lookup id="dataSource"
      lazy-init="true"
      jndi-name="jdbc/myDatabase"
      resource-ref="true"
      proxy-interface="javax.sql.DataSource" />
  </beans>
</beans>
```

##### 3.1.2 激活profile
```
若设置了spring.profiles.active属性,则用该属性;
若没有,则用spring.profiles.default;
若两个都没有,则@profile注解声明的bean,都不会被创建.
```
```
profiles可以同时设置多个profile;激活多个profile
```
多种方式设置spring.profiles.active和spring.profiles.default
```
1.作为DispatcherServlet初始化参数
2.作为Web应用上下文参数;
3.作为JNDI条目
4.作为环境变量
5.作为JVM的系统属性
6.在集成测试类上,使用@ActiveProfiles注解设置
```
推荐在web.xml设置默认
```xml
<context-param>
	<param-name>spring.profiles.default</param-name>
    <param-value>dev</param-value>
</context-param>
```
使用profile进行测试
```Java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataSourceConfig.class)
@ActiveProfiles("dev")
public class PersistenceTest{
	//...
}
```

### 3.2 条件化的bean
```
使用@Conditional注解,来判断当某个条件满足,才创建bean.
```
```java
@Configuration
public class MagicConfig {

  @Bean
  @Conditional(MagicExistsCondition.class)
  public MagicBean magicBean() {
    return new MagicBean();
  }
  
}

public class MagicExistsCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    Environment env = context.getEnvironment();
    return env.containsProperty("magic");
  }
  
}

```

### 3.3 处理自动装配的歧义性
```
第2章中, 自动装配时,如果发现多个bean都满足条件.
spring会抛出NoUniqueBeanDefinitionException异常.
```

##### 3.3.1 标示首选的bean(@Primary)
```
告诉Spring若在自动装配上发生歧义,请使用@Primary
```
```
@Primary能够与@Component组合用在组件扫描的bean上;也与@Bean组合用在Java配置bean的声明中.
```
自动配置
```java
@Component
@Primary
public class IceCream implements Dessert {...}
```
JavaConfig
```java
@Bean
@Primary
public Dessert iceCream {
	return new IceCream();
}
```
XML配置
```xml
<bean id="iceCream" class "org.thinking.desserteater.IceCream"
   primary="true"/>
```

##### 3.3.2 限定自动装配的bean(注解:@Qualifier)
```
@Qualifier注解在注入时候指定想要注入进去的bean.如:@Qualifier(bean的ID)
```

创建自定义的限定符(推荐)
```
在创建bean时候加上注解:@Qualifier(...)
```
```java
@Component
@Qualifier("cold")
public class IceCream implements Dessert { ... }

@Autowired // 或者是@Bean
@Qualifier("cold")
public void setDessert(Dessert dessert) {
	this.dessert = dessert;
}
```

使用自定义的限定符注解
```
当一个@Qualifier注解无法区别出其中的两个bean时,且java不允许同一个条目中出现多个相同的注解.
所以必须采用自定义限定符注解,将相似的两个bean区别开来.
```
```java
//创建一个Code注解
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,
			ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Cold {}

//创建一个Creamy注解
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,
			ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Creamy {}

//创建一个Fruity注解
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,
			ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Creamy {}
```
```java
@Component
@Cold
@Creamy
public class IceCream implements Dessert { ... }

@Component
@Cold
@Fruity
public class Popsicle implements Dessert { ... }

@Autowired
@Cold
@Creamy
public void setDessert(Dessert dessert) {
	this.dessert = dessert;
}
```

### 3.4 bean的作用域
```
在默认情况下,Spring应用上下文中所有的bean都是单例的,也就是说一个bean不管被注入到其他的bean中几次,
都是同一个实例.
```
```
Spring定义了多种作用域:
1. 单例: 在整个应用中,只创建bean的一个实例.
2. 原型: 每次注入或通过Spring应用上下文获取时候,都会创建一个新的bean实例.
3. 会话: 在Web应用中,为每个会话创建一个bean实例.
4. 请求: 在Web应用中,为每个请求创建一个bean实例.
```

##### 使用注解@Scope为bean声明作用域
```java
@Component
// 或者是 @Scope("prototype")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) 
public class Notepad {...}

@Bean
// 或者是 @Scope("prototype")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) 
public Notepad notePad(){
	return new Notepad();
}

<bean id="notepad" class="com.myapp.NotePad" scope="prototype" />
```

##### 3.4.1 使用会话和请求作用域
```
在一些Web应用中,在会话和请求范围内共享bean是非常有意义的.
```
举例
```java
@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION,
	proxyMode=ScopedProxyMode.INTERFACES)
public interface ShoppingCart { ... }

@Component
public class StoreService {
	ShoppingCart shoppingCart;
	
	@Autowired
	public void setShoppingCard(ShoppingCart shoppingCart){
		this.shoppingCart = shoppingCart;
	}
}
```
解释
```
1. value=WebApplicationContext.SCOPE_SESSION表明spring为每个session创建一个bean.
2. proxyMode=ScopedProxyMode.INTERFACES由于Spring在创建StoreService时候,还无法有session,
3. 所以提供一个代理,且需要保证ShoppingCart是一个接口.
如果ShoppingCart是一个类,同时希望作用域是session,需要使用:proxyMode=ScopedProxyMode.TARGET_CLASS)
```
解释作用域代理
```
当一个对象创建时,它依赖的另一个对象目前还不能创建,就需要使用作用域代理.
如果另一个对象是借口,使用proxyMode=ScopedProxyMode.INTERFACES.
如果另一个对象是类,需要使用CGLib生成基于类的代理:proxyMode=ScopedProxyMode.TARGET_CLASS
```
![](https://github.com/thinkingfioa/spring-example/blob/master/chapter03/src/main/resources/3-1.png)

##### 3.4.2 在XML中声明作用域代理
```
xml中不能使用注解@Scope,需要用<aop:scoped-proxy/>来代替
```
```xml
<bean id = "cart"
	class="org.thinking.ShoppingCart"
    scope="session" >
    <aop:scoped-proxy proxy-target-class="false"/>
```
Note
```
xml默认情况下使用的是CGLib创建目标类的代理.如果将proxy-target-class="false"则表示生成基于接口的代理"
```

### 3.5 运行时值注入
```
避免硬编码值,采用下面两种方法,运行时才确定.
1. 属性占位符
2. Spring表达式语言(SpEL)
```

##### 3.5.1 注入外部的值
```
使用Spring的Environment+注解@PropertySource,来从配置文件中读取值
```
```java
@Configuration
@PropertySource("classpath:/org/thinking/app.properties")
public class EnvironmentConfig {

  @Autowired
  Environment env;
  
  @Bean
  public BlankDisc blankDisc() {
    return new BlankDisc(
        env.getProperty("disc.title"),
        env.getProperty("disc.artist"));
  }
}
```
Note:
```
注解@PropertySource读取配置文件,填入Spring的Environment
```

深入学习Spring的Environment
```
getProperty(...)方法如果没有获取到指定的属性,返回null;
getRequiredProperty(String key)方法要求必须有key, 否则抛出IllegalStateException异常.
```
```
Environment几种获取配置方法介绍:
1. String getProperty(String key);
2. String getProperty(String key, String defaultValue);//提供缺省值
3. T getProperty(String key, Class<T> type);//返回指定的类型
4. T getProperty(String key, Class<T> type, T defaultValue)//
5. String getRequiredProperty(String key);//要求必须定义了key
6. boolean containsProperty(String key);//检查配置文件是否定义了key.
```

解析属性占位符
```
为了使用属性占位符,必须使用类PropertySourcesPlaceholderConfigurer
1. JavaConfig方式
@Bean
public static PropertySourcesPlaceholderConfigurer placeholderCOnfigurer(){
	return new PropertySourcesPlaceholderConfigurer();
}
2. XML方式
<context:property-placeholder />
```
```xml
<bean id = "sgtPeppers" calss = "org.thinking.externals.BlankDisc"
	c:_tile="${disc.title}"
    c:_artist="${disc.artist}" />
```
```java
public class BlankDisc{
	public BlankDisc (@Value("${disc.title}") String title, @Value("${disc.artist}")String artist)
}
```

##### 3.5.2 使用Spring表达式语言进行装配(SpEL)
```
试图延迟到运行时才确定填入的值.
```
```
SpEL表达式: "#{...}",和属性占位符"${...}"很相似.
```

表示字面值
```
(double, int,string ,boolean,科学计数法)
#{3.14}, #{'hello'}, #{false}, #{9.84E4}(也就是98400
```

引用bean,属性,方法
```
#{sgtPeppers}, #{sgtPeppers.title}, #{sgtPeppers.getObject().getObject()}
```
```
SpEL允许调用bean的方法且可以多次执行,为了避免某次得到null,可以使用"?";
比如: #{artistSelector.selectArtist()?.toUpperCase()}.
如果方法:artistSelector.selectArtist()返回null,将不会调用toUpperCase()方法了.
```

表达式中使用类型
```
使用类的静态方法或常量,需要使用关键运算符: T().
```
```
#{T(java.lang.Math).PI}//调用Math中静态常量
#{T(java.lang.Math).random()}//调用类Math的静态方法
```

SpEL运算符
```
SpEL支持多种运算符,基本的都支持.
```
![](https://github.com/thinkingfioa/spring-example/blob/master/chapter03/src/main/resources/3-21.png)
![](https://github.com/thinkingfioa/spring-example/blob/master/chapter03/src/main/resources/3-22.png)

计算正则表达式
```
利用正则表达式,判断是否相匹配,匹配是true,不匹配是false
#{admin.email matches '[a-zA-z...]'}
```

计算集合
```
获取列表中第2个值: #{thinking.songs[2].title}
```
```
查询运算符(.?[]),对集合进行过滤,得到满足条件的集合
如 : #{thinking.songs.?[artist eq 'Tian']}.//从集合songs歌曲中,挑出Tian唱的歌
如 : #{thinking.songs.^[artist eq 'Tian']}.//从集合songs歌曲中,查询第一个匹配的
如 : #{thinking.songs.$[artist eq 'Tian']}.//从集合songs歌曲中,查询最后一个匹配的
如 : #{thinking.songs.![artist}.//将songs歌曲中artist放到List中.
```

SpEL使用
```java
public BlankDisc(@Value("#{'The Litter Cuter'}") String title, @Value("#{'Tian'}") String artist) {
	this.title = title;
    this.artist = artist;
}
```
```xml
<bean id = "sgtPeppers" calss = "org.thinking.externals.BlankDisc"
	c:_tile="#{'The Litter Cuter'}"
    c:_artist="#{'Tian'}" />
```
