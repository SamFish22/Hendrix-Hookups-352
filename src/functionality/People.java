package functionality;

import java.util.HashMap;

public class People {
	HashMap<String, String> knownUsers;
	
	public People() {
		knownUsers = new HashMap<String, String>();
	}
	
	public People(Profile p) {
		knownUsers = new HashMap<String, String>();
		addUser(p);
	}
	
	public void addUser(Profile p) {
		knownUsers.put(p.getIp(), p.toString());
	}
	
	@Override
	public String toString() {
		String people;
		String temp;
		
		people = "";
		
		for (String s : knownUsers.keySet()) {
			temp = knownUsers.get(s) + " ";
			people = people + temp.length() + " " + temp; 
		}
		
		return people;
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
