package net.modmycrft.launcher.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import net.modmycrft.launcher.log.ConsoleLogManager;

public class GuiConsole {
	private boolean displayConsole;
	public GuiConsole(BufferedReader stdInput, BufferedReader stdError, Process proc, boolean disableConsole) {
		this.displayConsole = !disableConsole;
		this.displayConsole(stdInput, stdError, proc);
	}
	private void displayConsole(BufferedReader stdInput, BufferedReader stdError, Process proc) {
		if(displayConsole) {
		JFrame consoleF = new JFrame();
		JTextArea consoleArea = new JTextArea();
		consoleArea.setBounds(15,15,432,575);
		consoleF.setLayout(new GridBagLayout());
        
		consoleArea = new JTextArea(5, 20);
		consoleArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleArea);
 
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
		consoleF.add(scrollPane,c);
		consoleF.setTitle("Client Console");
		consoleF.setSize(480,640);
		consoleF.setVisible(true);
		consoleF.setLocationRelativeTo(null);
		consoleF.setResizable(false);
		DefaultCaret caret = (DefaultCaret)consoleArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		consoleF.addWindowListener(new WindowAdapter() {
			@Override
	          public void windowClosing(WindowEvent windowEvent){
				consoleF.dispose();
		      }       
		});
		Logger logger = Logger.getLogger("Modmycrft");
		ConsoleLogManager.init();
		while(proc.isAlive()) {
		String s = null;
		try {
			while ((s = stdInput.readLine()) != null) {
				consoleArea.append("[LOG] " + s + "\n");
				logger.log(Level.INFO, s);
			}
			while ((s = stdError.readLine()) != null) {
				Gui.showError(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		consoleF.dispose();
		} else {
		Logger logger = Logger.getLogger("Modmycrft");
		ConsoleLogManager.init();
		while(proc.isAlive()) {
		String s = null;
		try {
			while ((s = stdInput.readLine()) != null) {
				logger.log(Level.INFO, s);
			}
			while ((s = stdError.readLine()) != null) {
				Gui.showError(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		}
	}
}
