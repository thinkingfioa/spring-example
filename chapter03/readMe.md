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



















