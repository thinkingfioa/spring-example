package org.thinking.autoconfig.soundsystem;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ComponentScan默认会扫描配置类相同的包.
 * 因此Spring会扫描org.thinking.autoconfig.soundsystem这个包和这个包
 * 下的所有子包,查找带有@Component注解的类.
 * @author thinking
 *
 */
@Configuration
@ComponentScan
public class CDPlayerConfig {

}
