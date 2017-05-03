package org.thinking.springinaction.knights.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thinking.springinaction.knights.BraveKnight;
import org.thinking.springinaction.knights.Knight;
import org.thinking.springinaction.knights.Quest;
import org.thinking.springinaction.knights.SlayDragonQuest;

@Configuration
public class KnightConfig {
	@Bean
	public Knight knight(){
		return new BraveKnight(quest());
	}
	
	@Bean
	public Quest quest(){
		return new SlayDragonQuest(System.out);
	}
}
