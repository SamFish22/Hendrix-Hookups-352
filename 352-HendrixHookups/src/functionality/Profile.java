package functionality;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class Profile {

	private String name;
	private String ip;
	private String stat;
	private String bio;
	private Image pic;
	private File file;
	private File imageFile;
	private String line;

	public Profile() {
		name = "";
		ip = "";
		stat = "";
		bio = "";
		pic = null;
		file = null;
		imageFile = null;
		line = "";
	}

	public Profile(String profile) {
		int end=0;
		while(!(profile.substring(0, end).contains(" "))) {
			end++;
		}
		List<String> info = Arrays.asList(profile.split(","));
		this.name = info.get(0);
		this.ip = info.get(1);
		this.stat = info.get(2);
		this.bio = info.get(3);

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

	public void createProfile() {
		file = new File(System.getProperty("user.dir")+"\\"+name+".txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveProfile() {
		//TODO figure out how to save image
		FileWriter writer = null;
		try {
			System.out.println(file.getAbsolutePath());
			writer = new FileWriter(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("made it");
		PrintWriter print = new PrintWriter(writer);
		print.println("Hendrix-Hookups File");
		print.println(name);
		print.println(ip);
		print.println(stat);
		print.println(bio);
		print.close();
	}

	public void loadFile(TextArea bio, TextField name, TextField status, File file) {
		try {
			FileReader fileReader = new FileReader(file.getAbsolutePath());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			try {
				line = bufferedReader.readLine();
				if (line.equals("Hendrix-Hookups File")) {
					name.setText(bufferedReader.readLine());
					bufferedReader.readLine();
					status.setText(bufferedReader.readLine());
					bio.setText(bufferedReader.readLine());
					//TODO open image
				} else {
					//TODO
				}
			} catch (IOException e) {
				//TODO
			}
		} catch (FileNotFoundException e) {
			//TODO
		}
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
		return getName()+","+getIp()+","+getStat()+","+getBio();
	}

	@Override
	public boolean equals(Object p) {
		if(p instanceof Profile) {
			if(getIp().equals(((Profile) p).getIp())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public Profile reconstruct(String profile) {
		int end=0;
		while(!(profile.substring(0, end).contains(" "))) {
			end++;
		}
		List<String> info = Arrays.asList(profile.split(","));
		Profile p = new Profile(info.get(0),info.get(1),info.get(2),info.get(3),null);
		return p;
	}
}
