package org.thinking.autoconfig.soundsystem;

import org.springframework.stereotype.Component;

@Component
public class SgtPeppers implements CompactDisc{
	
	private String title = "Little Luck";  
	private String artist = "Tian";
	
	@Override
	public void play(){
		System.out.println("Playing " + title + " by " + artist);
	}
}
