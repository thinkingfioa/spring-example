package org.thinking.scopebeans.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/** 
 * @author thinkingfioa
 * @Email thinking_fioa@163.com
 * @version May 21, 2017 11:50:01 AM
 */
@Configuration
@ComponentScan(excludeFilters={@Filter(type=FilterType.ANNOTATION, value=Configuration.class)})
public class ComponentScannedConfig {

}
