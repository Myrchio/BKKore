package it.blastmc.messages;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msg {

    public static void send(CommandSender sender, String message){
        send(sender, message, "&f");
    }

    public static void send(CommandSender sender, String message, String prefix){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }
}
