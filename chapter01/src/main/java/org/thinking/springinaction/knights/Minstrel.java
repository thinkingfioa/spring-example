package org.thinking.springinaction.knights;

import java.io.PrintStream;

public class Minstrel {

	  private PrintStream stream;
	  
	  public Minstrel(PrintStream stream) { // 松耦合,依赖注入(DI)
	    this.stream = stream;
	  }

	  public void singBeforeQuest() {
	    stream.println("Fa la la, the knight is so brave!");
	  }

	  public void singAfterQuest() {
	    stream.println("Tee hee hee, the brave knight " +
	    		"did embark on a quest!");
	  }

	}