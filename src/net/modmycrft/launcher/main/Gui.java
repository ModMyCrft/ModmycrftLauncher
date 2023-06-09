package net.modmycrft.launcher.main;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import net.modmycrft.launcher.backgrounds.Background;
import net.modmycrft.launcher.log.ConsoleLogManager;
public class Gui extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -528992046303642919L;
	protected Launcher launcher = new Launcher();
	int MaxRam;
	int MaxRamServ;
	boolean discordRPCUse;
	String minecraftDir;
	String java;
	String javaArgs;
	String defaultServer;
	boolean connectToServer;
	String jarsDir;
	String libDir;
	boolean disableConsole;
	HashMap<String, Object> options;
	HashMap<String, Object> serverProperties;
	String username;
	boolean serverOnlineMode;
	int serverPort;
	String serverIP;
	int serverMaxPlayers;
	String serverLevelName;
	public Gui() {
		try {
			this.initGui();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initGui() throws IOException {
		JFrame frame =new JFrame("Modmycrft Launcher");
		frame.setContentPane(new Background());
		Container cont = getContentPane();
		JButton butt1 =new JButton("Start Game");
		JLabel label4 =new JLabel("Username");
		label4.setForeground(new Color(0xffffff));
		JTextField userField = new JTextField();
		JButton butt2 =new JButton("Options");
		JButton butt3 =new JButton("Start Server");
		JButton butt5 =new JButton("Server Properties");
		cont.setLayout(new FlowLayout());
	    cont.add(butt1);
	    cont.add(butt2);
	    cont.add(butt3); 
	    cont.add(userField);
	    cont.add(butt5);
		try{
			serverProperties = reloadOptions("ServerProperties");
		} catch(Exception e){
			serverProperties = null;
		}
		if (serverProperties != null) {
			serverOnlineMode = (boolean) serverProperties.get("onlineMode");
			serverLevelName = (String) serverProperties.get("levelName");
			serverIP = (String) serverProperties.get("serverIP");
			serverPort = (int) serverProperties.get("serverPort");
			serverMaxPlayers = (int) serverProperties.get("maxPlayers");
		} else {
			serverOnlineMode = false;
			serverPort = 25565;
			serverIP = "";
			serverMaxPlayers = 20;
			serverLevelName = "world";
		}
		try {
			options = reloadOptions("OptionsLauncher");
		} catch(Exception e){
			options = null;
		}
		if(options != null) {
			MaxRam = (int) options.get("MaxRAM");
			MaxRamServ = (int) options.get("ServerMaxRAM");
			username = (String) options.get("Username");
			disableConsole = (boolean) options.get("disableConsole");
			discordRPCUse = (boolean) options.get("DiscordRPCUse");
			minecraftDir = (String) options.get("MinecraftDirectory");
			java = (String) options.get("Java");
			javaArgs = (String) options.get("JavaArgs");
			defaultServer = (String) options.get("DefaultServer");
			connectToServer = (boolean) options.get("ConnectToServer");
			jarsDir = (String) options.get("JarsDirectory");
			libDir = (String) options.get("LibDirectory");
		}
		userField.setText(username);
     	userField.setBounds(680,350,150, 20);
		butt1.setBounds(680,440,150, 70);
		label4.setBounds(680, 330, 150, 20);
		butt2.setBounds(680,400,150, 30);
		butt3.setBounds(680,250,150, 70);
		butt5.setBounds(680,210,150, 30);
	    butt2.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frameOptions =new JFrame("Launcher Options");
				JTextField memoryField = new JTextField();
				JLabel label2 =new JLabel("MB(CLIENT-RAM)");
				JButton butt4 =new JButton("Apply and quit");
				JTextField memoryField1 = new JTextField();
				JLabel label3 =new JLabel("MB(SERVER-RAM)");
				JLabel javaArgsLabel =new JLabel("Java Arguments");
				JButton browseJava = new JButton("Browse");
				JButton browseMinecraftDir = new JButton("Browse");
				JButton browseJarsDir = new JButton("Browse");
				JButton browseLibDir = new JButton("Browse");
				JTextField libDirField = new JTextField();
				JTextField jarsDirField = new JTextField();
				JTextField minecraftDirField = new JTextField();
				JTextField javaField = new JTextField();
				JTextField defaultServerField = new JTextField();
				JCheckBox disableConsoleChBox = new JCheckBox("Disable Client Console", disableConsole);
				JCheckBox connectToServerChBox = new JCheckBox("Connect to server on client start", connectToServer);
				JCheckBox discordRPCChBox = new JCheckBox("DiscordRPC (Only on Windows and Linux)", discordRPCUse);
				JLabel javaFieldLabel = new JLabel("Path to java file");
				JLabel minecraftFieldLabel = new JLabel("Path to minecraft directory");
				JLabel jarsFieldLabel = new JLabel("Path to jars directory");
				JLabel libFieldLabel = new JLabel("Path to libraries directory");
				JLabel defaultServerFieldLabel = new JLabel("Server IP if connecting on client start");
				JTextField javaArgsField = new JTextField();
				HashMap<String, Object> options = null;
				try {
					options = reloadOptions("OptionsLauncher");
				} catch(Exception e1){
					options = null;
				}
				if(options != null) {
					MaxRam = (int) options.get("MaxRAM");
					MaxRamServ = (int) options.get("ServerMaxRAM");
					username = (String) options.get("Username");
					disableConsole = (boolean) options.get("disableConsole");
					discordRPCUse = (boolean) options.get("DiscordRPCUse");
					minecraftDir = (String) options.get("MinecraftDirectory");
					java = (String) options.get("Java");
					javaArgs = (String) options.get("JavaArgs");
					defaultServer = (String) options.get("DefaultServer");
					connectToServer = (boolean) options.get("ConnectToServer");
					jarsDir = (String) options.get("JarsDirectory");
					libDir = (String) options.get("LibDirectory");
				}
				if(options != null) {
					memoryField.setText(MaxRam + "");
					memoryField1.setText(MaxRamServ + "");
					libDirField.setText(libDir);
					minecraftDirField.setText(minecraftDir);
					jarsDirField.setText(jarsDir);
					javaField.setText(java);
					javaArgsField.setText(javaArgs);
					defaultServerField.setText(defaultServer);
				}
				memoryField.setBounds(30,30,70, 20);
				label2.setBounds(110,30,400, 20);
				butt4.setBounds(235,360,150, 40);
				memoryField1.setBounds(30,60,70, 20);
				label3.setBounds(110,60,400, 20);
				disableConsoleChBox.setBounds(26,90,280, 20);
				browseJava.setBounds(26,120,80, 20);
				browseMinecraftDir.setBounds(26,150,80, 20);
				browseJarsDir.setBounds(26,180,80, 20);
				browseLibDir.setBounds(26,210,80, 20);
				defaultServerField.setBounds(26,240,150, 20);
				javaField.setBounds(116,120,150, 20);
				jarsDirField.setBounds(116,180,150, 20);
				minecraftDirField.setBounds(116,150,150, 20);
				libDirField.setBounds(116,210,150, 20);
				connectToServerChBox.setBounds(26,270,240, 20);
				discordRPCChBox.setBounds(306,270,240, 20);
				javaFieldLabel.setBounds(286,120,150, 20);
				minecraftFieldLabel.setBounds(286,150,150, 20);
				libFieldLabel.setBounds(286,210,150, 20);
				jarsFieldLabel.setBounds(286,180,150, 20);
				defaultServerFieldLabel.setBounds(196,240,190, 20);
				javaArgsField.setBounds(26, 300, 300, 20);
				javaArgsLabel.setBounds(346, 300, 150, 20);
				if(!Launcher.rpc) {
					discordRPCChBox.setEnabled(false);
				}
				if(connectToServerChBox.isSelected()) {
					defaultServerField.setEnabled(true);
				} else {
					defaultServerField.setEnabled(false);
				}
				connectToServerChBox.addItemListener(new ItemListener() {
				    @Override
				    public void itemStateChanged(ItemEvent e) {
				        if(e.getStateChange() == ItemEvent.SELECTED) {
				        	defaultServerField.setEnabled(true);
				        } else {
				        	defaultServerField.setEnabled(false);
				        };
				    }
				});
				frameOptions.setSize(600,460);
				frameOptions.setLayout(null);
				frameOptions.setVisible(true);
				frameOptions.setLocationRelativeTo(null);
				frameOptions.setResizable(false);
				
				frameOptions.addWindowListener(new WindowAdapter() {
			          public void windowClosing(WindowEvent windowEvent){
			        	  frameOptions.dispose();
			          }        
			       });
				frameOptions.add(label2);
				frameOptions.add(memoryField);
				frameOptions.add(butt4);
				frameOptions.add(label3);
				frameOptions.add(memoryField1);
				frameOptions.add(browseJava);
				frameOptions.add(javaField);
				frameOptions.add(jarsDirField);
				frameOptions.add(minecraftDirField);
				frameOptions.add(libDirField);
				frameOptions.add(browseMinecraftDir);
				frameOptions.add(browseJarsDir);
				frameOptions.add(browseLibDir);
				frameOptions.add(minecraftFieldLabel);
				frameOptions.add(javaFieldLabel);
				frameOptions.add(jarsFieldLabel);
				frameOptions.add(libFieldLabel);
				frameOptions.add(defaultServerField);
				frameOptions.add(disableConsoleChBox);
				frameOptions.add(defaultServerFieldLabel);
				frameOptions.add(connectToServerChBox);
				frameOptions.add(discordRPCChBox);
				frameOptions.add(javaArgsField);
				frameOptions.add(javaArgsLabel);
			    butt4.addActionListener(new ActionListener(){  
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							launcher.setOptionsToFile(
									Integer.parseInt(memoryField.getText()),
									userField.getText(),
									Integer.parseInt(memoryField1.getText()),
									disableConsoleChBox.isSelected(),
									discordRPCChBox.isSelected(),
									minecraftDirField.getText(),
									javaField.getText(),
									jarsDirField.getText(),
									libDirField.getText(),
									connectToServerChBox.isSelected(),
									defaultServerField.getText(),
									javaArgsField.getText()
									);
						} catch (IOException e1) {
							e1.printStackTrace();	
							return;
							} catch (NumberFormatException e1) {
							label2.setText("MB(CLIENT-RAM) BAD INPUT");
							label3.setText("MB(SERVER-RAM) BAD INPUT");
							return;
						}
						optionsSet();
						frameOptions.dispose();
					}  
			     });
			    browseJava.addActionListener(new ActionListener() {
			    	@Override
			    	public void actionPerformed(ActionEvent e) {
			    		Runnable r = new Runnable() {

			    			@Override
			    			public void run() {
			    			    JFileChooser jfc = new JFileChooser();
			    			    if( jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
			    			        File selected = jfc.getSelectedFile();
			    			        javaField.setText(selected.getAbsolutePath());
			    			    }
			    			}
			    			};
			    			SwingUtilities.invokeLater(r);
			    
			    		}
			    	});
			    browseMinecraftDir.addActionListener(new ActionListener() {
			    	@Override
			    	public void actionPerformed(ActionEvent e) {
			    		Runnable r = new Runnable() {

			    			@Override
			    			public void run() {
			    			    JFileChooser jfc = new JFileChooser();
			    			    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    			    if( jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
			    			        File selected = jfc.getSelectedFile();
			    			        minecraftDirField.setText(selected.getAbsolutePath());
			    			    }
			    			}
			    			};
			    			SwingUtilities.invokeLater(r);
			    
			    		}
			    	});
			    browseLibDir.addActionListener(new ActionListener() {
			    	@Override
			    	public void actionPerformed(ActionEvent e) {
			    		Runnable r = new Runnable() {

			    			@Override
			    			public void run() {
			    			    JFileChooser jfc = new JFileChooser();
			    			    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    			    if( jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
			    			        File selected = jfc.getSelectedFile();
			    			        libDirField.setText(selected.getAbsolutePath());
			    			    }
			    			}
			    			};
			    			SwingUtilities.invokeLater(r);
			    
			    		}
			    	});
			    browseJarsDir.addActionListener(new ActionListener() {
			    	@Override
			    	public void actionPerformed(ActionEvent e) {
			    		Runnable r = new Runnable() {

			    			@Override
			    			public void run() {
			    			    JFileChooser jfc = new JFileChooser();
			    			    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    			    if( jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
			    			        File selected = jfc.getSelectedFile();
			    			        jarsDirField.setText(selected.getAbsolutePath());
			    			    }
			    			}
			    			};
			    			SwingUtilities.invokeLater(r);
			    
			    		}
			    	});
			}
			public void optionsSet(){
				try {
					options = reloadOptions("OptionsLauncher");
				} catch(Exception e1){
					options = null;
				}
				if(options != null) {
					boolean b1 = false;
					if(!discordRPCUse) {
						b1 = true;
					}
					MaxRam = (int) options.get("MaxRAM");
					MaxRamServ = (int) options.get("ServerMaxRAM");
					username = (String) options.get("Username");
					disableConsole = (boolean) options.get("disableConsole");
					discordRPCUse = (boolean) options.get("DiscordRPCUse");
					minecraftDir = (String) options.get("MinecraftDirectory");
					java = (String) options.get("Java");
					javaArgs = (String) options.get("JavaArgs");
					defaultServer = (String) options.get("DefaultServer");
					connectToServer = (boolean) options.get("ConnectToServer");
					jarsDir = (String) options.get("JarsDirectory");
					libDir = (String) options.get("LibDirectory");
					if(b1 && discordRPCUse) {
						launcher.updateDiscordRPCThread();
						launcher.startRPC();
					} else if (!b1 && !discordRPCUse){
						launcher.shutdownRPC();
					}
				}
			}
	     });
	    butt5.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frameOptions =new JFrame("Server Properties");
				JCheckBox servModeChBox = new JCheckBox("Online-Mode", serverOnlineMode);
				JButton butt6 =new JButton("Apply and quit");
				JTextField serverPortField = new JTextField(serverPort);
				JLabel label5 =new JLabel("Server Port");
				JTextField serverMaxPlayersField = new JTextField();
				JTextField serverIPField = new JTextField(serverIP);
				JTextField serverLevelNameField = new JTextField();
				JLabel label6 =new JLabel("Max Players");
				JLabel label7 =new JLabel("Level Name");
				JLabel label8 =new JLabel("Server IP (Not necessary)");
				butt6.setBounds(235,360,150, 40);
				servModeChBox.setBounds(26,90,100, 20);
				label5.setBounds(110,210,400, 20);
				label6.setBounds(100,120,400, 20);
				label7.setBounds(160,180,400, 20);
				label8.setBounds(160,150,400, 20);
				serverMaxPlayersField.setBounds(30, 120, 60, 20);
				serverIPField.setBounds(30, 150, 120, 20);
				serverLevelNameField.setBounds(30, 180, 120, 20);
				serverPortField.setBounds(30, 210, 60, 20);
				frameOptions.setSize(600,460);
				frameOptions.setAlwaysOnTop(true);
				frameOptions.setLayout(null);
				frameOptions.setVisible(true);
				frameOptions.setLocationRelativeTo(null);
				frameOptions.setResizable(false);
				
				frameOptions.addWindowListener(new WindowAdapter() {
			          public void windowClosing(WindowEvent windowEvent){
			        	  frameOptions.dispose();
			          }        
			       });
				frameOptions.add(label5);
				frameOptions.add(servModeChBox);
				frameOptions.add(butt6);
				frameOptions.add(label6);
				frameOptions.add(label7);
				frameOptions.add(label8);
				System.out.println(serverPort + " " + serverLevelName + " " + serverOnlineMode);
				frameOptions.add(serverPortField);
				frameOptions.add(serverLevelNameField);
				frameOptions.add(serverIPField);
				frameOptions.add(serverMaxPlayersField);
				servModeChBox.setSelected(serverOnlineMode);
				serverPortField.setText(serverPort + "");
				serverLevelNameField.setText(serverLevelName);
				serverIPField.setText(serverIP);
				serverMaxPlayersField.setText(serverMaxPlayers + "");
			    butt6.addActionListener(new ActionListener(){  
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (serverLevelNameField.getText().equals("") || serverLevelNameField.getText() == null) {
								label7.setText("Level Name BAD INPUT");
							}
							launcher.setServerPropertiesToFile("server.properties", serverLevelNameField.getText(),serverIPField.getText(),Integer.parseInt(serverPortField.getText()), Integer.parseInt(serverMaxPlayersField.getText()), servModeChBox.isSelected());
						} catch (IOException e1) {
							e1.printStackTrace();	
							return;
							} catch (NumberFormatException e1) {
							label5.setText("Server Port BAD INPUT");
							label6.setText("Max Players BAD INPUT");
							return;
						}
						optionsSet();
						frameOptions.dispose();
					}  
			     }); 
			}
			public void optionsSet(){
				try{
					serverProperties = reloadOptions("ServerProperties");
				} catch(Exception e){
					serverProperties = null;
				}
				if (serverProperties != null) {
					serverOnlineMode = (boolean) serverProperties.get("onlineMode");
					serverLevelName = (String) serverProperties.get("levelName");
					serverIP = (String) serverProperties.get("serverIP");
					serverPort = (int) serverProperties.get("serverPort");
					serverMaxPlayers = (int) serverProperties.get("maxPlayers");
				} else {
					serverOnlineMode = false;
					serverPort = 25565;
					serverIP = "";
					serverMaxPlayers = 20;
					serverLevelName = "world";
				}
			}
	     });
	    butt1.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!userField.getText().equals("") && !(userField.getText().length() < 3) && !(userField.getText().length() > 16) && !(userField.getText().split(" ").length > 1)) {
					try {
						launcher.setOptionsToFile(
								MaxRam,
								username,
								MaxRamServ,
								disableConsole,
								discordRPCUse,
								minecraftDir,
								java,
								jarsDir,
								libDir,
								connectToServer,
								defaultServer,
								javaArgs
								);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					launcher.launchGame(userField.getText(), MaxRam, disableConsole, javaArgs, minecraftDir, java, libDir, jarsDir, defaultServer, connectToServer);
				} else {
					label4.setText("Bad username");
				}
			}  
	     });  
	    butt3.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				launcher.launchServer(MaxRamServ, java, jarsDir);
			}  
	     });  
	     frame.addWindowListener(new WindowAdapter() {
	          public void windowClosing(WindowEvent windowEvent){
	        	 if(Launcher.rpc && discordRPCUse) {
				 launcher.shutdownRPC();
				 }
	             System.exit(0);
	          }        
	       });
		          
		frame.add(butt1);
		frame.add(userField);
		frame.add(butt2);
		frame.add(butt3);
		frame.add(butt5);
		frame.add(label4);
		      
		frame.setSize(865,560);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	public static HashMap<String, Object> reloadOptions(String type){
		HashMap<String, Object> hm = null;
			switch (type) {
				case "OptionsLauncher":
					hm = Launcher.getOptionsFromFile();
					break;
				case "ServerProperties":
					hm = Launcher.getServerPropertiesFromFile("server.properties");
					break;
			}
			return hm;
	}
	public static void showError(String message) {
		JOptionPane.showMessageDialog(null, message,"Launcher Error",JOptionPane.ERROR_MESSAGE);
	}
}
