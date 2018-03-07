package functionality;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	private ProfileList accepted;
	private File file;
	private File imageFile;
	private String line;

	public Profile() {
		name = "";
		ip = "";
		stat = "";
		bio = "";
		pic = null;
		accepted = null;
		file = null;
		imageFile = null;
		line = "";
	}

	public Profile(String user_name, String ip_address, String status, String biography, Image picture, ProfileList list) {
		name = user_name;
		ip = ip_address;
		stat = status;
		bio = biography;
		pic = picture;
		accepted = list;

	}

	public void editProfile(String n, String s, String b, Image p, ProfileList l) {
		name = n;
		stat = s;
		bio = b;
		pic = p;
		accepted = l;
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

	public ProfileList getList() {
		return accepted;
	}

	public void setList(ProfileList list) {
		accepted = list;
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

}
