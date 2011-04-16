package no.ntnu.stud.flatcraft.messagesystem;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public class MessageSystem {

	ArrayList<Message> messages;

	public MessageSystem() {
		messages = new ArrayList<Message>();
	}

	public void update(int dt) {
		for (Message message : messages) {
			message.update(dt);
		}
		for(int i=0;i<messages.size();){
			if(messages.get(i).isDone()){
				messages.remove(i);
			}
			else{
				i++;
			}
		}
	}

	public void render(Graphics g){
	for(Message message : messages){
		message.render(g);
	}
}

	public boolean addMessage(Message message) {
		if (!messages.contains(message)) {
			return messages.add(message);
		}
		return false;
	}
}