package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import functionality.People;
import functionality.Profile;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
	TextArea bio, localIp;

	@FXML
	TabPane holder;

	ObservableList<String> items = FXCollections.observableArrayList();

	int portNum; //newJ

	People knownUsers;

	Profile localUser;

	private ServerSocket accepter;

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

		portNum = 8888;
		knownUsers = new People();
		localUser = new Profile();

		try {
			accepter = new ServerSocket(portNum);
			listen();

		} catch (IOException e){

			badNews(e.getMessage());
			e.printStackTrace();
		}

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
			/*ProfileList a = new ProfileList();
			ProfileList b = new ProfileList();
			Profile p = new Profile(name.getText(), "123", "single", bio.getText(),null, a);
			Profile g = new Profile("george", null, "single", bio.getText(),null, b);
			a.addProfile(g);
			a.addProfile(p);
			ProfileList x = a.filteredList("123");
			System.out.println(x.getProfile(0).getName());*/
		} else {
			edit.setText("Done");
			name.setEditable(true);
			bio.setEditable(true);
		}

		localUser.setBio(bio.getText());
		localUser.setIp(localIp.getText());
		//localUser.setList(list);
		localUser.setName(name.getText());
		//localUser.setStat(newStat);

		knownUsers.addUser(localUser);
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

	void badNews(String what) { //Dr. Ferrer 352 sockDemo
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(what);
		badNum.show();
	}

	private void dealWithInput(String info) {
		People newUsers = new People();
		People temp = new People(info); //needs reconstructed people implemented
		for (String s : temp.getKeys()) {
			if (!knownUsers.getKeys().contains(s)) {
				for (String s1 : knownUsers.getKeys()) {
					sendTo(s1, new People(temp.getProfile(s1)).toString()); //needs reconstructed people implemented
					newUsers.addUser(temp.getProfile(s1));
				}
			}
		}
		for (String s2 : newUsers.getKeys()) {
			knownUsers.addUser(newUsers.getProfile(s2));
		}
	}

	private void listen() throws IOException {
		new Thread(() -> {
			try {
				while (true) {
					Socket s = accepter.accept();
					newClient(s);
				}
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}).start();
	}

	private void newClient(Socket s) throws IOException { //Dr. Ferrer sockDemo
		new Thread(() -> {
			try {
				BufferedReader responses =
	            		new BufferedReader(new InputStreamReader(s.getInputStream()));
	            StringBuilder sb = new StringBuilder();
	            while (!responses.ready()){}
	            while (responses.ready()) {
	                sb.append(responses.readLine() + '\n');
	            }
	            dealWithInput(sb.toString());
	            s.close();

			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}).start();
	}

	private void sendTo(String host, String message) { //Dr. Ferrer sockDemo
		new Thread(() -> {
			try {
				Socket target = new Socket(host, portNum);
				send(target, message);
				target.close();
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}).start();
	}

	private void send(Socket target, String message) throws IOException { //Dr. Ferrer sockDemo
		PrintWriter sockout = new PrintWriter(target.getOutputStream());
		sockout.println(message);
		sockout.flush();
	}

	public void sendKnownUsers() {
		sendTo(IPaddress.getText(), knownUsers.toString());
	}
}
