package no.ntnu.stud.flatcraft.messagesystem;

import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.Color;
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

	// lots of hardcodedness in here :)

	public void calculateSize() {
		width = Main.FONT_BOLD.getWidth(text);
		height = Main.FONT_BOLD.getHeight(text);

		width *= 4 / Main.GULOL; // hard-coded constant to scale down the large
									// text, because I don't know how to resize
									// fonts other than making lots of them.
		height *= 4 / Main.GULOL;

		width += Main.GULOL * 4;
		height += Main.GULOL * 4;

		x = Main.SCREEN_W * 0.5f - width * 0.5f + Main.GULOL * 2;
		y = Main.SCREEN_H * 0.5f - height * 0.5f + Main.GULOL * 2;
	}

	public void render(Graphics g) {

		g.pushTransform();
		g.setColor(new Color(0, 0, 0, 0.8f));
		g.fillRect(x, y, width, height);
		g.setColor(Color.gray);
		g.setFont(Main.FONT_BOLD);
		g.translate(x + Main.GULOL * 2, y + Main.GULOL * 2);
		g.scale(4 / Main.GULOL, 4 / Main.GULOL);
		g.drawString(text, 0, 0);
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
