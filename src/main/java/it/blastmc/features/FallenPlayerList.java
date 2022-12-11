package it.blastmc.features;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.UUID;

public class FallenPlayerList {
    final private ArrayList<UUID> fallenPlayers;

    public FallenPlayerList() {
        fallenPlayers = new ArrayList<>();
    }

    public boolean search(UUID uuid) {
        for (UUID fallenPlayerUUID : fallenPlayers) {
            if (uuid == fallenPlayerUUID) {
                return true;
            }
        }
        return false;
    }

    public void add(UUID uuid){
        fallenPlayers.add(uuid);
    }

    public void remove(UUID uuid){
        fallenPlayers.remove(uuid);
    }

    public int size(){
        return fallenPlayers.size();
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (UUID uuid : fallenPlayers) {
            String s =  "" + ChatColor.WHITE + ", ";
            text.append(Bukkit.getPlayer(uuid).getDisplayName()).append(s);
        }
        if (text.length() > 0)
            return text.substring(0, text.length()-2);
        else
            return text.toString();
    }
}
