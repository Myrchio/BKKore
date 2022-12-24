package it.blastmc;

import it.blastmc.commands.BKKCommands;
import it.blastmc.commands.FallenPlayerCommands;
import it.blastmc.features.FallenPlayerList;
import it.blastmc.features.listeners.BlockTimedListener;
import it.blastmc.features.listeners.NoFirstFallDamageListener;
import it.blastmc.hook.PlaceHolderAPIHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main extends JavaPlugin {
    private static Main instance;
    private FileConfiguration msgConfig;
    public final FallenPlayerList fallenPlayerList = new FallenPlayerList();
    public Main() {
        instance = this;
    }
    @Override
    public void onEnable() {
        System.out.println(getPrefix() + "Plugin abilitato con successo!");
        getConfig().options().copyDefaults(true);
        saveConfig();
        msgConfig = loadCustomConfig("messages.yml", new File(getDataFolder(), "messages.yml"));
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
            System.out.println(ChatColor.GREEN + "+ No first fall damage");
        } else {
            System.out.println(ChatColor.RED + "- No first fall damage");
        }
    }
    private void getCommands(){
        new FallenPlayerCommands();
        new BKKCommands();
    }

    public FileConfiguration loadCustomConfig(String resourceName, File out) {
        try {
            InputStream in = getResource(resourceName);

            if (!out.exists()){
                    getDataFolder().mkdir();
                    out.createNewFile();
            }
            FileConfiguration file = YamlConfiguration.loadConfiguration(out);
            if (in!=null){
                InputStreamReader inReader = new InputStreamReader(in);
                file.setDefaults(YamlConfiguration.loadConfiguration(inReader));
                file.options().copyDefaults(true);
                    file.save(out);
            }
            return file;

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        msgConfig = loadCustomConfig("messages.yml", new File(getDataFolder(), "messages.yml"));
    }

    public String getPrefix(){
        return getMessages().getString("messages.prefix");
    }

    public FileConfiguration getMessages(){
        if (this.msgConfig == null) {
            this.reloadConfig();
        }
        return this.msgConfig;
    }

    public static Main getInstance() {return instance;}
}