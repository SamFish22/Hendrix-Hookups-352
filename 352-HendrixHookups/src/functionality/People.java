package functionality;

import java.util.HashMap;
import java.util.Set;

public class People {
	HashMap<String, String> knownUsers;
	String firstIP;

	public People() {
		knownUsers = new HashMap<String, String>();
		firstIP = null;
	}

	public People(Profile p) {
		knownUsers = new HashMap<String, String>();
		addUser(p);
		firstIP = p.getIp();
	}

	public People(String str) {
		boolean first = true;
		knownUsers = new HashMap<String, String>();
		String[] profiles = str.split("Profile:");
		for (String each : profiles) {
			if (!each.isEmpty()) {
				addUser(new Profile(each));
				if (first) {
					firstIP = new Profile(each).getIp();
					first = false;
				}
			}
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

	public Profile getProfile(String key) { // needs profile reconstruction from string implemented
		return new Profile(knownUsers.get(key));
	}

	public People reconstruct(String str) {
		People list = new People();
		String[] profiles = str.split("Profile:");
		;
		for (String each : profiles) {
			list.addUser(new Profile(each));
		}
		return list;
	}

	public String getFirstIP() {
		return firstIP;
	}

	@Override
	public String toString() {
		/*
		 * String people; String temp;
		 *
		 * people = "";
		 *
		 * for (String s : knownUsers.keySet()) { temp = knownUsers.get(s) + " "; people
		 * = people + temp.length() + " " + temp; }
		 */

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
