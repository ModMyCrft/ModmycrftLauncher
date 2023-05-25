package net.modmycrft.launcher.log;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogManager {
	public static Logger logger = Logger.getLogger("ModMyCrft");

	public static void init() {
		ConsoleLogFormatter consoleLogFormatter0 = new ConsoleLogFormatter();
		logger.setUseParentHandlers(false);
		ConsoleHandler consoleHandler1 = new ConsoleHandler();
		consoleHandler1.setFormatter(consoleLogFormatter0);
		logger.addHandler(consoleHandler1);
		try {
			File logsDir = new File("logs");
			if(!logsDir.exists()) {
				logsDir.mkdir();			
			}
			FileHandler fileHandler2 = new FileHandler("logs\\modmycrft.log");
			fileHandler2.setFormatter(consoleLogFormatter0);
			logger.addHandler(fileHandler2);
		} catch (Exception exception3) {
			logger.log(Level.WARNING, "Failed to log to logs\\modmycrft.log", exception3);
		}

	}
}
