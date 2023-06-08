package net.modmycrft.launcher.main;

import java.io.IOException;
import java.io.File;
import java.lang.reflect.Field;

public class LauncherInit{
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if(!(new File(System.getProperty("user.dir") + "\\launcherLib", "discord-rpc.jar")).exists() || !(new File(System.getProperty("user.dir") + "\\launcherLib", "discord-rpc.dll")).exists()){
		Downloader d = new Downloader();
		d.justDownloadRes("discordRPC");
		}
		
		System.setProperty( "java.library.path", "launcherLib/" );

		Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
		fieldSysPath.setAccessible( true );
		fieldSysPath.set( null, null );
		Launcher.main(args);
	}
}