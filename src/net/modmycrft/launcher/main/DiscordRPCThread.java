package net.modmycrft.launcher.main;

import club.minnced.discord.rpc.DiscordRPC;

public class DiscordRPCThread extends Thread {
	DiscordRPC discordRPC;
	public DiscordRPCThread(DiscordRPC discordRPC){
		super("RPC-Callback-Handler");
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
		this.discordRPC.Discord_ClearPresence();
		this.discordRPC.Discord_Shutdown();
		this.interrupt();
	}
}
