package it.blastmc.commands.handler;

import it.blastmc.Main;
import it.blastmc.messages.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

public abstract class CommandHandler extends BukkitCommand implements CommandExecutor {
    private final int minArgs, maxArgs;

    private final FileConfiguration messages = Main.getInstance().messages.getCustomConfig();

    public CommandHandler(String name) {
        this(name, 0);
    }

    public CommandHandler(String name, int requiredArgs) {
        this(name, requiredArgs, requiredArgs);
    }

    public CommandHandler(String name, int minArgs, int maxArgs) {
        super(name);

        this.minArgs = minArgs;
        this.maxArgs = maxArgs;

        CommandMap commandMap = getCommandMap();
        if (commandMap != null) {
            commandMap.register(name, this);
        }
    }

    public CommandMap getCommandMap(){
        if (Bukkit.getPluginManager() instanceof SimplePluginManager){
            try {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);

                return (CommandMap) field.get(Bukkit.getPluginManager());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void sendUsage(CommandSender sender){
        Msg.send(sender, getUsage());
    }

    @Override
    public boolean execute(CommandSender commandSender, String alias, String[] strings) {
        if (strings.length < minArgs || strings.length > maxArgs) {
            sendUsage(commandSender);
            return true;
        }
        String permission = getPermission();
        if (permission != null && !commandSender.hasPermission(permission)){
            Msg.send(commandSender, messages.getString("messages.no-perms"), messages.getString("messages.prefix"));
            return true;
        }
        if (!onCommand(commandSender, strings)){
            sendUsage(commandSender);
            return true;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] strings) {
        return this.onCommand(commandSender, strings);
    }

    public abstract boolean onCommand(CommandSender sender, String[] strings);

    public abstract String getUsage();
}
