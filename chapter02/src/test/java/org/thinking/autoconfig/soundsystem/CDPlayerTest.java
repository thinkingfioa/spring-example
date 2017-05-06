package org.thinking.autoconfig.soundsystem;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * SpringJUnit4ClassRunner在测试开始的时候自动创建Spring的应用上下文.
 * 注解@ContextConfiguration 告诉它需要CDplayerConfig中加载配置.
 * @author thinking
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CDPlayerConfig.class)
public class CDPlayerTest {
	
	@Autowired
	private CompactDisc cd;
	
	@Autowired
	private MediaPlayer player;
	
	@Test
	public void cdShouldNotBeNull() {
		Assert.assertNotNull(cd);
		System.out.println("lwl");
	}
	
	@Test
	public void play() {
		player.play();
		//Assert.assertSame(expected, actual);
	}
}
