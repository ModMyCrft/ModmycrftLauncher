package net.modmycrft.launcher.main;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class LauncherDiscordRPC {
	private DiscordRPCThread discordRPCThread;
	public void start(){
		discordRPCThread.start();
	}
	public void update() {
		discordRPCThread = new DiscordRPCThread(getRPC());
	}
	private static DiscordRPC getRPC() {
		DiscordRPC discordRPC = DiscordRPC.INSTANCE;
		String applicationId = "1108056681650335774";
		String steamId = "";
		DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
		eventHandlers.ready = (discordUser) -> System.out.println("EventHandlers ready.");
		discordRPC.Discord_Initialize(applicationId, eventHandlers, true, steamId);
		DiscordRichPresence rpc = new DiscordRichPresence();
		rpc.startTimestamp = System.currentTimeMillis() / 1000;
		rpc.details = "";
		rpc.state = "";
		discordRPC.Discord_UpdatePresence(rpc);
		return discordRPC;
	}
	public void shutdownThread() {
		discordRPCThread.shutdownRPC();
	}
}
