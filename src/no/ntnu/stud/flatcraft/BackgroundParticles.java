package no.ntnu.stud.flatcraft;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class BackgroundParticles {
	Vector2f[] points;

	public BackgroundParticles() {
		points = new Vector2f[50];
		for (int i = 0; i < points.length; i++) {
			points[i] = new Vector2f((float) Math.random() * Main.SCREEN_W,
					(float) Math.random() * Main.SCREEN_H);
		}
	}

	public void render(Graphics g) {
		g.pushTransform();
		g.setColor(Color.gray);
		g.setDrawMode(Graphics.MODE_ADD);
		for (Vector2f p : points) {
			g.fillOval(p.getX(), p.getY(), Main.GULOL, Main.GULOL);
		}
		g.setDrawMode(Graphics.MODE_NORMAL);
		g.popTransform();
	}

	public void update(int delta) {
		for (int i = 0; i < points.length; i++) {
			points[i].add(new Vector2f(-(float) Math.random()*Main.GULOL*0.1f , -(float) Math.random()*Main.GULOL*0.1f));
			if (points[i].getX() < 0) {
				points[i].set(Main.SCREEN_W, points[i].getY());
			}
			if (points[i].getY() < 0) {
				points[i].set(points[i].getX(), Main.SCREEN_H);
			}
		}
	}
}
