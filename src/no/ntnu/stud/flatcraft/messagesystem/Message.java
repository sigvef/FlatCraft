package no.ntnu.stud.flatcraft.messagesystem;

import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Message {

	int button = Input.KEY_SPACE;
	String indicator = "input-indicator.png";

	float easetime = 0.5f;
	float fadetime = 0.3f;
	float easeoffset = 10;

	float x = 0;
	float y = 0;
	float width = 0;
	float height = 0;

	String text = "Text is not set.";

	int timeout = 1000;

	public Message(String _text) {
		text = _text;
		calculateSize();
	}

	public Message(String _text, int _timeout) {
		text = _text;
		timeout = _timeout;
		calculateSize();
	}

	public void calculateSize() {
		width = 20 * Main.GULOL;
		height = 10 * Main.GULOL;
		x = Main.SCREEN_W * 0.5f - width * 0.5f;
		y = Main.SCREEN_H * 0.5f - height * 0.5f;
	}

	public void render(Graphics g) {

		g.pushTransform();
		g.setColor(new Color(1, 1, 1, 0.8f));
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawString(text, x, y);
		g.popTransform();
	}

	public void update(int dt) {
		timeout -= dt;
		if (Main.KEYDOWN[button]) {
			timeout = 0;
		}
	}

	public boolean isDone() {
		return timeout <= 0;
	}
}
