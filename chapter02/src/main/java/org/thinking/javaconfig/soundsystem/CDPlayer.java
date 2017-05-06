package org.thinking.javaconfig.soundsystem;

public class CDPlayer implements MediaPlayer{
	private CompactDisc cd;
	
	public CDPlayer(CompactDisc cd){
		this.cd = cd;
	}
	
	@Override
	public void play(){
		cd.play();
	}
}
