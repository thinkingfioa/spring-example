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

##### 5.1.3 Spittr应用简介
Spittr应用是一个简单微博。有两个基本的领域概念:Spitter(应用的用户)和Spittle(用户发布的简短状态更新)

### 5.2 编写基本的控制器
在方法上加上注解:@RequestMapping注解的类，就是控制器。
下面是一个处理对"/"的请求，并渲染应用的首页

```java
@Controller //声明为一个控制器
@RequestMapping("/")
public class HomeController {

    // 处理对"/"的GET请求
    @RequestMapping(method = GET)
    public String home(Model model){
        return "home"; //视图名为hoem
    }
}
```
1. 注解：@Controller用来声明控制器,组件会扫描到HomeController，并将其声明为Spring应用上下文中的一个bean.
2. 这里如果使用@Component注解也是一样的效果。但是表达性略差。
3. 方法: home(...)返回逻辑的视图名称。视图解析器会将其解析为:"/WEB-INF/views/home.jsp"

主页: home.jsp也可以参考chapter05中的home.jsp

##### 5.2.1 测试控制器（编写测试用例未通过）
```java
public class HomeControllerTest {
    @Test
    public void testHomePage() throws Exception {
        HomeController homeController = new HomeController();
        MockMvc mockMvc = standaloneSetup(homeController).build();
        mockMvc.perform(get("/")).andExpect(view().name("home"));
    }
}
```

##### 5.2.2 定义类级别的请求处理
1. 注解:@RequestMapping 可以修饰一个类,如果类上面又注解@RequestMapping，那么方法上的注解@ RequestMapping将会是一种补充。
2. 注解:@RequestMapping 可以接受多参数。

```java
@Controller
@RequestMapping({"/","/homepage"})
public class HomeController {
 ...
}
```

##### 5.2.3 传递模型数据到视图中






