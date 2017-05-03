package org.thinking.springinaction.knights.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.thinking.springinaction.knights.BraveKnight;
import org.thinking.springinaction.knights.Quest;

public class BraveKnightTest {

	@Test
	public void knightShouldEmbarkOnQuest() {
		Quest mockQuest = Mockito.mock(Quest.class);
		BraveKnight knight = new BraveKnight(mockQuest);
		knight.embarkOnQuest();
		Mockito.verify(mockQuest,Mockito.times(1)).embark();
	}
}
