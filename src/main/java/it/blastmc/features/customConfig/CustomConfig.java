package it.blastmc.features.customConfig;

import it.blastmc.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CustomConfig{
    private final String fileName;

    private final Plugin plugin = Main.getInstance();

    private FileConfiguration fileConfiguration;

    public CustomConfig(String fileName) {
        this.fileName = fileName;
        loadCustomConfig();
    }

    public void loadCustomConfig() {
        File out = new File(plugin.getDataFolder(), fileName);
        try {
            InputStream in = plugin.getResource(fileName);

            if (!out.exists()){
                plugin.getDataFolder().mkdir();
                out.createNewFile();
            }
            FileConfiguration file = YamlConfiguration.loadConfiguration(out);
            if (in!=null){
                InputStreamReader inReader = new InputStreamReader(in);
                file.setDefaults(YamlConfiguration.loadConfiguration(inReader));
                file.options().copyDefaults(true);
                file.save(out);
            }
            this.fileConfiguration = file;

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public FileConfiguration getCustomConfig(){
        if (this.fileConfiguration == null) {
            plugin.reloadConfig();
        }
        return this.fileConfiguration;
    }
}
