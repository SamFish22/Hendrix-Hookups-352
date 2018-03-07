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
import java.util.ArrayList;

import functionality.Chat;
import functionality.InfoType;
import functionality.People;
import functionality.Profile;
import functionality.Request;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;

public class Controller { // add profile picture
	@FXML
	Button edit, send, chat, go, getConnections, changeChat;

	@FXML
	TextField IPaddress, chatText, name;

	@FXML
	Tab homepage, profileView, chatroom;

	@FXML
	ListView<String> peopleConnected, chattingView; // change peopleConnected

	@FXML
	TextArea bio, localIp; // inetaddress.getlocalhost.get

	@FXML
	TabPane holder;

	@FXML
	ChoiceBox<String> status, chatterChoiceBox;

	@FXML
	Accordion profilesFound;

	@FXML
	AnchorPane chatWindow;

	static final int portNum = 8888;

	People knownUsers;
	People associates;

	Profile localUser;

	Chat allChats = new Chat();

	File file;

	private ServerSocket accepter;

	public void initialize() {
		String userHomeFolder = System.getProperty("user.home");
		file = new File(userHomeFolder, "HendrixHookups.txt");
		localUser = new Profile();
		knownUsers = new People();
		associates = new People();
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
		ObservableList<String> options = FXCollections.observableArrayList("Single", "In a relationship",
				"It's complicated", "Too blessed to be stressed", "To stressed to be blessed", "Thruple aspiring");
		status.getItems().addAll(options);

		IPaddress.setVisible(false);
		go.setVisible(false);

		try {
			accepter = new ServerSocket(portNum);
			listen();

		} catch (IOException e) {

			badNews(e.getMessage());
			e.printStackTrace();
		}
	}

	private boolean isFirstTime() {
		if (file.exists()) {
			return false;
		}
		return true;
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
					status.getSelectionModel().selectFirst();
					bio.setText(bufferedReader.readLine());
					bufferedReader.close();
					// TODO open image
				} else {
					// TODO
				}
			} catch (IOException e) {
				// TODO
			}
		} catch (FileNotFoundException e) {
			// TODO
		}
	}

	public int parseStatus(String loading) {
		String[] choices = { "Single", "In a relationship", "It's complicated", "Too blessed to be stressed",
				"To stressed to be blessed", "Thruple aspiring" };
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
		if (profilesFound.getExpandedPane() != null) {
			String name = profilesFound.getExpandedPane().getText();
			sendTo(knownUsers.getIP(name), new Request(localUser).toString(), InfoType.REQUEST.ordinal());
		}
	}

	public void pressChatExclaimationPoint() {
		String name = chatterChoiceBox.getSelectionModel().getSelectedItem();
		if (name != null) {
			String ip = knownUsers.getIP(name);
			Profile c = knownUsers.getProfile(ip);
			allChats.setChatter(c);
			chattingView.setItems(allChats.getChatWith(name));
		}
	}

	public void sendMyMessage() {
		String text = localUser.getName() + ": " + chatText.getText();
		String currentChatter = allChats.getCurrentChatter().getName();
		allChats.updateChat(currentChatter, text);
		chattingView.setItems(allChats.getChatWith(currentChatter));
		chatText.setText("");
		sendTo(knownUsers.getIP(currentChatter), currentChatter.length() + " " + currentChatter + text, InfoType.MESSAGE.ordinal());
	}

	void badNews(String what) { // Dr. Ferrer 352 sockDemo
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(what);
		badNum.show();
	}

	private void dealWithInput(String info) {
		System.out.println("deal with input: " + info);
		String temp;
		int ord;

		temp = info.substring(1);
		ord = Integer.parseInt(info.charAt(0) + "");
		System.out.println("deal with input temp" + temp);

		if (ord == InfoType.PEOPLE.ordinal()) {
			updateAllPeople(temp);
		} else if (ord == InfoType.UPDATE.ordinal()) {
			updateSelf(temp);
		} else if (ord == InfoType.REQUEST.ordinal()) {
			handleRequest(temp);
		} else if (ord == InfoType.ACCEPT.ordinal()) {
			acceptRequest(temp);
		} else if (ord == InfoType.MESSAGE.ordinal()) {
			newMessage(temp);
		} else {
			badNews("Unrecognized InfoType attempted.");
		}
	}

	private void newMessage(String info) {
		String count;
		String message;
		String name;

		int indexer;

		count   = "";
		message = "";
		name    = "";

		for (int i = 0; i < info.length(); i++) {
			if (info.charAt(i) != ' ') {
				count = count + info.charAt(i);
			} else {
				indexer = Integer.parseInt(count);
				count = "";
				name = info.substring(i + 1, i + 1 + indexer);
				message = info.substring(i + 1 + indexer, info.length());
				i = info.length();
			}
		}

		allChats.updateChat(name, message);
		chattingView.setItems(allChats.getChatWith(name));
	}

	private void handleRequest(String info) {
		new Thread(() -> {
			try {
				Request temp = new Request(info);
				updateSelf(new People(temp.getSender()).toString());
				if (temp.chatRequest()) {
					sendTo(temp.getSender().getIp(), localUser.toString(), InfoType.ACCEPT.ordinal());
					associates.addUser(temp.getSender());
					allChats.addChatter(temp.getSender().getName());
					chatterChoiceBox.getItems().addAll(allChats.getChatList());
					holder.getSelectionModel().select(chatroom);
					chatroom.setDisable(false);
				}
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}).start();
	}

	private void acceptRequest(String info) {
		associates.addUser(new Profile(info));
		// the other part of a miracle happens here
	}

	private void updateAllPeople(String info) {
		/*
		 * System.out.println("update all people " + info); People newUsers = new
		 * People(); People temp = new People(info); for (String s : temp.getKeys()) {
		 * //updateProfileView(temp.getProfile(s)); if
		 * (!knownUsers.getKeys().contains(s) ||
		 * !knownUsers.getProfile(s).equals(temp.getProfile(s))) { //sendTo(s, new
		 * People(temp.getProfile(s)).toString(), InfoType.PEOPLE.ordinal());
		 * newUsers.addUser(temp.getProfile(s)); for (String s1 : knownUsers.getKeys())
		 * { sendTo(s1, new People(temp.getProfile(s)).toString(),
		 * InfoType.PEOPLE.ordinal()); newUsers.addUser(temp.getProfile(s)); } } }
		 *
		 * People needToSend = new People(); for (String s1 : knownUsers.getKeys()) { if
		 * (!temp.getKeys().contains(s1)) {
		 * needToSend.addUser(knownUsers.getProfile(s1));
		 * knownUsers.addUser(knownUsers.getProfile(s1)); } } sendTo(temp.getFirstIP(),
		 * needToSend.toString(), InfoType.PEOPLE.ordinal());
		 *
		 * for (String s2 : newUsers.getKeys()) {
		 * knownUsers.addUser(newUsers.getProfile(s2));
		 * updateProfileView(newUsers.getProfile(s2)); }
		 * System.out.println("updateallpeople knownUsers " + knownUsers.toString());
		 */

		People temp;

		updateSelf(info);

		temp = new People(info);
		for (String s1 : knownUsers.getKeys()) {
			if (!s1.equals(localUser.getIp())) {
				sendTo(s1, knownUsers.toString(), InfoType.UPDATE.ordinal());
			}
		}
	}

	private void updateSelf(String info) {
		People temp = new People(info);

		for (String s : temp.getKeys()) {
			if (!knownUsers.getKeys().contains(s) || !knownUsers.getProfile(s).equals(temp.getProfile(s))) {
				knownUsers.addUser(temp.getProfile(s));
			}
		}
		updateProfileView();
	}

	private void updateProfileView() {
		Platform.runLater(() -> {
			profilesFound.getPanes().clear();
			for (String each : knownUsers.getKeys()) {
				Profile match = knownUsers.getProfile(each);
				if (!match.getIp().equals(localUser.getIp())) {
					TitledPane pane = new TitledPane();
					pane.setText(match.getName());
					GridPane grid = new GridPane();
					grid.addRow(0, new Label(match.getStat()));
					TextArea theirBio = new TextArea(match.getBio());
					theirBio.setEditable(false);
					theirBio.setWrapText(true);
					grid.addRow(1, theirBio);
					pane.setContent(grid);
					profilesFound.getPanes().add(pane);
				}
			}
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

	private void newClient(Socket s) throws IOException { // Dr. Ferrer sockDemo
		new Thread(() -> {
			try {
				BufferedReader responses = new BufferedReader(new InputStreamReader(s.getInputStream()));
				StringBuilder sb = new StringBuilder();
				while (!responses.ready()) {
				}
				while (responses.ready()) {
					sb.append(responses.readLine());
				}
				System.out.println("double check");
				System.out.println("check " + sb.toString() + " Please in controller");
				// String string = sb.toString();
				dealWithInput(sb.toString().toString());
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

	private void sendTo(String host, String message, int ord) { // Dr. Ferrer sockDemo
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

	private void send(Socket target, String message) throws IOException { // Dr. Ferrer sockDemo
		PrintWriter sockout = new PrintWriter(target.getOutputStream());
		sockout.println(message);
		sockout.flush();
	}

}
