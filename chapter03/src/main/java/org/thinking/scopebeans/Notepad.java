package org.thinking.scopebeans;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** 
 * @author thinkingfioa
 * @Email thinking_fioa@163.com
 * @version May 21, 2017 11:01:05 AM
 */
@Component
//或者 @Scope("prototype")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) //每次都会创建一个新的bean
public class Notepad {
  // the details of this class are inconsequential to this example
}
