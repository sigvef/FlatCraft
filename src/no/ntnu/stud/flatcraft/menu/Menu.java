package no.ntnu.stud.flatcraft.menu;

import java.util.ArrayList;

import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;


public class Menu {
	public ArrayList<MenuItem> items;
	int pointer;
	boolean uppressed;
	boolean downpressed;
	private boolean selectpressed;
	Color color;
	StateBasedGame game;

	Color selectedColor;
	
	public Menu(StateBasedGame game){
		this.game = game;
		init();
	}
	
	public Menu(ArrayList<String> levels,StateBasedGame game){
		this.game = game;
		init();
		for(String l : levels){
			items.add(new MenuLevelItem(l,game));
		}
	}
	
	private void init(){
		items = new ArrayList<MenuItem>();
		pointer = 0;
		uppressed = false;
		downpressed = false;
		selectpressed = false;
		color = new Color(255,255,255);
		selectedColor = new Color (255,0,255);	
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getSelectedColor() {
		return selectedColor;
	}
	
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
	
	public void addMenuItem(MenuItem item){
		items.add(item);
	}
	
	public void removeMenuItem(MenuItem item){
		items.remove(item);
	}
	
	public void update(int dt){
		if(!uppressed && (Main.KEYDOWN[Input.KEY_W] || Main.KEYDOWN[Input.KEY_UP] || Main.KEYDOWN[Input.KEY_A])){
			pointer--;
			uppressed = true;
		}else if(!(Main.KEYDOWN[Input.KEY_W] || Main.KEYDOWN[Input.KEY_UP] || Main.KEYDOWN[Input.KEY_A])){
			uppressed = false;
		}
		
		if(!downpressed && (Main.KEYDOWN[Input.KEY_S] || Main.KEYDOWN[Input.KEY_DOWN] || Main.KEYDOWN[Input.KEY_D])){
			pointer ++;
			downpressed = true;
		} else if(!(Main.KEYDOWN[Input.KEY_S] || Main.KEYDOWN[Input.KEY_DOWN] || Main.KEYDOWN[Input.KEY_D])){
			downpressed = false;
		}
		if(pointer > items.size()-1){
			pointer = 0;
		}
		if(pointer<0){
			pointer = items.size()-1;
		}
		

		if(!selectpressed && (Main.KEYDOWN[Input.KEY_SPACE] || Main.KEYDOWN[Input.KEY_ENTER])){
			items.get(pointer).actionCallback();
			selectpressed = true;
		}else if(!(Main.KEYDOWN[Input.KEY_SPACE] || Main.KEYDOWN[Input.KEY_ENTER])){
			selectpressed = false;
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		g.translate(0, 25*Main.GULOL);
		for(int i=0;i<items.size();i++){
			if(i==pointer){
				g.setColor(selectedColor);
			}else{
				g.setColor(color);
			}
			
			g.setFont(Main.FONT_BOLD);
			g.drawString(items.get(i).getText(), 5*Main.GULOL, (-pointer + i)*10*Main.GULOL);
		}
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}

	public int getPointer() {
		return pointer;
	}
	
}