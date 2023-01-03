package it.blastmc;

import it.blastmc.commands.BKKCommands;
import it.blastmc.commands.FallenPlayerCommands;
import it.blastmc.features.FallenPlayerList;
import it.blastmc.features.customConfig.CustomConfig;
import it.blastmc.features.listeners.BlockTimedListener;
import it.blastmc.features.listeners.NoFirstFallDamageListener;
import it.blastmc.hook.PlaceHolderAPIHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    public CustomConfig messages;
    public final FallenPlayerList fallenPlayerList = new FallenPlayerList();
    public Main() {
        instance = this;
    }
    @Override
    public void onEnable() {
        messages = new CustomConfig("messages.yml");
        getConfig().options().copyDefaults(true);
        saveConfig();
        getEvents();
        getCommands();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderAPIHook().register();
        }
        System.out.println(getPrefix() + "Plugin abilitato con successo!");
    }
    @Override
    public void onDisable() {
        System.out.println(getPrefix() + "Plugin disabilitato con successo!");
    }

    private void getEvents(){
        getServer().getPluginManager().registerEvents(new BlockTimedListener(), this);
        if (this.getConfig().getBoolean("no-first-fall-damage.enabled")){
            getServer().getPluginManager().registerEvents(new NoFirstFallDamageListener(), this);
            System.out.println("+ No first fall damage");
        } else {
            System.out.println("- No first fall damage");
        }
    }
    private void getCommands(){
        new FallenPlayerCommands();
        new BKKCommands();
    }

    public String getPrefix(){
        return messages.getCustomConfig().getString("messages.prefix");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        messages.loadCustomConfig();
    }

    public static Main getInstance() {return instance;}
}