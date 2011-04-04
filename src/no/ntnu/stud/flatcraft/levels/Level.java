package no.ntnu.stud.flatcraft.levels;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;

import no.ntnu.stud.flatcraft.quadtree.QuadTree;

import org.newdawn.slick.geom.Vector2f;

public final class Level implements Serializable {
	private static final long serialVersionUID = -3639668302683141216L;
	
	QuadTree quadTree;
	Vector2f startPosition;
	
	public QuadTree getQuadTree() {
		return quadTree;
	}
	
	public Vector2f getStartPosition() {
		return startPosition;
	}
	
	public static Level getLevel(String levelName)
			throws IOException, ClassNotFoundException {
		ObjectInputStream in;
		Level level;
		
		in = new ObjectInputStream(Level.class.getClass().getResourceAsStream("levels/" + levelName + ".flat"));
		level = (Level) in.readObject();
		in.close();
		
		return level;
	}
}