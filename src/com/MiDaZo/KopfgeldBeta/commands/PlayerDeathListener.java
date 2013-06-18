package com.MiDaZo.KopfgeldBeta.commands;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.MiDaZo.KopfgeldBeta.KopfgeldBeta;

public class PlayerDeathListener implements Listener {
	
	public PlayerDeathListener(KopfgeldBeta plugin) {
		
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void Listener(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			Player p = event.getEntity();
			Location location = p.getLocation();
			Player killer = p.getKiller();
            if(plugin.getConfig().isSet("Config.Spieler." + p.getName() )) {
            	p.getWorld().playEffect(location , Effect.MOBSPAWNER_FLAMES, 1);
		    	p.getWorld().playSound(p.getLocation(), Sound.ORB_PICKUP, 5, 10);
            	killer.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] Auf " + p.getName() + "ist " + plugin.getConfig().getString("Config.Spieler." + p.getName()) + " " + KopfgeldBeta.economy.currencyNamePlural() + " Kopfgeld gesetzt!");
            	int Kopfgeld = plugin.getConfig().getInt("Config.Spieler." + p.getName());
            	KopfgeldBeta.economy.depositPlayer(killer.getName() , Kopfgeld);
            	killer.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] Du hast " + Kopfgeld + " " + KopfgeldBeta.economy.currencyNamePlural() + " bekommen!");
            	plugin.getConfig().set("Config.Spieler." + p.getName() , null);
            	plugin.getConfig().options().copyDefaults(true);
         	    plugin.saveConfig();
         	    plugin.reloadConfig();
            } else{}
        } else {}
	}
	private KopfgeldBeta plugin;
}
