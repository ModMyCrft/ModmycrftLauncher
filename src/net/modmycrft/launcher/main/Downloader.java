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
	private static String fs = File.separator;
	private String libDir;
	private String[] client = new String[3];
	private String[] server = new String[3];
	private String[][] lib = new String[3][3];
	private String[] natives = new String[3];
	private String[][] launcherLibWindows = new String[2][3];
	private String[][] launcherLibLinux = new String[2][3];
	
	public Downloader(){
		launcherLibWindows[0][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/launcherLib/discord-rpc.jar?raw=true";
		launcherLibWindows[0][1] = System.getProperty("user.dir") + fs + "launcherLib" + fs;
		launcherLibWindows[0][2] = "discord-rpc.jar";
		launcherLibWindows[1][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/launcherLib/discord-rpc.dll?raw=true";
		launcherLibWindows[1][1] = System.getProperty("user.dir") + fs + "launcherLib" + fs;
		launcherLibWindows[1][2] = "discord-rpc.dll";
		launcherLibLinux[0] = launcherLibWindows[0];
		launcherLibLinux[1][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/launcherLib/discord-rpc.so?raw=true";
		launcherLibLinux[1][1] = System.getProperty("user.dir") + fs + "launcherLib" + fs;
		launcherLibLinux[1][2] = "discord-rpc.so";
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
		EnumOS os = Launcher.getOs();
		if(os.equals(EnumOS.windows)) {
			natives[0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/bin/natives.zip?raw=true";
		} else if(os.equals(EnumOS.linux)) {
			natives[0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/bin/natives-linux.zip?raw=true";
		} else if(os.equals(EnumOS.solaris)) {
			natives[0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/bin/natives-solaris.zip?raw=true";
		} else if(os.equals(EnumOS.macos)) {
			natives[0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/bin/natives-macos.zip?raw=true";
		} 
		natives[1] = libDir;
		natives[2] = "natives.zip";
		launcherLibWindows[0][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/launcherLib/discord-rpc.jar?raw=true";
		launcherLibWindows[0][1] = System.getProperty("user.dir") + fs + "launcherLib" + fs;
		launcherLibWindows[0][2] = "discord-rpc.jar";
		launcherLibWindows[1][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/launcherLib/discord-rpc.dll?raw=true";
		launcherLibWindows[1][1] = System.getProperty("user.dir") + fs + "launcherLib" + fs;
		launcherLibWindows[1][2] = "discord-rpc.dll";
		launcherLibLinux[0] = launcherLibWindows[0];
		launcherLibLinux[1][0] = "https://github.com/ModMyCrft/ModmycrftLauncher/blob/main/versions/V9/launcherLib/discord-rpc.so?raw=true";
		launcherLibLinux[1][1] = System.getProperty("user.dir") + fs + "launcherLib" + fs;
		launcherLibLinux[1][2] = "discord-rpc.so";
		
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
				File file = new File(this.libDir + "natives");
				if(!file.exists()) {
					file.mkdir();
				}
				Launcher.unzip(new FileInputStream(new File(this.libDir, "natives.zip")), Paths.get(file.getAbsolutePath()));
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
			case ("discordRPC-Windows"):
				downloadResource(launcherLibWindows[0][0],launcherLibWindows[0][1],launcherLibWindows[0][2]);
				downloadResource(launcherLibWindows[1][0],launcherLibWindows[1][1],launcherLibWindows[1][2]);
				break;
			case ("discordRPC-Linux"):
				downloadResource(launcherLibLinux[0][0],launcherLibLinux[0][1],launcherLibLinux[0][2]);
				downloadResource(launcherLibLinux[1][0],launcherLibLinux[1][1],launcherLibLinux[1][2]);
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
		Path path1 = Paths.get(System.getProperty("user.dir") + fs + file);
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
