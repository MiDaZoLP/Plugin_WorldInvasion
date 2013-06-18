package com.MiDaZo.KopfgeldBeta;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.MiDaZo.KopfgeldBeta.commands.KopfgeldCommand;
import com.MiDaZo.KopfgeldBeta.commands.PlayerDeathListener;
import com.MiDaZo.KopfgeldBeta.commands.PlayerJoinListener;

import net.milkbowl.vault.economy.Economy;


public class KopfgeldBeta extends JavaPlugin {
//-------------------------------------------------------------------------------------------------------------//
	public static Economy economy = null;
	//ONENABLE
	public void onEnable() {
		loadConfig();
		this.loadCommands();
		this.setupEconomy();
	}
	//---------------
	
	//ONDISABLE
	public void onDisable() {
		this.saveConfig();
	}
	//---------------
	
	//ECONOMY//
	private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
	//---------------
	
	//CONFIG//
	private void loadConfig() {
		this.getConfig().createSection("Config.Spieler");
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	//---------------
	
	//EVENTSLADEN//
    public void registerEvents() {
	    pdl = new PlayerDeathListener(this);
	    pjl = new PlayerJoinListener(this);
	}
    //---------------
    
    //KOMMANDSLADEN//
    public void loadCommands() {
    	this.getCommand("kopfgeld").setExecutor(new KopfgeldCommand(this));
    }
	   private PlayerDeathListener pdl;
	   private PlayerJoinListener pjl;
//-------------------------------------------------------------------------------------------------------------//

}
