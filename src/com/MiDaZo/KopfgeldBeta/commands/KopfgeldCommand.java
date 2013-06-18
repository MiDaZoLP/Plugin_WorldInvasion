package com.MiDaZo.KopfgeldBeta.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.MiDaZo.KopfgeldBeta.KopfgeldBeta;

public class KopfgeldCommand implements CommandExecutor {
	
	public KopfgeldCommand(KopfgeldBeta plugin) {
		
		this.plugin = plugin;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		if(sender instanceof Player) {
			//VARIABLEN
			String sanzahl = " " + args[1] + " ";
			String Waerung = " " + KopfgeldBeta.economy.currencyNamePlural() + " ";
			String lspieler = " " + args[0] + " ";
			String StringAnzahl = args[1];
			String Spieler = args[0];
            int Anzahl = Integer.parseInt(StringAnzahl);
            int ConfigAnzahl = plugin.getConfig().getInt("Config.Spieler." + Spieler);
            //------------------
			Player p = (Player)sender;
			if(args.length == 0) {
				p.sendMessage(ChatColor.GOLD + "§l-----Kopfgeld - Hilfe-----");
				p.sendMessage(ChatColor.GOLD + "/kopfgeld" + ChatColor.BLACK + " - " + ChatColor.WHITE + "Zeigt die Hilfe");
				p.sendMessage(ChatColor.GOLD + "/kopfgeld [Spieler] [Preis]" + ChatColor.BLACK + " - " + ChatColor.WHITE + "Setzt [Preis] Kopfgeld auf [Spieler]");
				p.sendMessage(ChatColor.GOLD + "/kopfgeld [Spieler]" + ChatColor.BLACK + " - " + ChatColor.WHITE + " Zeigt wie viel Kopfgeld auf [Spieler] gesetzt ist");
				return true;
			}			
			else if(args.length == 1) {
				String arg1 = args[0];
				if(plugin.getConfig().isSet("Config.Spieler" + arg1)) {
					p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] " + ChatColor.GOLD + "Auf" + lspieler + "ist" + ConfigAnzahl + Waerung + "Kopfgeld gesetzt");
				    return true;
				} else {
					p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] " + ChatColor.RED + "Auf " + ChatColor.RED + arg1 + ChatColor.RED + "Ist kein Kopfgeld gesetzt!");
					return true;
				}
			}
			else if(args.length > 3) {
				p.sendMessage(ChatColor.GOLD + "[Kopfgeld]" + ChatColor.RED + "Zu viele Argumente");
				return false;
			}
			else if(args.length == 2) {
                if(KopfgeldBeta.economy.hasAccount(p.getName())) {
                	if(KopfgeldBeta.economy.getBalance(p.getName()) == Anzahl) {
                		if(p.getName() == Spieler) {
                			p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] " + ChatColor.RED + "Du kannst nicht auf dich selber Kopfgeld setzen!");
                			return true;
                		}
                		if(plugin.getConfig().isSet("Config.Spieler." + Spieler)) {
                			int GesamtZahl = ConfigAnzahl + Anzahl;
                			plugin.getConfig().set("Config.Spieler." + Spieler, GesamtZahl);
                			plugin.getConfig().options().copyDefaults(true);
                			plugin.saveConfig();
                			plugin.reloadConfig();
                			p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] " + ChatColor.GOLD + "Du hast das Kopfgeld von" + lspieler + "um" + sanzahl + "erhöht!");
                			plugin.getServer().broadcastMessage(ChatColor.GOLD + "§l[Kopfgeld] " + p.getDisplayName() + ChatColor.GOLD + "hat das Kopfgeld von" + lspieler + "um" + sanzahl + "erhöht!");
                			plugin.getServer().broadcastMessage(ChatColor.GOLD + "§l[Kopfgeld] Auf" + lspieler + "ist nun " + plugin.getConfig().getInt("Config.Spieler." + Spieler) + Waerung + "Kopfgeld gesetzt!");
                			KopfgeldBeta.economy.withdrawPlayer(p.getName(), Anzahl);
                			return true;
                		} else {
                			if(Anzahl > 100) {
                			    plugin.getConfig().addDefault("Config.Spieler" + Spieler, Anzahl);
                			    plugin.getConfig().options().copyDefaults(true);
                			    plugin.saveConfig();
                			    plugin.reloadConfig();
                		        KopfgeldBeta.economy.withdrawPlayer(Spieler, Anzahl);
                			    plugin.getServer().broadcastMessage(ChatColor.GOLD + "§l[Kopfgeld] " + p.getDisplayName() + ChatColor.GOLD + " hat" + sanzahl + Waerung + "Kopfgeld auf" + lspieler + "gesetzt");
                			    p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] " + ChatColor.RED + "Du hast auf" + lspieler + sanzahl + Waerung + "gesetzt");
                			    return true;
                			} else {
                				p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] " + ChatColor.RED + "Du musst mindestens 100" + Waerung + "Kopfgeld setzen");
                				return true;
                			}
                		}
                	} else {
                		p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] " + ChatColor.RED + "Du hast nicht genug " + ChatColor.RED + Waerung + ChatColor.RED + "!");
                		return true;
                	}
                } else {
                	p.sendMessage(ChatColor.GOLD + "§l[Kopfgeld] " + ChatColor.RED + "Du hast kein Bankkonto!");
                	return true;
                }
			}
		}
		return false;
	}
    private KopfgeldBeta plugin;
}
