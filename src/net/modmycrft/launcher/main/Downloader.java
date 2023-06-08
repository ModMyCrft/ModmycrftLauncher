package net.modmycrft.launcher.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class Downloader {
	private String libDir;
	private String[] client = new String[3];
	private String[] server = new String[3];
	private String[][] lib = new String[3][3];
	private String[] natives = new String[3];
	private String[][] launcherLib = new String[2][3];
	
	public Downloader(){
		launcherLib[0][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/launcherLib/discord-rpc.jar?raw=true";
		launcherLib[0][1] = System.getProperty("user.dir") + "\\launcherLib\\";
		launcherLib[0][2] = "discord-rpc.jar";
		launcherLib[1][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/launcherLib/discord-rpc.dll?raw=true";
		launcherLib[1][1] = System.getProperty("user.dir") + "\\launcherLib\\";
		launcherLib[1][2] = "discord-rpc.dll";
	}
	
	public Downloader(String libDir, String jarsDir) {
		client[0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/jars/modmycrft.jar?raw=true";
		client[1] = jarsDir;
		client[2] = "modmycrft.jar";
		server[0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/jars/modmycrft_server.jar?raw=true";
		server[1] = jarsDir;
		server[2] = "modmycrft_server.jar";
		lib[0][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/lib/lwjgl.jar?raw=true";
		lib[0][1] = libDir;
		lib[0][2] = "lwjgl.jar";
		lib[1][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/lib/lwjgl_util.jar?raw=true";
		lib[1][1] = libDir;
		lib[1][2] = "lwjgl_util.jar";
		lib[2][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/lib/jinput.jar?raw=true";
		lib[2][1] = libDir;
		lib[2][2] = "jinput.jar";
		natives[0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/bin/natives.zip?raw=true";
		natives[1] = libDir;
		natives[2] = "natives.zip";
		launcherLib[0][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/resources/launcherLib/discord-rpc.jar?raw=true";
		launcherLib[0][1] = System.getProperty("user.dir") + "\\launcherLib\\";
		launcherLib[0][2] = "discord-rpc.jar";
		launcherLib[1][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/resources/launcherLib/discord-rpc.dll?raw=true";
		launcherLib[1][1] = System.getProperty("user.dir") + "\\launcherLib\\";
		launcherLib[1][2] = "discord-rpc.dll";
		this.libDir = libDir;
	}
	
	public void justDownloadRes(String res) {
		try {
			if(res != null && !res.equals("")) {
			switch(res) {
			case ("client"):
				downloadResource(client[0],client[1],client[2]);
				break;
			case ("server"):
				downloadResource(server[0],server[1],server[2]);
				break;
			case ("natives"):
				downloadResource(natives[0],natives[1],natives[2]);
				Launcher.unzip(new FileInputStream(new File(this.libDir, "natives.zip")), Paths.get(libDir));
				new File(this.libDir, "natives.zip").delete();
				break;
			case ("lwjgl"):
				downloadResource(lib[0][0],lib[0][1],lib[0][2]);
				break;
			case ("lwjgl_util"):
				downloadResource(lib[1][0],lib[1][1],lib[1][2]);
				break;
			case ("jinput"):
				downloadResource(lib[2][0],lib[2][1],lib[2][2]);
				break;
			case ("discordRPC"):
				downloadResource(launcherLib[0][0],launcherLib[0][1],launcherLib[0][2]);
				downloadResource(launcherLib[1][0],launcherLib[1][1],launcherLib[1][2]);
				break;
			}	
			}
		} catch (IOException e11) {
			e11.printStackTrace();
		}
	}
	public static void downloadResource(String uRL, String path, String file) throws IOException {
		if(!new File(path).exists()) {
			new File(path).mkdir();
		}
		Path path1 = Paths.get(path + file);
		URL url1 = new URL(uRL);
		try {
			url1 = new URL(uRL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try (InputStream in = url1.openStream()) {
		    Files.copy(in, path1, StandardCopyOption.REPLACE_EXISTING);
		}
	}
	public static void downloadResource(String uRL, String file) throws IOException {
		Path path1 = Paths.get(System.getProperty("user.dir") + "\\" + file);
		URL url1 = new URL(uRL);
		try {
			url1 = new URL(uRL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try (InputStream in = url1.openStream()) {
		    Files.copy(in, path1, StandardCopyOption.REPLACE_EXISTING);
		}
	}
}
