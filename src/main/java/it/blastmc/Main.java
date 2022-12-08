package it.blastmc;

import it.blastmc.commands.FallenPlayersCommand;
import it.blastmc.features.FallenPlayerList;
import it.blastmc.features.listener.BlockTimedListener;
import it.blastmc.features.listener.NoFirstFallDamageListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    public final FallenPlayerList fallenPlayerList = new FallenPlayerList();
    public Main() {
        instance = this;
    }

    public static void main(){
        System.out.println();
    }
    @Override
    public void onEnable() {
        System.out.println("[EmeraldCore] Plugin abilitato con successo!");
        getConfig().options().copyDefaults(true);
        saveConfig();
        getEvents();
        getCommands();
    }
    @Override
    public void onDisable() {
        System.out.println("[EmeraldCore] Plugin disabilitato con successo!");
    }

    private void getEvents(){
        getServer().getPluginManager().registerEvents(new BlockTimedListener(), this);
        if (this.getConfig().getBoolean("no-first-fall-damage.enabled")){
            getServer().getPluginManager().registerEvents(new NoFirstFallDamageListener(), this);
            System.out.println("[EmeraldCore] + No first fall damage");
        } else {
            System.out.println("[EmeraldCore] - No first fall damage");
        }
    }
    private void getCommands(){
        this.getCommand("fplayers").setExecutor(new FallenPlayersCommand());
    }
    public static Main getInstance() {return instance;}
}