package functionality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class Profile {

	private String name;
	private String ip;
	private String stat;
	private String bio;
	private Image pic;
	private File imageFile;

	public Profile() {
		name = "";
		ip = "";
		stat = "";
		bio = "";
		pic = null;
		imageFile = null;
	}

	public Profile(String profile) {
		String count;
		ArrayList<String> stats;
		int indexer;
		count = "";
		stats = new ArrayList<String>();
		for (int i = 0; i < profile.length(); i++) {
			if (profile.charAt(i) != ' ') {
				count = count + profile.charAt(i);
			} else {
				indexer = Integer.parseInt(count);
				count = "";
				stats.add(profile.substring(i + 1, i + 1 + indexer));
				i = i + indexer;
			}
		}
		this.name = stats.get(0);
		this.ip = stats.get(1);
		this.stat = stats.get(2);
		this.bio = stats.get(3);
	}

	public Profile(String user_name, String ip_address, String status, String biography, Image picture) {
		name = user_name;
		ip = ip_address;
		stat = status;
		bio = biography;
		pic = picture;

	}

	public void editProfile(String n, String s, String b, Image p) {
		name = n;
		stat = s;
		bio = b;
		pic = p;
	}

	public void createProfile(File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveProfile(File file) {
		// TODO figure out how to save image
		FileWriter writer = null;
		try {
			writer = new FileWriter(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter print = new PrintWriter(writer);
		print.println("Hendrix-Hookups File");
		print.println(name);
		print.println(ip);
		print.println(stat);
		print.println(bio);
		print.close();
	}

	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public String getStat() {
		return stat;
	}

	public String getBio() {
		return bio;
	}

	public Image getPic() {
		return pic;
	}

	public void setIp(String newIp) {
		this.ip = newIp;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public void setStat(String newStat) {
		this.stat = newStat;
	}

	public void setBio(String newBio) {
		this.bio = newBio;
	}

	@Override
	public String toString() {
		return name.length() + " " + name + ip.length() + " " + ip + stat.length() + " " + stat + bio.length() + " "
				+ bio;
	}

	@Override
	public boolean equals(Object p) {
		return toString().equals(p.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public static Profile reconstruct(String profile) {
		String count;
		ArrayList<String> stats;
		int indexer;
		count = "";
		stats = new ArrayList<String>();
		for (int i = 0; i < profile.length(); i++) {
			if (profile.charAt(i) == ' ') {
				count = count + profile.charAt(i);
			} else {
				indexer = Integer.parseInt(count);
				count = "";
				stats.add(profile.substring(i + 1, i + 1 + indexer));
				i = i + indexer;
			}
		}
		Profile p = new Profile(stats.get(0), stats.get(1), stats.get(2), stats.get(3), null);

		return p;

	}

}
