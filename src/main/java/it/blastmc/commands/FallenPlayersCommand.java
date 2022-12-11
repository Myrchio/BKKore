package it.blastmc.commands;

import it.blastmc.Main;
import it.blastmc.features.FallenPlayerList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FallenPlayersCommand implements CommandExecutor {
    private final FallenPlayerList fallenPlayers = Main.getInstance().fallenPlayerList;
    private final String prefix = Main.getInstance().prefix;
    private String onHelp(){
        String s = prefix + "Comandi disponibili:";
        s += "\n/bkk fp - " + ChatColor.GREEN + "Guarda i giocatori caduti";
        return s;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0){
            commandSender.sendMessage(onHelp());
            return true;
        } else if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("fp")){
                commandSender.sendMessage(prefix + "Players caduti ("+ ChatColor.GREEN + fallenPlayers.size() + ChatColor.WHITE + "): \n" + fallenPlayers);
                return true;
            }
            if (strings[0].equalsIgnoreCase("reload")){
                try{
                    Main.getInstance().saveConfig();
                }catch (Exception e){
                    commandSender.sendMessage(prefix + ChatColor.RED + "Plugin non caricato correttamente!");
                }

                commandSender.sendMessage(prefix + ChatColor.GREEN + "Plugin caricato correttamente!");
                return true;
            }
            if (strings[0].equalsIgnoreCase("help")){
                commandSender.sendMessage(onHelp());
                return true;
            }
        }
        commandSender.sendMessage(onHelp());
        return true;
    }
}
