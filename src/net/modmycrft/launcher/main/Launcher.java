package net.modmycrft.launcher.main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
public class Launcher {
	private static LauncherDiscordRPC launcherDiscordRPC;
	public static boolean rpc = true;
	public static void main(String[] args) {
		boolean discordRPCUse = false;
		HashMap<String, Object> options = getOptionsFromFile();
		if(options != null) {
			discordRPCUse = (boolean) options.get("DiscordRPCUse");
		}
		if(discordRPCUse && rpc) {
			startRPC();
		}
		new Gui();
	}

	public static void startRPC() {
		if(rpc) {
			launcherDiscordRPC.start();
		}
	}
	
	public static EnumOS getOs() {
		String string0 = System.getProperty("os.name").toLowerCase();
		return string0.contains("win") ? EnumOS.windows : (string0.contains("mac") ? EnumOS.macos : (string0.contains("solaris") ? EnumOS.solaris : (string0.contains("sunos") ? EnumOS.solaris : (string0.contains("linux") ? EnumOS.linux : (string0.contains("unix") ? EnumOS.linux : EnumOS.unknown)))));
	}
	
	public static void updateDiscordRPCThread() {
		if(rpc) {
			launcherDiscordRPC = new LauncherDiscordRPC();
		}
	}

	public void launchGame(String username, int maxRam, boolean disableConsole, String javaArgs, String minecraftDir, String java, String libDir, String jarsDir, String defaultServer, boolean connectToServer) {
		Game startGame = new Game(true,username,"pass",maxRam,maxRam, disableConsole, javaArgs, jarsDir, libDir, minecraftDir, java, defaultServer, connectToServer);
		startGame.startGame();
	}	
	public void launchServer(int maxRam,String java,String jarsDir) {
		Game startGame = new Game(maxRam,maxRam, jarsDir, java);
		startGame.startGameServer();
	}

	public static void unzip(InputStream is, Path targetDir) throws IOException {
		targetDir = targetDir.toAbsolutePath();
		try (ZipInputStream zipIn = new ZipInputStream(is)) {
			for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null; ) {
				Path resolvedPath = targetDir.resolve(ze.getName()).normalize();
				if (!resolvedPath.startsWith(targetDir)) {
					throw new RuntimeException("Entry with an illegal path: "
							+ ze.getName());
				}
				if (ze.isDirectory()) {
					Files.createDirectories(resolvedPath);
				} else {
					Files.createDirectories(resolvedPath.getParent());
					Files.copy(zipIn, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}
		return;
	}
	public static void shutdownRPC() {
		if(rpc) {
			launcherDiscordRPC.shutdownThread();
		}
	}

	public static HashMap<String, Object> getOptionsFromFile() {
		File optionsFile = new File("Options.launcher");
			try{
			FileInputStream propertiesFileStream = new FileInputStream(optionsFile);
			int maxRam;
			int maxRamServ;
			String username;
			boolean disableConsole;
			boolean discordRPCUse;
			String minecraftDir;
			String java;
			String javaArgs;
			String defaultServer;
			boolean connectToServer;
			String jarsDir;
			String libDir;
			Properties property = new Properties();
			property.load(propertiesFileStream);
			maxRam = Integer.parseInt(property.getProperty("MaxRAM", "256"));
			maxRamServ = Integer.parseInt(property.getProperty("ServerMaxRAM", "256"));
			username = property.getProperty("Username", "DefaultPlayer");
			disableConsole = Boolean.parseBoolean(property.getProperty("DisableConsole", "false"));
			discordRPCUse = Boolean.parseBoolean(property.getProperty("DiscordRPCUse", "false"));
			minecraftDir = property.getProperty("MinecraftDirectory", "default");
			java = property.getProperty("Java", "java");
			javaArgs = property.getProperty("JavaArgs", "-Djava.util.Arrays.useLegacyMergeSort=true -Dhttp.proxyHost=betacraft.uk -Dhttp.proxyPort=11705");
			defaultServer = property.getProperty("DefaultServer", "");
			connectToServer = Boolean.parseBoolean(property.getProperty("ConnectToServer", "false"));
			jarsDir = property.getProperty("JarsDirectory", "default");
			libDir = property.getProperty("LibDirectory", "default");
			if(username.length() < 3 || username.length() > 16){
				username = "DefaultPlayer"; 
			}
			HashMap<String, Object> out = new HashMap<String, Object>();
			out.put("MaxRAM", maxRam);
			out.put("ServerMaxRAM", maxRamServ);
			out.put("Username", username);
			out.put("disableConsole", disableConsole);
			out.put("DiscordRPCUse", discordRPCUse);
			out.put("MinecraftDirectory", minecraftDir);
			out.put("Java", java);
			out.put("JavaArgs", javaArgs);
			out.put("DefaultServer", defaultServer);
			out.put("ConnectToServer", connectToServer);
			out.put("JarsDirectory", jarsDir);
			out.put("LibDirectory", libDir);
			return out;
			} catch(Exception e){
			int maxRam;
			int maxRamServ;
			String username;
			boolean disableConsole;
			boolean discordRPCUse;
			String minecraftDir;
			String java;
			String javaArgs;
			String defaultServer;
			boolean connectToServer;
			String jarsDir;
			String libDir;
			maxRam = 256;
			maxRamServ = 256;
			username = "DefaultPlayer";
			disableConsole = false;
			discordRPCUse = false;
			minecraftDir = "default";
			java = "java";
			javaArgs = "-Djava.util.Arrays.useLegacyMergeSort=true -Dhttp.proxyHost=betacraft.uk -Dhttp.proxyPort=11702";
			defaultServer = "";
			connectToServer = false;
			jarsDir = "default";
			libDir = "default";
			if(username.length() < 3 || username.length() > 16){
				username = "DefaultPlayer"; 
			}
			HashMap<String, Object> out = new HashMap<String, Object>();
			out.put("MaxRAM", maxRam);
			out.put("ServerMaxRAM", maxRamServ);
			out.put("Username", username);
			out.put("disableConsole", disableConsole);
			out.put("DiscordRPCUse", discordRPCUse);
			out.put("MinecraftDirectory", minecraftDir);
			out.put("Java", java);
			out.put("JavaArgs", javaArgs);
			out.put("DefaultServer", defaultServer);
			out.put("ConnectToServer", connectToServer);
			out.put("JarsDirectory", jarsDir);
			out.put("LibDirectory", libDir);
			return out;
			}				
	}

	public void setOptionsToFile(int MaxRam, String username, int MaxRamServ, boolean consoleDisable, boolean discordRPCUse, String mineDir, String java, String jarsDir, String libDir, boolean connectToServer, String defaultServer, String javaArgs) throws IOException {
		File optionsFile = new File("Options.launcher");
		if(optionsFile.exists()) {
			optionsFile.delete();
			optionsFile.createNewFile();
		} else {
			optionsFile.createNewFile();
		}
		FileInputStream propertiesFileStream = new FileInputStream(optionsFile);
		Properties property = new Properties();
		property.load(propertiesFileStream);
		property.setProperty("MaxRAM", "" + MaxRam);
		property.setProperty("ServerMaxRAM", "" + MaxRamServ);
		property.setProperty("Username", username);
		property.setProperty("DisableConsole", "" + consoleDisable);
		property.setProperty("DiscordRPCUse", "" + discordRPCUse);
		property.setProperty("MinecraftDirectory", "" + mineDir);
		property.setProperty("Java", "" + java);
		property.setProperty("JavaArgs", javaArgs);
		property.setProperty("DefaultServer", defaultServer);
		property.setProperty("ConnectToServer", "" + connectToServer);
		property.setProperty("JarsDirectory", jarsDir);
		property.setProperty("LibDirectory", libDir);
		property.store(new FileWriter(optionsFile), null);
		return;
	}

	public static HashMap<String, Object> getServerPropertiesFromFile(String file) {
		File file1 = new File(file);
		if(!file1.exists()) {
			return null;
		} else {
			try {
			FileInputStream propertiesFileStream = new FileInputStream(file1);
			Properties property = new Properties();
			property.load(propertiesFileStream);
			boolean onlineMode;
			String levelName;
			int serverPort;
			int maxPlayers;
			String serverIP;
			onlineMode = Boolean.parseBoolean(property.getProperty("online-mode", "false"));
			serverIP = property.getProperty("server-ip", "");
			serverPort = Integer.parseInt(property.getProperty("server-port", "25565"));
			maxPlayers = Integer.parseInt(property.getProperty("max-players", "20"));
			levelName = property.getProperty("level-name", "world");
			HashMap<String, Object> out = new HashMap<String, Object>();
			out.put("onlineMode", onlineMode);
			out.put("serverIP", serverIP);
			out.put("serverPort", serverPort);
			out.put("maxPlayers", maxPlayers);
			out.put("levelName", levelName);
			return out;
			} catch (Exception e) {
				boolean onlineMode;
				String levelName;
				int serverPort;
				int maxPlayers;
				String serverIP;
				onlineMode = false;
				serverIP = "";
				serverPort = 25565;
				maxPlayers = 20;
				levelName = "world";
				HashMap<String, Object> out = new HashMap<String, Object>();
				out.put("onlineMode", onlineMode);
				out.put("serverIP", serverIP);
				out.put("serverPort", serverPort);
				out.put("maxPlayers", maxPlayers);
				out.put("levelName", levelName);
				return out;
			}
		}
	}

	public void setServerPropertiesToFile(String file, String level, String serverIP, int serverPort, int maxPlayers, boolean onlineMode) throws IOException {
		File file1 = new File(file);
		if(file1.exists()) {
			file1.delete();
			file1.createNewFile();
		} else {
			file1.createNewFile();
		}
		FileInputStream propertiesFileStream = new FileInputStream(file1);
		Properties property = new Properties();
		property.load(propertiesFileStream);
		property.setProperty("online-mode", "" + onlineMode);
		property.setProperty("server-ip", serverIP);
		property.setProperty("server-port", "" + serverPort);
		property.setProperty("max-players", "" + maxPlayers);
		property.setProperty("level-name", level);
		property.store(new FileWriter(file1), null);
		return;
	}
}
