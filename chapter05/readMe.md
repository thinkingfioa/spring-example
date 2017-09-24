# 第5章 构建Spring Web应用程序

### 5.1 Spring MVC起步
##### 5.1.1 跟踪Spring MVC的请求
![](https://github.com/thinkingfioa/spring-example/tree/master/chapter05/src/main/resources/5.1)

1. 第一站：DispatcherServlet(前端控制器)任务是将请求发送给Spring MVC控制器，在发送控制器之前，需要查询一个或多个处理器映射。
2. 第二站：控制器会根据用户传入数据，进行逻辑处理，并产生对应的回复数据(模型)和逻辑视图名.
3. 第三站：根据逻辑视图名，匹配一个特定的视图实现。
4. 第四站：视图将使用模型数据渲染输出，这个输出通过相应的对象传递给客户端。

##### 5.1.2 搭建Spring MVC
**配置DispatcherServlet**： 负责将请求路由到其他的组件之中.

```
传统的方式：像DispatcherServlet这样的Servlet会配置在web.xml文件中，然后放到对应的war包中。
推荐方式：使用Java将DispatcherServlet配置到Servlet中。
```
代码:

```java
public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
    //加载ContextLoaderListener的Spring上下文
    //包含后端的中间层和数据层组件的bean
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    //加载DispatcherServer应用上下文
    //包含web组件的bean
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }
    
    //会将一个或多个路径映射到DispatcherServlet上。本例中，它的映射是"/"
    //表示它是应用默认的Servlet。处理进入应用的所有请求。
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
```
说明:

1. 继承AbstractAnnotationConfigDispatcherServletInitializer的任意类都会自动配置DispatcherServlet和Spring应用上下文，Spring应用上下文位于应用程序的Servlet上下文中。
2. 留意覆写的方法。

**两个应用上下文之间的故事**
1. 方法：getServletConfigClasses()用于加载Servlet上下文.利用的是Java配置，WebConfig.class
2. 方法：getRootConfigClasses()用于加载Spring应用上下文.利用的是Java配置，RootConfig.class

**启用Spring MVC**
使用@EnableWebMVC注解的类WebConfig.class来启动Spring MVC

```java
@Configuration
@EnableWebMvc //启用Spring MVC
@ComponentScan("org.thinking.spittr.web") //启动组件扫描
public class WebConfig extends WebMvcConfigurerAdapter {
    //配置JSP视图解析器.
    //查找JSP文件，会在视图名称上加上特定的前缀和后缀
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    //配置静态资源的处理。对静态资源的请求转发到Servlet容器默认的Servlet上。
    //而不是使用DispatcherServlet本身
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        super.addResourceHandlers(registry);
    }
}
```









