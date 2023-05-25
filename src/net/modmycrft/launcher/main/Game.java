package net.modmycrft.launcher.main;

import java.io.File;
import java.io.IOException;


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
		this.jarsDir = jarsDir + "\\";
		this.libDir = libDir + "\\";
		if(this.gameDir.equals("default")) {
			this.isGameDirNotDefault = false;
			this.minecraftDir = "";
		}
		if(this.libDir.equals("default\\")) {
			this.libDir = System.getProperty("user.dir") + "\\lib\\";
		}
		if(this.jarsDir.equals("default\\")) {
			this.jarsDir = System.getProperty("user.dir") + "\\jars\\";
		}
		this.connectToServer = connectToServer;
		this.defaultServer = defaultServer;
	}
	public Game(int minRam, int maxRam, String jarsDir, String java) {
		this.minRam = minRam;
		this.maxRam = maxRam;
		this.java = java;
		this.jarsDir = jarsDir + "\\";
	}
	public void startGame() {
		Runnable threadTask = () -> {
			if (this.username == null && this.offlineMode == (Boolean) null && this.password == null && this.disableConsole == (Boolean)null) {
				try {
					System.out.println(maxRam);
					if(!(new File(jarsDir, "modmycrft_server.jar")).exists()) {
						Gui.showError("Error with starting game: server isn't exists!");
						return;
					}
					Process process = Runtime.getRuntime().exec(this.java +  " -Xms" + this.minRam + "m -Xmx" + this.maxRam + "m -jar " + this.jarsDir + "modmycrft_server.jar");
					while(process.isAlive()) {}
					this.interrupt();
			} catch(IOException e) {
				Gui.showError("Error with starting game: IOException!");
			}
				} else {      
				try {
					System.out.println(jarsDir);
					if(!(new File(this.jarsDir, "modmycrft.jar")).exists()) {
						Gui.showError("Error with starting game: client isn't exists!");
						return;
					}
					if(!(new File(this.libDir, "lwjgl.jar")).exists()) {
						Gui.showError("Error with starting game: lib isn't exists!");
						return;
					}
					if(!(new File(this.libDir, "jinput.jar")).exists()) {
						Gui.showError("Error with starting game: lib isn't exists!");
						return;
					}
					if(!(new File(this.libDir, "lwjgl_util.jar")).exists()) {
						Gui.showError("Error with starting game: lib isn't exists!");
						return;
					}
					Process process = null;
					System.out.println(this.java + " -Xms" + this.minRam + "m -Xmx" + this.maxRam + "m " + this.javaArgs + " -Djava.library.path=" + libDir + "natives\\ -cp \"" + this.jarsDir + "" + "modmycrft.jar;" + this.jarsDir + "lwjgl.jar;" + this.jarsDir + "lwjgl_util.jar;" + this.jarsDir + "jinput.jar\" net.minecraft.client.Minecraft "+this.username+ " 0");
					if(this.connectToServer && this.isMinecraftDirNotDefault) {
						process = Runtime.getRuntime().exec(this.java + " -Xms" + this.minRam + "m -Xmx" + this.maxRam + "m " + this.javaArgs + " -Djava.library.path=" + libDir + "natives\\ -cp \"" + this.jarsDir + "" + "modmycrft.jar;" + this.libDir + "lwjgl.jar;" + this.libDir + "lwjgl_util.jar;" + this.libDir + "jinput.jar\" net.minecraft.client.Minecraft "+this.username+ " 0 " + this.defaultServer + "McDir=" + gameDir);
					} else if (this.connectToServer && !this.isMinecraftDirNotDefault){
						process = Runtime.getRuntime().exec(this.java + " -Xms" + this.minRam + "m -Xmx" + this.maxRam + "m " + this.javaArgs + " -Djava.library.path=" + libDir + "natives\\ -cp \"" + this.jarsDir + "" + "modmycrft.jar;" + this.libDir + "lwjgl.jar;" + this.libDir + "lwjgl_util.jar;" + this.libDir + "jinput.jar\" net.minecraft.client.Minecraft "+this.username+ " 0 " + this.defaultServer);
					} else if(this.isMinecraftDirNotDefault && !this.connectToServer){
						process = Runtime.getRuntime().exec(this.java + " -Xms" + this.minRam + "m -Xmx" + this.maxRam + "m " + this.javaArgs + " -Djava.library.path=" + libDir + "natives\\ -cp \"" + this.jarsDir + "" + "modmycrft.jar;" + this.libDir + "lwjgl.jar;" + this.libDir + "lwjgl_util.jar;" + this.libDir + "jinput.jar\" net.minecraft.client.Minecraft "+this.username+ " 0 " + "McDir=" + gameDir);
					} else {
						process = Runtime.getRuntime().exec(this.java + " -Xms" + this.minRam + "m -Xmx" + this.maxRam + "m " + this.javaArgs + " -Djava.library.path=" + libDir + "natives\\ -cp \"" + this.jarsDir + "" + "modmycrft.jar;" + this.libDir + "lwjgl.jar;" + this.libDir + "lwjgl_util.jar;" + this.libDir + "jinput.jar\" net.minecraft.client.Minecraft "+this.username+ " 0");
					}
					
				Launcher launcher = new Launcher();
				if(!this.disableConsole) {
					launcher.clientConsole(process);
				}
				while(process.isAlive()) {}
				this.interrupt();
				} catch(IOException e) {
					Gui.showError("Error with starting game: IOException!");
				}
				}
		};
		Thread gameThread = new Thread(threadTask);
		gameThread.setName("ModMyCrftGame");
		gameThread.start();
	}
}
