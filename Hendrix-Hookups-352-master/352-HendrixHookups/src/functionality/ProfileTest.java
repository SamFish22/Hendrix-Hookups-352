package functionality;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import javafx.scene.image.Image;

class ProfileTest {

	@Test
	void firstConstructorTest() {
		Profile user = new Profile();
		user.setName("John Doe");
		user.setBio("Average human person");
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			user.setIp(ip);
		} catch (UnknownHostException e) {
			ip = null;
		}
		user.setStat("Single");
		assertEquals("John Doe",user.getName());
		assertEquals("Average human person", user.getBio());
		assertEquals(ip, user.getIp());
		assertEquals("Single", user.getStat());
	}
	
	@Test
	void secondConstructorTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		assertEquals("John Doe",user.getName());
		assertEquals("Average human person", user.getBio());
		assertEquals(ip, user.getIp());
		assertEquals("Single", user.getStat());
	}
	
	@Test 
	void thirdConstructorTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		Profile newUser = new Profile(user.toString());
		assertEquals(user.toString(), newUser.toString());
	}
	
	@Test 
	void editProfileTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		user.editProfile("Jane Doe", "In a Relationship", "human being", null);
		assertEquals(user.getName(), "Jane Doe");
		assertEquals(user.getStat(), "In a Relationship");
		assertEquals(user.getBio(), "human being");
		assertEquals(user.getPic(), null);
	}

}
