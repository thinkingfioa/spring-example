package org.thinking.autoconfig.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer implements MediaPlayer{
	private CompactDisc cd;
	
	//@Autowired可以用户任何方法上,Spring会尽最大能力不装配.
	//创建CDPlayer bean时候,自动传入CompactDisc类型的bean;
	@Autowired
	public CDPlayer(CompactDisc cd){
		this.cd = cd;
	}
	
	@Override
	public void play(){
		cd.play();
	}
}
