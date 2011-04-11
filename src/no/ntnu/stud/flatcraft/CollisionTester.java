package no.ntnu.stud.flatcraft;

import no.ntnu.stud.flatcraft.quadtree.QuadTree;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class CollisionTester {
	public static void main(String[] args) throws SlickException{
		System.out.println("Test");
	
	Vector2f right = new Vector2f(0f,2f);
	right.normalise();
	Rectangle rect = new Rectangle(10,10,20,20);
	Rectangle rect2 = new Rectangle(15,15,20,20);
	
	Line[] lines = Hack.getLines(rect);
	Line[] lines2 = Hack.getLines(rect2);
	
	for(Line line : lines){
		for(Line line2 : lines2){
			if(Hack.intersects(line, line2)){
				System.out.println(line+""+line2);
				System.out.println(line.intersect(line2));
			}
		}
	}
	
	
	
	System.out.println(Hack.intersects(rect, rect2));
	
	}

}
