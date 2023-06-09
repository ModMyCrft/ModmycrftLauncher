package net.modmycrft.launcher.main;

import java.io.*;
import java.net.URL;


public class Game extends Thread implements Runnable{
	private boolean offlineMode;
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
	private boolean isGameDirNotDefault;
	private boolean connectToServer;
	private String defaultServer;
	private Downloader d;
	public Game(boolean offlineMode, String username, String password, int minRam, int maxRam, boolean disableConsole, String javaArgs, String jarsDir, String libDir, String gameDir, String java, String defaultServer, boolean connectToServer) {
		this.offlineMode = offlineMode;
		this.username = username;
		this.password = password;
		this.minRam = minRam;
		this.maxRam = maxRam;
		this.disableConsole = disableConsole;
		this.javaArgs = javaArgs;
		this.java = java;
		this.gameDir = gameDir;
		this.jarsDir = jarsDir + "/";
		this.libDir = libDir + "/";
		if(this.gameDir.equals("default")) {
			this.isGameDirNotDefault = false;
			this.gameDir = "";
		}
		if(this.libDir.equals("default/")) {
			this.libDir = System.getProperty("user.dir") + "/lib/";
		}
		if(this.jarsDir.equals("default/")) {
			this.jarsDir = System.getProperty("user.dir") + "/jars/";
		}
		d = new Downloader(this.libDir, this.jarsDir);
		this.connectToServer = connectToServer;
		this.defaultServer = defaultServer;
	}
	public Game(int minRam, int maxRam, String jarsDir, String java) {
		this.minRam = minRam;
		this.maxRam = maxRam;
		this.java = java;
		this.jarsDir = jarsDir + "/";
		if(this.jarsDir.equals("default/")) {
			this.jarsDir = System.getProperty("user.dir") + "/jars/";
		}
		d = new Downloader("", this.jarsDir);
	}
	public void startGameServer(){
		if (this.username == null && this.password == null) {
		if(this.checkUpdate()){
			d.justDownloadRes("server");
			d.justDownloadRes("client");
		} else if(!(new File(jarsDir, "modmycrft_server.jar")).exists()) {
			d.justDownloadRes("server");
		}
		Runnable threadTask = () -> {
			try {
					System.out.println(maxRam);
					Process process = Runtime.getRuntime().exec(this.java +  " -Xms" + this.minRam + "M -Xmx" + this.maxRam + "M -jar " + this.jarsDir + "modmycrft_server.jar");
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
					if(!(new File(this.libDir + "natives/")).exists() || !(new File(this.libDir + "natives/OpenAL32.dll")).exists() || !(new File(this.libDir + "natives/OpenAL64.dll")).exists() || !(new File(this.libDir + "natives/jinput-dx8.dll")).exists() || !(new File(this.libDir + "natives/jinput-dx8_64.dll")).exists() || !(new File(this.libDir + "natives/jinput-raw.dll")).exists() || !(new File(this.libDir + "natives/jinput-raw_64.dll")).exists() || !(new File(this.libDir + "natives/lwjgl.dll")).exists() || !(new File(this.libDir + "natives/lwjgl64.dll")).exists() || !(new File(this.libDir + "natives/META-INF")).exists() || !(new File(this.libDir + "natives/META-INF/LWJGL.RSA")).exists() || !(new File(this.libDir + "natives/META-INF/LWJGL.SF")).exists() || !(new File(this.libDir + "natives/META-INF/MANIFEST.MF")).exists()) {
						d.justDownloadRes("natives");
					}
					} else if(os.equals(EnumOS.linux)) {
					if(!(new File(this.libDir + "natives")).exists() || !(new File(this.libDir + "natives/libopenal64.so")).exists() || !(new File(this.libDir + "natives/libopenal.so")).exists() || !(new File(this.libDir + "natives/libjinput-linux.so")).exists() || !(new File(this.libDir + "natives/libjinput-linux64.so")).exists() || !(new File(this.libDir + "natives/liblwjgl.so")).exists() || !(new File(this.libDir + "natives/liblwjgl64.so")).exists() || !(new File(this.libDir + "natives/META-INF")).exists() || !(new File(this.libDir + "natives/META-INF/LWJGL.RSA")).exists() || !(new File(this.libDir + "natives/META-INF/LWJGL.SF")).exists() || !(new File(this.libDir + "natives/META-INF/MANIFEST.MF")).exists()) {
						d.justDownloadRes("natives");
					}
					} else if(os.equals(EnumOS.solaris)) {
					if(!(new File(this.libDir + "natives")).exists() || !(new File(this.libDir + "natives/libopenal.so")).exists() || !(new File(this.libDir + "natives/liblwjgl.so")).exists() || !(new File(this.libDir + "natives/liblwjgl64.so")).exists() || !(new File(this.libDir + "natives/META-INF")).exists() || !(new File(this.libDir + "natives/META-INF/LWJGL.RSA")).exists() || !(new File(this.libDir + "natives/META-INF/LWJGL.SF")).exists() || !(new File(this.libDir + "natives/META-INF/MANIFEST.MF")).exists()) {
						d.justDownloadRes("natives");
					}
					} else if(os.equals(EnumOS.macos)) {
					if(!(new File(this.libDir + "natives")).exists() || !(new File(this.libDir + "natives/openal.dylib")).exists() || !(new File(this.libDir + "natives/liblwjgl.jnilib")).exists() || !(new File(this.libDir + "natives/libjinput-osx.jnilib")).exists() || !(new File(this.libDir + "natives/META-INF")).exists() || !(new File(this.libDir + "natives/META-INF/LWJGL.RSA")).exists() || !(new File(this.libDir + "natives/META-INF/LWJGL.SF")).exists() || !(new File(this.libDir + "natives/META-INF/MANIFEST.MF")).exists()) {
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
					Runnable threadTask = () -> {
					try {
					StringBuilder processString = new StringBuilder(this.java + " -Xms" + this.minRam + "M -Xmx" + this.maxRam + "M " + this.javaArgs + " -Djava.library.path=" + libDir + "natives/ -cp \"" + this.jarsDir + "" + "modmycrft.jar;" + this.libDir + "lwjgl.jar;" + this.libDir + "lwjgl_util.jar;" + this.libDir + "jinput.jar\" net.minecraft.client.Minecraft "+this.username+ " 0");
					Process process = null;
					if(this.connectToServer) {
						processString.append(" " + this.defaultServer);
					} 
					if(this.isGameDirNotDefault){
						processString.append(" McDir=" + gameDir);
					} 
					process = Runtime.getRuntime().exec(processString.toString());
					Launcher launcher = new Launcher();
					if(!this.disableConsole) {
						launcher.clientConsole(process);
					}
					while(process.isAlive()) {}
						this.interrupt();
					} catch(IOException e) {
					Gui.showError("Error with starting game: IOException!");
					}
				};
				Thread gameThread = new Thread(threadTask);
				gameThread.setName("ModMyCrftGame");
				gameThread.start();
		}
	}
}
