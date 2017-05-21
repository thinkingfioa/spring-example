package org.thinking.scopebeans.sessions;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/** 
 * @author thinkingfioa
 * @Email thinking_fioa@163.com
 * @version May 21, 2017 11:11:55 AM
 */
@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION,
	proxyMode=ScopedProxyMode.INTERFACES)
//如果ShoppingCart是一个类,需要改成ScopedProxyMode.TARGET_CLASS
public interface ShoppingCart {

}
