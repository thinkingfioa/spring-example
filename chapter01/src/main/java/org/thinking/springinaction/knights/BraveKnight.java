package org.thinking.springinaction.knights;

public class BraveKnight implements Knight{

	  private Quest quest;

	  public BraveKnight(Quest quest) { //松耦合
	    this.quest = quest;
	  }

	  public void embarkOnQuest() {
	    quest.embark();
	  }

}
