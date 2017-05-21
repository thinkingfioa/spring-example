package org.thinking.scopebeans.test;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.thinking.scopebeans.Notepad;
import org.thinking.scopebeans.UniqueThing;

/** 
 * @author thinkingfioa
 * @Email thinking_fioa@163.com
 * @version May 21, 2017 11:50:16 AM
 */
@Configuration
public class ExplicitConfig {

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public Notepad notepad() {
    return new Notepad();
  }
  
  @Bean
  public UniqueThing unique() {
    return new UniqueThing();
  }
  
}
