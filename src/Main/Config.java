package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
GamePanel gp;
public Config(GamePanel gp) {
	this.gp=gp;
}
public void saveConfig() {
	try {
		BufferedWriter bw=new BufferedWriter(new FileWriter("config.txt"));
	//Music Volume
		bw.write(String.valueOf(gp.music.volumeScale));
		bw.newLine();
		
	//SE VOLUME
		bw.write(String.valueOf(gp.se.volumeScale));
		bw.newLine();
		
		bw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public void loadConfig() {
    try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
        String s;

        // Music Volume
        s = br.readLine(); // Skip header/comment line
        s = br.readLine();
        gp.music.volumeScale = parseOrDefault(s, gp.music.volumeScale);

        // SE Volume
        s = br.readLine();
        gp.se.volumeScale = parseOrDefault(s, gp.se.volumeScale);

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Config file missing, using defaults.");
    }
}

private int parseOrDefault(String value, int defaultValue) {
    if (value == null) return defaultValue;
    try {
        return Integer.parseInt(value.trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid number in config: " + value + ", using default.");
        return defaultValue;
    }
}

}
