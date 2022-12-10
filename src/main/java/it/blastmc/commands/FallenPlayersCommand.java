package it.blastmc.commands;

import it.blastmc.Main;
import it.blastmc.features.FallenPlayerList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FallenPlayersCommand implements CommandExecutor {
    private final FallenPlayerList fallenPlayers = Main.getInstance().fallenPlayerList;
    private final String prefix = Main.getInstance().prefix;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            p.sendMessage(prefix + "Players caduti("+ ChatColor.GREEN + fallenPlayers.size() + ChatColor.WHITE + ") : \n" + fallenPlayers);
            return true;
        } else if (commandSender instanceof ConsoleCommandSender){
            ConsoleCommandSender ccs = (ConsoleCommandSender) commandSender;
            ccs.sendMessage(prefix + "Players caduti("+ ChatColor.GREEN + fallenPlayers.size() + ChatColor.WHITE + ") : \n" + fallenPlayers);
            return true;
        }
        return false;
    }
}
