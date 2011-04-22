//package no.ntnu.stud.flatcraft.levels.generator;
//
//import java.util.ArrayList;
//
//import no.ntnu.stud.flatcraft.Main;
//import no.ntnu.stud.flatcraft.entities.GameEntity;
//import no.ntnu.stud.flatcraft.quadtree.QuadTree;
//
//import org.newdawn.slick.Color;
//import org.newdawn.slick.GameContainer;
//import org.newdawn.slick.Graphics;
//import org.newdawn.slick.SlickException;
//import org.newdawn.slick.geom.Rectangle;
//import org.newdawn.slick.geom.Vector2f;
//import org.newdawn.slick.state.StateBasedGame;
//
//public class GeneratorWorld {
//	public QuadTree terrain;
//	Creator creator;
//	float viewportzoom;
//	Vector2f viewportgoal;
//	public Rectangle viewport;
//	
//	public GeneratorWorld() throws SlickException{
//		terrain = new QuadTree(0,0,320*Main.GU,8,null); //hardcoded level width: a square 10x the with of the screen.
//		viewport = new Rectangle(0,0,Main.SCREEN_W,Main.SCREEN_H);
//		viewportgoal =  new Vector2f(viewport.getX(),viewport.getY());
//		viewportzoom = 1;
//		creator = new Creator(this);
//	}
//	
//	//reset() - resets the map, calls reset on all the things it controls.
//	public void reset(){
//		viewport = new Rectangle(0,0,Main.SCREEN_W,Main.SCREEN_H);
//		
//		viewportzoom = 1;
//		//reload terrain also TODO
//	}
//	
//	public void setViewportPosition(Vector2f position){
//		viewport.setLocation(position);
//	}
//	public void setViewportPositionGoal(Vector2f position){
//		viewportgoal = position;
//	}
//	
//	public Vector2f getViewportPosition(){
//		return new Vector2f(viewport.getX(),viewport.getY());
//	}
//	
//	//removeEntities() - removes all the GameEntities it controls.
//	public void removeEntities(){
//		
//	}
//	
//	//findEntities(Vector2f, float, ArrayList<GameEntity>, int) - finds and adds
//	//entities close to position to the Entities list.Returns the the number of
//	//entities found and added to the Entities list.
//	public int findEntities(Vector2f position, float radius, ArrayList<GameEntity> Entities, int type){
//		return 0;
//	}
//	
//	//interSectCharacter(Rectangle, GameEntity) - returns a Character that 
//	//collides with the boundingBox. Does not check for collisions against
//	//notThis. returns null if there is no collision.
//	Character intersectCharacter(Rectangle boundingBox, GameEntity notThis){
//		return null;
//	}
//	
//	//closestCharacter(Vector2f, GameEntity) - returns the Character closest
//	//to the point. Does not check notThis. Returns null if no Character is
//	//close enough.
//	Character closestCharacter(Vector2f point, GameEntity notThis){
//		return null;
//	}
//	
//	
//	//update(GameContainer, StateBasedGame,int) - updates itself and all it's
//	//children one tick.
//	public void update(GameContainer container, StateBasedGame game, int delta){
//		creator.update(container, game, delta);
//		
//		if(viewportgoal.getX() != viewport.getX() || viewportgoal.getY() != viewport.getY()){
//			viewport.setLocation(viewport.getX()-(viewport.getX()-viewportgoal.getX())*0.5f,
//					viewport.getY()-(viewport.getY()-viewportgoal.getY())*0.5f);
//		}
//	}
//	
//	public void render(Graphics g){
//		terrain.render(g, viewport);
//		creator.render(g);
//	}
//}
