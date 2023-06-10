package net.modmycrft.launcher.main;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Game extends Thread implements Runnable{
	private static String fs = File.separator;
	private String username;
	private String password;
	private int minRam;
	private int maxRam;
	private boolean disableConsole;
	private String javaArgs;
	private String java;
	private String jarsDir;
	private String libDir;
	private String gameDir;
	private String sessionID = "-";
	private boolean isGameDirNotDefault;
	private boolean connectToServer;
	private String defaultServer;
	private Downloader d;
	public Game(boolean offlineMode, String username, String password, int minRam, int maxRam, boolean disableConsole, String javaArgs, String jarsDir, String libDir, String gameDir, String java, String defaultServer, boolean connectToServer) {
		this.username = username;
		this.password = password;
		this.minRam = minRam;
		this.maxRam = maxRam;
		this.disableConsole = disableConsole;
		this.javaArgs = javaArgs;
		this.java = java;
		this.gameDir = gameDir;
		this.jarsDir = jarsDir + fs;
		this.libDir = libDir + fs;
		if(this.gameDir.equals("default")) {
			this.isGameDirNotDefault = false;
			this.gameDir = "";
		}
		if(this.libDir.equals("default" + fs)) {
			this.libDir = System.getProperty("user.dir") + fs + "lib" + fs;
		}
		if(this.jarsDir.equals("default" + fs)) {
			this.jarsDir = System.getProperty("user.dir") + fs + "jars" + fs ;
		}
		d = new Downloader(this.libDir, this.jarsDir);
		this.connectToServer = connectToServer;
		this.defaultServer = defaultServer;
	}
	public Game(int minRam, int maxRam, String jarsDir, String java) {
		this.minRam = minRam;
		this.maxRam = maxRam;
		this.java = java;
		this.jarsDir = jarsDir + fs;
		if(this.jarsDir.equals("default" + fs)) {
			this.jarsDir = System.getProperty("user.dir") + fs + "jars" + fs;
		}
		d = new Downloader("", this.jarsDir);
	}
	public void startGameServer(){
		if (this.username == null && this.password == null) {
		Runnable threadTask = () -> {
			try {
					if(this.checkUpdate()){
						d.justDownloadRes("server");
						d.justDownloadRes("client");
					} else if(!(new File(jarsDir, "modmycrft_server.jar")).exists()) {
						d.justDownloadRes("server");
					}
					System.out.println(maxRam);
					File serverDir = new File(System.getProperty("user.dir") + fs +"server");
					if(!serverDir.exists()) {
						serverDir.mkdir();
					}
					List<String> args = new ArrayList<String>();
					args.add(this.java);
					args.add("-Xms" + this.minRam + "M");
					args.add("-Xmx" + this.maxRam + "M");
					args.add("-jar");
					args.add(this.jarsDir + "modmycrft_server.jar");
					ProcessBuilder builder = new ProcessBuilder(args);
					builder.directory(serverDir);
					Process process = builder.start();
					while(process.isAlive()) {}
					this.interrupt();
			} catch(IOException e) {
				Gui.showError("Error with starting game: IOException!");
			}
		};
		Thread gameThread = new Thread(threadTask);
		gameThread.setName("ModMyCrftGameServer");
		gameThread.start();
		}
	}
	
	private boolean checkUpdate(){
		try {
		URL current = new URL("https://raw.githubusercontent.com/ModMyCrft/ModmycrftLauncher/main/versions/V9/jars/current");
		BufferedReader br = new BufferedReader(new InputStreamReader(current.openStream()));
		String version = br.readLine();
		br.close();
		File currentFile = new File(this.jarsDir + "current");
		if(!currentFile.exists()){
			currentFile.getParentFile().mkdirs();
			currentFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(currentFile));
			bw.write(version);
			bw.close();
			return true;
		} else {
		BufferedReader br1 = new BufferedReader(new FileReader(currentFile));
		String version1 = br1.readLine();
		br1.close();
		if(!version.equals(version1)){
			currentFile.delete();
			currentFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(currentFile));
			bw.write(version);
			bw.close();
			return true;
		} else {
			return false;
		}
		}
		} catch(Exception e){
			e.printStackTrace();
			return true;
		}
	}
	
	public void startGame() {
		if(this.password != null && this.username != null){
					Runnable threadTask = () -> {
					try {
						if(!(new File(this.libDir, "lwjgl.jar")).exists()) {
							d.justDownloadRes("lwjgl");
						}
						if(!(new File(this.libDir, "jinput.jar")).exists()) {
							d.justDownloadRes("jinput");
						}
						if(!(new File(this.libDir, "lwjgl_util.jar")).exists()) {
							d.justDownloadRes("lwjgl_util");
						}
						EnumOS os = Launcher.getOs();
						if(os.equals(EnumOS.windows)) {
						if(!(new File(this.libDir + "natives")).exists() || !(new File(this.libDir + "natives" + fs + "OpenAL32.dll")).exists() || !(new File(this.libDir + "natives" + fs + "OpenAL64.dll")).exists() || !(new File(this.libDir + "natives" + fs + "jinput-dx8.dll")).exists() || !(new File(this.libDir + "natives" + fs + "jinput-dx8_64.dll")).exists() || !(new File(this.libDir + "natives" + fs + "jinput-raw.dll")).exists() || !(new File(this.libDir + "natives" + fs + "jinput-raw_64.dll")).exists() || !(new File(this.libDir + "natives" + fs + "lwjgl.dll")).exists() || !(new File(this.libDir + "natives" + fs + "lwjgl64.dll")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "LWJGL.RSA")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "LWJGL.SF")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "MANIFEST.MF")).exists()) {
							d.justDownloadRes("natives");
						}
						} else if(os.equals(EnumOS.linux)) {
						if(!(new File(this.libDir + "natives")).exists() || !(new File(this.libDir + "natives" + fs + "libopenal64.so")).exists() || !(new File(this.libDir + "natives" + fs + "libopenal.so")).exists() || !(new File(this.libDir + "natives" + fs + "libjinput-linux.so")).exists() || !(new File(this.libDir + "natives" + fs + "libjinput-linux64.so")).exists() || !(new File(this.libDir + "natives" + fs + "liblwjgl.so")).exists() || !(new File(this.libDir + "natives" + fs + "liblwjgl64.so")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "LWJGL.RSA")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "LWJGL.SF")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "MANIFEST.MF")).exists()) {
							d.justDownloadRes("natives");
						}
						} else if(os.equals(EnumOS.solaris)) {
						if(!(new File(this.libDir + "natives")).exists() || !(new File(this.libDir + "natives" + fs + "libopenal.so")).exists() || !(new File(this.libDir + "natives" + fs + "liblwjgl.so")).exists() || !(new File(this.libDir + "natives" + fs + "liblwjgl64.so")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "LWJGL.RSA")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "LWJGL.SF")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF/MANIFEST.MF")).exists()) {
							d.justDownloadRes("natives");
						}
						} else if(os.equals(EnumOS.macos)) {
						if(!(new File(this.libDir + "natives")).exists() || !(new File(this.libDir + "natives" + fs + "openal.dylib")).exists() || !(new File(this.libDir + "natives" + fs + "liblwjgl.jnilib")).exists() || !(new File(this.libDir + "natives" + fs + "libjinput-osx.jnilib")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "LWJGL.RSA")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "LWJGL.SF")).exists() || !(new File(this.libDir + "natives" + fs + "META-INF" + fs + "MANIFEST.MF")).exists()) {
							d.justDownloadRes("natives");
						}
						} 
						if(this.checkUpdate()){
							d.justDownloadRes("server");
							d.justDownloadRes("client");
						} else {
						if(!(new File(this.jarsDir, "modmycrft.jar")).exists()) {
							d.justDownloadRes("client");
						}
						}
					if(os.equals(EnumOS.linux)) {
						if(System.getProperty("sun.arch.data.model").equals("32")) {
							(new File(this.libDir + "natives" + fs + "libopenal64.so")).delete();
							(new File(this.libDir + "natives" + fs + "libjinput-linux64.so")).delete();
							(new File(this.libDir + "natives" + fs + "liblwjgl64.so")).delete();
						} else {
							(new File(this.libDir + "natives" + fs + "libopenal.so")).delete();
							(new File(this.libDir + "natives" + fs + "libjinput-linux.so")).delete();
							(new File(this.libDir + "natives" + fs + "liblwjgl.so")).delete();
						}
					} else if(os.equals(EnumOS.solaris)) {
						if(System.getProperty("sun.arch.data.model").equals("32")) {
							(new File(this.libDir + "natives" + fs + "liblwjgl64.so")).delete();
						} else {
							(new File(this.libDir + "natives" + fs + "liblwjgl.so")).delete();
						}
					}
					List<String> args = new ArrayList<String>();
					args.add(this.java);
					args.add("-Xms" + this.minRam + "M");
					args.add("-Xmx" + this.maxRam + "M");
					if(os.equals(EnumOS.solaris)) {
						args.add("-cp"); 
						args.add("\"" + this.jarsDir + "" + "modmycrft.jar:" + this.libDir + "lwjgl.jar:" + this.libDir + "lwjgl_util.jar:" + this.libDir + "jinput.jar\"");
					} else {
						args.add("-cp"); 
						args.add("\"" + this.jarsDir + "" + "modmycrft.jar;" + this.libDir + "lwjgl.jar;" + this.libDir + "lwjgl_util.jar;" + this.libDir + "jinput.jar\"");
					}
					String[] otherArgs = this.javaArgs.split(" ");
					for(String arg : otherArgs) {
						args.add(arg);
					}
					args.add("-Djava.library.path=" + libDir + "natives" + fs);
					args.add("net.minecraft.client.Minecraft");
					args.add(this.username);
					args.add(this.sessionID);
					if(this.connectToServer) {
						args.add(this.defaultServer);
					} 
					if(this.isGameDirNotDefault){
						args.add("McDir=" + gameDir);
					} 
					ProcessBuilder builder = new ProcessBuilder(args);
					Process process = builder.start();
					this.clientConsole(process, this.disableConsole);
					while(process.isAlive()) {}
						this.interrupt();
					} catch(IOException e) {
						e.printStackTrace();
					Gui.showError("Error with starting game: IOException!");
					}
				};
				Thread gameThread = new Thread(threadTask);
				gameThread.setName("ModMyCrftGame");
				gameThread.start();
		}
	}
	private void clientConsole(Process proc, boolean disableConsole) {
		BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new
				InputStreamReader(proc.getErrorStream()));
		new GuiConsole(stdInput, stdError, proc, disableConsole);
	}
}
