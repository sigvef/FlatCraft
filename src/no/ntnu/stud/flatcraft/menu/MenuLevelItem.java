package no.ntnu.stud.flatcraft.menu;

import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.state.StateBasedGame;

public class MenuLevelItem implements MenuItem {

	String text;
	StateBasedGame game;
	public MenuLevelItem(String level, StateBasedGame game){
		text = level;
		this.game = game;
	}
	
	@Override
	public String getText() {
		return text;
	}

	@Override
	public void actionCallback() {
		Main.LEVEL = text;
		game.enterState(1);
	}
	
}
