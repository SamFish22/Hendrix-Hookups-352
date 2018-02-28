package functionality;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class People {
	HashMap<String, String> knownUsers;

	public People() {
		knownUsers = new HashMap<String, String>();
	}

	public People(Profile p) {
		knownUsers = new HashMap<String, String>();
		addUser(p);
	}

	public People(String str) {
		knownUsers = new HashMap<String, String>();
		String[] profiles = str.split("Profile:");;
		for (String each : profiles) {
			addUser(new Profile(each));
		}

	}

	public void addUser(Profile p) {
		knownUsers.put(p.getIp(), p.toString());
	}

	public Set<String> getKeys() {
		return knownUsers.keySet();
	}

	public String getProfileString(String key) {
		return knownUsers.get(key);
	}

	public Profile getProfile(String key) {      // needs profile reconstruction from string implemented
		return new Profile(knownUsers.get(key));
	}

	public People reconstruct(String str) {
		People list = new People();
		String[] profiles = str.split("Profile:");;
		for (String each : profiles) {
			list.addUser(new Profile(each));
		}
		return list;
	}

	@Override
	public String toString() {
		/*String people;
		String temp;

		people = "";

		for (String s : knownUsers.keySet()) {
			temp = knownUsers.get(s) + " ";
			people = people + temp.length() + " " + temp;
		}*/

		String temp;
		temp = "";

		for (String s : knownUsers.keySet()) {
			temp = temp + "Profile:" + knownUsers.get(s);
		}
		return temp;
	}

	@Override
	public boolean equals(Object e) {
		return toString().equals(e.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
