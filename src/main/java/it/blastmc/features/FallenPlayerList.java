package it.blastmc.features;

import org.bukkit.Bukkit;

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
            text.append(Bukkit.getPlayer(uuid).getDisplayName()).append("\n");
        }
        return text.toString();
    }
}
