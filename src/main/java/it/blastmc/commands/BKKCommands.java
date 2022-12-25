package it.blastmc.commands;

import it.blastmc.Main;
import it.blastmc.commands.handler.CommandHandler;
import it.blastmc.messages.Msg;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class BKKCommands {
    private final FileConfiguration messages = Main.getInstance().messages.getCustomConfig();
    public BKKCommands() {
        new CommandHandler("bkk", 1){
            @Override
            public boolean onCommand(CommandSender sender, String[] strings) {
                if (strings[0].equalsIgnoreCase("help")){
                    setPermission("bkk.help");
                    for (String s : messages.getStringList("messages.help")) {
                        if (s.equalsIgnoreCase(messages.getStringList("messages.help").get(0)))
                            Msg.send(sender, s, messages.getString("messages.prefix"));
                        else{
                            Msg.send(sender, s);
                        }
                    }
                    return true;
                }
                return true;
            }

            @Override
            public String getUsage() {
                return messages.getString("messages.no-valid");
            }
        };
    }
}
