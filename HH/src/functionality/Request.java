package functionality;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Request {
	
	private String info;
	
	public String getInfo(Profile p) {
		info = "You have recieved a request from "+p.getName()+".\n"+
				"Status: " + p.getStat()+"\n"+
				"Bio: " + p.getBio()+"\n";
		return info;
		}
	
	public void sendRequest(Profile a, Profile b) {
		//TODO implement a way to send to the second profile
		Alert alert = new Alert(AlertType.CONFIRMATION, getInfo(a), ButtonType.YES,ButtonType.NO);
		alert.setWidth(200);
		alert.setHeight(200);
		Optional<ButtonType> result = alert.showAndWait();
		if(!result.isPresent()) {
			return;
		}
		else if(result.get() == ButtonType.YES) {
			acceptRequest(a,b);
		}
		else if(result.get() == ButtonType.NO) {
			return;
		}
	}
	
	public void acceptRequest(Profile a, Profile b) {
	/*	ProfileList list_a = a.getList();
		list_a.addProfile(b);
		a.setList(list_a);
		ProfileList list_b = b.getList();
		list_b.addProfile(a);
		b.setList(list_b);*/
	}
	
	

}
