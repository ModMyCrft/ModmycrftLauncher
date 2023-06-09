package net.modmycrft.launcher.main;

import club.minnced.discord.rpc.DiscordRPC;

public class DiscordRPCThread extends Thread {
	DiscordRPC discordRPC;
	public DiscordRPCThread(DiscordRPC discordRPC){
		super("RPC-Callback-Handler");
		System.out.println("Starting discordRPC thread...");
		this.discordRPC = discordRPC;
	}
	@Override 
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			this.discordRPC.Discord_RunCallbacks();
			
			try {
				Thread.sleep(2000);
			} catch(InterruptedException i){
				
			}
		}
	}
	public void shutdownRPC() {
		System.out.println("Interrupting discordRPC thread...");
		this.discordRPC.Discord_ClearPresence();
		this.discordRPC.Discord_Shutdown();
		this.interrupt();
	}
}
