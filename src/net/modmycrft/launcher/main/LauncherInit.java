package net.modmycrft.launcher.main;

import java.io.File;
import java.lang.reflect.Field;

import javax.swing.UIManager;

public class LauncherInit{
	private static String fs = File.separator;
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exception3) {
		}
		EnumOS os = Launcher.getOs();
		File[] libs = new File[2];
		libs[0] = new File(System.getProperty("user.dir") + fs + "launcherLib", "discord-rpc.jar");
		if(os.equals(EnumOS.unknown)) {
			Gui.showError("You're using an unsupported operating system!\n"
					+ "Supported operating systems: MacOS, Windows, Linux, Solaris.");
		} else {
		if(os.equals(EnumOS.windows)) {
			libs[1] = new File(System.getProperty("user.dir") + fs + "launcherLib", "discord-rpc.dll");
		} else if(os.equals(EnumOS.linux)) {
			libs[1] = new File(System.getProperty("user.dir") + fs + "launcherLib", "discord-rpc.so");
		} else {
			Launcher.rpc = false;
			libs[1] = null;
		}
		if(libs[1] != null){
			if(!(libs[0]).exists() || !(libs[1]).exists()){
				Downloader d = new Downloader();
				if(os.equals(EnumOS.windows)) {
					d.justDownloadRes("discordRPC-Windows");
				} else if(os.equals(EnumOS.linux)) {
					d.justDownloadRes("discordRPC-Linux");
				}
			}
		}
		if(Launcher.rpc) {
		System.setProperty( "java.library.path", System.getProperty("user.dir") + fs + "launcherLib" + fs );
		
		try {
			Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
			fieldSysPath.setAccessible( true );
			fieldSysPath.set( null, null );
		} catch (Exception e) {
			Launcher.rpc = false;
			e.printStackTrace();
			if(e instanceof NoSuchFieldException) {
				Gui.showError("Oops!\nSometing went wrong with loading discordRPC!");
			} else {
				Gui.showError(e.toString());
			}
		}
		}
		Launcher.main(args);
		}
	}
}