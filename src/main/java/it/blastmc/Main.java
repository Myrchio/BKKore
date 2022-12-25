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
    public final CustomConfig messages = new CustomConfig("messages.yml");
    public final FallenPlayerList fallenPlayerList = new FallenPlayerList();
    public Main() {
        instance = this;
    }
    @Override
    public void onEnable() {
        System.out.println(getPrefix() + "Plugin abilitato con successo!");
        getConfig().options().copyDefaults(true);
        saveConfig();
        getEvents();
        getCommands();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderAPIHook().register();
        }
    }
    @Override
    public void onDisable() {
        System.out.println(getPrefix() + "Plugin disabilitato con successo!");
    }

    private void getEvents(){
        getServer().getPluginManager().registerEvents(new BlockTimedListener(), this);
        if (this.getConfig().getBoolean("no-first-fall-damage.enabled")){
            getServer().getPluginManager().registerEvents(new NoFirstFallDamageListener(), this);
            System.out.println("\\u001B[32m" + "+ No first fall damage");
        } else {
            System.out.println("\\u001B[31m" + "- No first fall damage");
        }
    }
    private void getCommands(){
        new FallenPlayerCommands();
        new BKKCommands();
    }

    public String getPrefix(){
        return messages.getCustomConfig().getString("messages.prefix");
    }

    public static Main getInstance() {return instance;}
}