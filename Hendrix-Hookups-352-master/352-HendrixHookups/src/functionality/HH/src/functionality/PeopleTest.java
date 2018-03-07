package functionality;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

class PeopleTest {

	@Test
	void addProfileTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		People people = new People();
		people.addUser(user);
		assertTrue(user.toString().equals(people.getProfileString(ip)));
	}
	
	@Test
	void firstConstructorTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		People people = new People(user);
		assertTrue(user.toString().equals(people.getProfileString(ip)));
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
		People people = new People(user);
		People newPeople = new People(people.toString());
		assertTrue(people.toString().equals(newPeople.toString()));
	}
	
	@Test
	void getKeysTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		Profile user2 = new Profile("Jane Doe", "10.253.202.117" , "In a Relationship", "Average human person", null);
		Profile user3 = new Profile("Jim Doe", "10.253.201.82" , "Single", "Average human person", null);
		People people = new People(user);
		people.addUser(user2);
		people.addUser(user3);
		assertTrue(people.getKeys().contains(user.getIp()));
		assertTrue(people.getKeys().contains(user2.getIp()));
		assertTrue(people.getKeys().contains(user3.getIp()));
	}
	
	@Test
	void getProfileTest() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = null;
		}
		Profile user = new Profile("John Doe", ip , "Single", "Average human person", null);
		People people = new People(user);
		assertTrue(user.toString().equals(people.getProfile(user.getIp()).toString()));
	}
}
