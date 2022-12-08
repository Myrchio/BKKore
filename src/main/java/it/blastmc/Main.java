package it.blastmc;

import it.blastmc.commands.FallenPlayersCommand;
import it.blastmc.features.FallenPlayerList;
import it.blastmc.features.listener.BlockTimedListener;
import it.blastmc.features.listener.NoFirstFallDamageListener;
import it.blastmc.hook.PlaceHolderAPIHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    public final FallenPlayerList fallenPlayerList = new FallenPlayerList();
    public final String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + ChatColor.BOLD + "BKKore" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE;
    public Main() {
        instance = this;
    }
    @Override
    public void onEnable() {
        System.out.println(prefix + "Plugin abilitato con successo!");
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
        System.out.println(prefix + "Plugin disabilitato con successo!");
    }

    private void getEvents(){
        getServer().getPluginManager().registerEvents(new BlockTimedListener(), this);
        if (this.getConfig().getBoolean("no-first-fall-damage.enabled")){
            getServer().getPluginManager().registerEvents(new NoFirstFallDamageListener(), this);
            System.out.println(ChatColor.GREEN + "+ No first fall damage");
        } else {
            System.out.println(ChatColor.RED + "- No first fall damage");
        }
    }
    private void getCommands(){
        this.getCommand("fplayers").setExecutor(new FallenPlayersCommand());
    }
    public static Main getInstance() {return instance;}
}