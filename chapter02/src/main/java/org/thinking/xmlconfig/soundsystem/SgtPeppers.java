package org.thinking.xmlconfig.soundsystem;

public class SgtPeppers implements CompactDisc{
	
	private String title = "Little Luck";  
	private String artist = "Tian";
	
	@Override
	public void play(){
		System.out.println("Playing " + title + " by " + artist);
	}
}
