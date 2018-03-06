package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import functionality.InfoType;
import functionality.People;
import functionality.Profile;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
	TextArea bio, localIp; //inetaddress.getlocalhost.get

	@FXML
	TabPane holder;

	@FXML
	ChoiceBox<String> status;

	@FXML
	Accordion profilesFound;

	ObservableList<String> textChat = FXCollections.observableArrayList();

	static final int portNum = 8888;

	People knownUsers;

	Profile localUser;

	File file;

	private ServerSocket accepter;

	public void initialize() {
		String userHomeFolder = System.getProperty("user.home");
		file = new File(userHomeFolder, "HendrixHookups.txt");
		localUser = new Profile();
		knownUsers = new People();
		chatroom.setDisable(true);
		try {
			localIp.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (isFirstTime()) {
			profileView.setDisable(true);
			edit.setText("Done");
		} else {
			loadFile(file);
			disableFirstPage();
			setLocalUser();
			knownUsers.addUser(localUser);
		}
		ObservableList<String> options =
			    FXCollections.observableArrayList("Single", "In a relationship", "It's complicated",
						"Too blessed to be stressed", "To stressed to be blessed", "Thruple aspiring");
		status.getItems().addAll(options);

		IPaddress.setVisible(false);
		go.setVisible(false);


		try {
			accepter = new ServerSocket(portNum);
			listen();

		} catch (IOException e){

			badNews(e.getMessage());
			e.printStackTrace();
		}
	}

	private boolean isFirstTime() {
		if (file.exists()) {
			return false;
		} return true;
	}
	public void loadFile(File file) {
		try {
			FileReader fileReader = new FileReader(file.getAbsolutePath());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			try {
				String line = bufferedReader.readLine();
				if (line.equals("Hendrix-Hookups File")) {
					name.setText(bufferedReader.readLine());
					bufferedReader.readLine();
					int i = parseStatus(bufferedReader.readLine());
					status.getSelectionModel().selectFirst();;
					bio.setText(bufferedReader.readLine());
					bufferedReader.close();
					//TODO open image
				} else {
					//TODO
				}
			} catch (IOException e) {
				//TODO
			}
		} catch (FileNotFoundException e) {
			//TODO
		}
	}

	public int parseStatus(String loading) {
		String[] choices = {"Single", "In a relationship", "It's complicated",
				"Too blessed to be stressed", "To stressed to be blessed", "Thruple aspiring"};
		for (int i = 0; i < choices.length - 1; i++) {
			if (loading.equals(choices[i])) {
				return i;
			}
		}
		return 3;
	}

	public void pressEdit() {
		if (edit.getText() == "Done") {
			edit.setText("Edit");
			setLocalUser();
			disableFirstPage();
			knownUsers.addUser(localUser);

		} else {
			profileView.setDisable(false);
			edit.setText("Done");
			name.setEditable(true);
			bio.setEditable(true);
			localIp.setEditable(true);
			status.setDisable(false);
		}
	}

	public void setLocalUser() {
		localUser.setBio(bio.getText());
		localUser.setIp(localIp.getText());
		localUser.setName(name.getText());
		localUser.setStat("Single");
		localUser.saveProfile(file);
	}

	public void disableFirstPage() {
		name.setEditable(false);
		bio.setEditable(false);
		status.setDisable(true);
		localIp.setEditable(false);
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
		textChat.add(text);
		chatMessages.setItems(textChat);
		chatText.setText("");
	}

	void badNews(String what) { //Dr. Ferrer 352 sockDemo
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(what);
		badNum.show();
	}

	private void dealWithInput(String info) {
		String temp;
		int ord;

		temp = info.substring(1);
		ord = Integer.parseInt(info.charAt(0) + "");

		if (ord == InfoType.PEOPLE.ordinal()) {
			updateAllPeople(temp);
		} else if (ord == InfoType.REQUEST.ordinal()) {
			//todo
		} else if (ord == InfoType.ACCEPT.ordinal()) {
			//todo
		} else if (ord == InfoType.MESSAGE.ordinal()) {
			//todo
		} else {
			badNews("Unrecognized InfoType attempted.");
		}
	}

	private void updateAllPeople(String info) {
		People newUsers = new People();
		People temp = new People(info);
		for (String s : temp.getKeys()) {
			updateProfileView(temp.getProfile(s));
			if (!knownUsers.getKeys().contains(s) || !knownUsers.getProfile(s).equals(temp.getProfile(s))) {
				for (String s1 : knownUsers.getKeys()) {
					sendTo(s1, new People(temp.getProfile(s1)).toString(), InfoType.PEOPLE.ordinal());
					newUsers.addUser(temp.getProfile(s1));
					updateProfileView(temp.getProfile(s1));
				}
			}
		}
		for (String s2 : newUsers.getKeys()) {
			knownUsers.addUser(newUsers.getProfile(s2));
		}
	}

	private void updateProfileView(Profile match) {
		Platform.runLater(() -> {
			TitledPane pane = new TitledPane();
			pane.setText(match.getName());
			pane.setContent(new TextField(match.getBio()));
			profilesFound.getPanes().add(pane);
		});

	}

	private void listen() throws IOException {
		new Thread(() -> {
			try {
				while (true) {
					Socket s = accepter.accept();
					System.out.println("New Client!");
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
	            System.out.println(sb.toString() + " Please in controller");
	            dealWithInput(sb.toString());
	            s.close();

			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}).start();
	}

	public void sendKnownUsers() {
		sendTo(IPaddress.getText(), knownUsers.toString(), InfoType.PEOPLE.ordinal());
	}

	private void sendTo(String host, String message, int ord) { //Dr. Ferrer sockDemo
		new Thread(() -> {
			try {
				Socket target = new Socket(host, portNum);
				send(target, ord + message);
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


}
