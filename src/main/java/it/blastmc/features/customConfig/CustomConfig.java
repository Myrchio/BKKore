package it.blastmc.features.customConfig;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CustomConfig extends JavaPlugin {
    private final String fileName;

    private final FileConfiguration fileConfiguration;

    public CustomConfig(String fileName) {
        this.fileName = fileName;
        fileConfiguration = loadCustomConfig();
    }

    public FileConfiguration loadCustomConfig() {
        File out = new File(getDataFolder(), fileName);
        try {
            InputStream in = getResource(fileName);

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
    public FileConfiguration getCustomConfig(){
        if (this.fileConfiguration == null) {
            this.reloadConfig();
        }
        return this.fileConfiguration;
    }
}
