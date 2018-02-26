package functionality;

import java.util.ArrayList;
import java.util.List;

public class ProfileList {
	
	private List<Profile> profileList = new ArrayList<>();
	
	public void addProfile(Profile p) {
		profileList.add(p);
	}
	
	public Profile getProfile(int i) {
		return profileList.get(i);
	}
	
	public void updateList(Profile p) {
		for (int i = 0; i < profileList.size();i++) {
			if (profileList.get(i).getIp()==p.getIp()) {
				p.editProfile(p.getName(),p.getStat(),p.getBio(),p.getPic(),p.getList());
			}
		}
	}
	
	public ProfileList filteredList(String s) {
		ProfileList p = new ProfileList();
		if (isIp(s)) {
			for (int i = 0; i < profileList.size();i++) {
				if (profileList.get(i).getIp()==s) {
					p.addProfile(profileList.get(i));
				}
			}
		} else {
			for (int i = 0; i < profileList.size();i++) {
				if (profileList.get(i).getStat()==s) {
					p.addProfile(profileList.get(i));
				}
			}
		}
		return p;
	}
	
	public boolean isIp(String s) {
		if (Character.isDigit(s.charAt(0))) {
			return true;
		}
		
		return false;
	}
}
