package it.blastmc.commands;

import it.blastmc.Main;
import it.blastmc.features.FallenPlayerList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FallenPlayersCommand implements CommandExecutor {
    private final FallenPlayerList fallenPlayers = Main.getInstance().fallenPlayerList;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            p.sendMessage("Players caduti("+ fallenPlayers.size() +"): \n" + fallenPlayers);
            return true;
        } else if (commandSender instanceof ConsoleCommandSender){
            ConsoleCommandSender ccs = (ConsoleCommandSender) commandSender;
            ccs.sendMessage("Players caduti("+ fallenPlayers.size() +"): \n" + fallenPlayers);
            return true;
        }
        return false;
    }
}
