package functionality;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chat {
	HashMap<String, ObservableList<String>> activeChats;
	Profile currentChatter;
	
	public Chat() {
		activeChats = new HashMap<String, ObservableList<String>>();
	}
	
	public void setChatter(Profile person) {
		currentChatter = person;
	}
	
	public Profile getCurrentChatter() {
		return currentChatter;
	}
	
	public void addChatter(String name) {
		ObservableList<String> c = FXCollections.observableArrayList();
		activeChats.put(name, c);
		System.out.println("!!!!!" + activeChats.keySet());
	}
	
	public ObservableList<String> getChatWith(String name) {
		return activeChats.get(name);
	}

	public void updateChat(String name, String message) {
		System.out.println(name+ "?");
		ObservableList<String> singleChat = activeChats.get(name);
		System.out.println(singleChat);
		singleChat.add(message);
		activeChats.replace(name, singleChat);
	}

	public ObservableList<String> getChatList() {
		ObservableList<String> c = FXCollections.observableArrayList();
		for (String each : activeChats.keySet()) {
			c.add(each);
		}
		return c;
	}
}
