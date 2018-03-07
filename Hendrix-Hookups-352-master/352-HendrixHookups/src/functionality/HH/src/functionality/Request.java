package functionality;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Request {

	private String info;
	private Alert alert;
	private Profile sender;

	public Request() {
		info = "";
		alert = null;
		sender = null;
	}

	public Request(Profile user) {
		sender = user;
		alert = new Alert(AlertType.CONFIRMATION, info, ButtonType.YES,ButtonType.NO);
		alert.setWidth(200);
		alert.setHeight(200);
	}

	public Request(String s){
		if (s.substring(0, 20).equals("Requesting Profile: ")) {
			s = s.substring(20);
			sender = new Profile(s);
			info = this.getInfo(sender);
			alert = new Alert(AlertType.CONFIRMATION, info, ButtonType.YES,ButtonType.NO);
			alert.setWidth(200);
			alert.setHeight(200);
		}
	}

	public String getInfo(Profile p) {
		info = "You have recieved a request from "+p.getName()+".\n"+
				"Status: " + p.getStat()+"\n"+
				"Bio: " + p.getBio()+"\n";
		return info;
		}

	public boolean chatRequest() {
		Optional<ButtonType> result = alert.showAndWait();
		if(!result.isPresent()) {
			return false;
		}
		else if(result.get() == ButtonType.YES) {
			return true;
		}
		return false;
	}

	public void setSender(Profile s) {
		sender = s;
	}

	public Profile getSender() {
		return sender;
	}

	@Override
	public String toString() {
		return "Requesting Profile: " + sender.toString();
	}

	@Override
	public boolean equals(Object p) {
		if (p instanceof Request) {
			if (getInfo(this.getSender()).equals(((Request) p).getInfo(((Request) p).getSender()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}