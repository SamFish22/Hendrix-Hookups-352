package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller { //add profile picture
	@FXML
	Button edit, send, chat, go, getConnections;
	
	@FXML
	TextField IPaddress, chatText, name;
		
	@FXML
	Tab homepage, profileView, chatroom;
	
	@FXML
	ListView<String> chatMessages, peopleConnected; //change peopleConnected
	
	@FXML
	TextArea bio;
	
	@FXML
	TabPane holder;
	
	ObservableList<String> items = FXCollections.observableArrayList();

	public void initialize() {
		if (isFirstTime()) {
			profileView.setDisable(true);
			chatroom.setDisable(true);
			edit.setText("Done");
		} else {
			//retrieve name and bio
		}
		IPaddress.setVisible(false);
		go.setVisible(false);
	}

	private boolean isFirstTime() {
		// TODO check for certain file extension
		return true;
	}
	
	public void pressEdit() {
		if (edit.getText() == "Done") {
			edit.setText("Edit");
			saveProfile();
			profileView.setDisable(false);
			name.setEditable(false);
			bio.setEditable(false);
		} else {
			edit.setText("Done");
			name.setEditable(true);
			bio.setEditable(true);
		}
	}
	
	public void saveProfile() {
		if (isFirstTime()) {
			//create file
		} else {
			//alter file
		}
	}
	
	public void pressGetConnections() {
		IPaddress.setVisible(true);
		go.setVisible(true);
	}
	
	public void pressChat() {
		//get selected person object
		holder.getSelectionModel().select(chatroom);
		chatroom.setDisable(false);
	}
	
	public void sendMyMessage() {
		String text = "You: " + chatText.getText();
		items.add(text);
		chatMessages.setItems(items);
		chatText.setText("");
	}
}
