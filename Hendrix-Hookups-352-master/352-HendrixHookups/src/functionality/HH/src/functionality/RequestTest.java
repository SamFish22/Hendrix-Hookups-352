package functionality;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

class RequestTest {

	@Test
	void senderTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		Request request = new Request();
		request.setSender(user);
		assertTrue(user.toString().equals(request.getSender().toString()));
	}
	
	@Test
	void getInfoTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		Request request = new Request();
		request.setSender(user);
		request.getInfo(user);
		assertTrue(request.getInfo(user).equals("You have recieved a request from "+user.getName()+".\n"+
				"Status: " + user.getStat()+"\n"+
				"Bio: " + user.getBio()+"\n"));
	}

}
