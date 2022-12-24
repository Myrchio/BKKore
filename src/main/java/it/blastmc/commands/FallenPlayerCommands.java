package it.blastmc.commands;

import it.blastmc.Main;
import it.blastmc.commands.handler.CommandHandler;
import it.blastmc.features.FallenPlayerList;
import it.blastmc.messages.Msg;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class FallenPlayerCommands {
    private final FileConfiguration messages = Main.getPlugin(Main.class).getMessages();
    private final FallenPlayerList fallenPlayers = Main.getInstance().fallenPlayerList;
    public FallenPlayerCommands(){
        new CommandHandler("fallenPlayers"){
            @Override
            public boolean onCommand(CommandSender sender, String[] parameters) {
                setPermission("bkk.fp");
                String message = messages.getString("messages.fallen-player").replace("{size}", Integer.toString(fallenPlayers.size()));
                Msg.send(sender, message + '\n' + fallenPlayers, messages.getString("messages.prefix"));
                return true;
            }

            @Override
            public String getUsage() {
                return messages.getString("messages.no-valid");
            }
        };
    }
}
