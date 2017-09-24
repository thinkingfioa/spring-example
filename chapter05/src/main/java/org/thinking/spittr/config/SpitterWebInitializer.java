package org.thinking.spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.thinking.spittr.web.WebConfig;

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
